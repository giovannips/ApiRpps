package br.gov.dataprev.insssat.rppsapi.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import br.gov.dataprev.insssat.rppsapi.dao.ArquivosDAO;
import br.gov.dataprev.insssat.rppsapi.dao.LinhasDAO;
import br.gov.dataprev.insssat.rppsapi.entidades.Arquivo;
import br.gov.dataprev.insssat.rppsapi.entidades.LinhaArquivo;
import br.gov.dataprev.insssat.rppsapi.exception.RPPSValidationException;
import br.gov.dataprev.insssat.rppsapi.exception.RPPSValidationMessage;

@Service
public class RecebimentoArquivoService {

	@Autowired
	private LinhasDAO linhasDAO;

	@Autowired
	private ArquivosDAO arquivosDAO;

	public Arquivo gravarDadosArquivo(MultipartFile file, String cnpjCredencial) throws IOException, RPPSValidationException {
		RPPSValidationException ex = new RPPSValidationException();
		Arquivo arquivo = new Arquivo();

		// FileDB FileDB = new FileDB(fileName, file.getContentType(), file.getBytes());

		String tipoArquivo = file.getContentType();

		if (!tipoArquivo.equals("text/csv")) {
			ex.addMessage(new RPPSValidationMessage("MSG_ARQUIVO_INVALIDO", "MSG_ARQUIVO_INVALIDO"));
			throw ex;
		}

		String cnpj = ValidadorService.obterCNPJValido(cnpjCredencial);
		if (cnpj == null){
			ex.addMessage(new RPPSValidationMessage("MSG_CNPJ_CREDENCIAL_INVALIDO", "MSG_CNPJ_CREDENCIAL_INVALIDO", cnpjCredencial));
			throw ex;
		}


		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		String stringArquivo = new String(file.getBytes(), StandardCharsets.UTF_8);

		// valida informação de quantidade de registros e número de linhas
		try {
			BufferedReader br = new BufferedReader(new StringReader(stringArquivo));
			ValidadorService.validarNumeroLinhas(br, ex);
			br.close();
		} catch (RPPSValidationException e) {
			throw e;
		} catch (IOException e) {
			ex.addMessage(new RPPSValidationMessage("MSG_ARQUIVO_INVALIDO", "MSG_ARQUIVO_INVALIDO"));
			throw ex;
		}

		try {
			BufferedReader br = new BufferedReader(new StringReader(stringArquivo));
			String line = "";
			Long id = 0L;
			List<LinhaArquivo> listaArquivoLinha = new ArrayList<LinhaArquivo>();
			Long idArquivo = null;

			arquivo.setCnpjEnte(Long.parseLong(cnpj));
			arquivo.setDataRegistro(new Date());
			arquivo.setNomeArquivo(fileName);
			arquivo.setProcessado(false);
			arquivo = arquivosDAO.save(arquivo);
			idArquivo = arquivo.getIdArquivo();

			while ((line = br.readLine()) != null) {
				// descarto as linhas em branco
				if (!line.equals("")) {
					String linha[] = line.split(";");
					// descarto a linha com a quantidade
					if (linha.length > 1) {
						id = id + 1;

						try {
							listaArquivoLinha.add(ValidadorService.validarCampos(linha, id, idArquivo, cnpj));
						} catch (RPPSValidationException e) {
							for (RPPSValidationMessage mensagem : e.getMessages()) {
								ex.addMessage(mensagem);
							}
						}

						// A cada 100 linhas verifico se existem muitas mensagens de erro.
						// Se existirem já subo e não valido tudo
						if (id % 100 == 0 && ex.getMessages().size() > 100) {
							ex.addMessage(new RPPSValidationMessage("MSG_MUITOS_ERROS", "MSG_MUITOS_ERROS"));
							arquivosDAO.delete(arquivo);
							br.close();
							throw ex;
						}
					}
				}
			}
			br.close();
			if (ex.getMessages().size() > 0) {
				throw ex;
			}else {
				linhasDAO.saveAll(listaArquivoLinha);
			}
		} catch (RPPSValidationException e) {
			arquivosDAO.delete(arquivo);
			throw e;
		} catch (Exception e) {
			// TODO: colocar log
			arquivosDAO.delete(arquivo);
			ex.addMessage(
					new RPPSValidationMessage("MSG_PROBLEMA_GRAVACAO_REGISTROS", "MSG_PROBLEMA_GRAVACAO_REGISTROS"));
			throw ex;
		}
		return arquivo;
	}

}
