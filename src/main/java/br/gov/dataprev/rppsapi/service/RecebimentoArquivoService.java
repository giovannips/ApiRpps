package br.gov.dataprev.rppsapi.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import br.gov.dataprev.rppsapi.dao.ArquivosDAO;
import br.gov.dataprev.rppsapi.dao.ConsultasDAO;
import br.gov.dataprev.rppsapi.dao.FileDBRepository;
import br.gov.dataprev.rppsapi.exception.RPPSValidationException;
import br.gov.dataprev.rppsapi.exception.RPPSValidationMessage;
import br.gov.dataprev.rppsapi.model.Arquivo;
import br.gov.dataprev.rppsapi.model.Consulta;
import br.gov.dataprev.rppsapi.model.FileDB;

@Service
public class RecebimentoArquivoService {

	@Autowired
	private FileDBRepository fileDBRepository;

	@Autowired
	private ConsultasDAO consultasDAO;

	@Autowired
	private ArquivosDAO arquivosDAO;

	private static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

	public Arquivo store(MultipartFile file) throws IOException, RPPSValidationException {
		RPPSValidationException ex = new RPPSValidationException();
		Arquivo arquivo = new Arquivo();

		// FileDB FileDB = new FileDB(fileName, file.getContentType(), file.getBytes());

		String tipoArquivo = file.getContentType();

		if (!tipoArquivo.equals("text/csv")) {
			ex.addMessage(new RPPSValidationMessage("MSG_ARQUIVO_INVALIDO", "MSG_ARQUIVO_INVALIDO"));
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

		// Aqui só pega as mensagens de erro de validação
		try (BufferedReader br = new BufferedReader(new StringReader(stringArquivo))) {
			ex = validarArquivo(br, ex);
			br.close();
		}
		if (!ex.isEmpty()) {
			throw ex;
		}

		// arquivo validado
		try {
			BufferedReader br = new BufferedReader(new StringReader(stringArquivo));
			String line = "";
			Long id = 0L;
			List<Consulta> listaConsulta = new ArrayList<Consulta>();
			
			while ((line = br.readLine()) != null) {
				// descarto as linhas em branco
				if (!line.equals("")) {
					String linha[] = line.split(";");
					// descarto a linha com a quantidade
					if (linha.length > 1) {
						// Grava o arquivo pegando o CNPJ da primeira linha
						if (id == 0) {
							String cnpj = linha[1];
							cnpj = cnpj.replaceAll("[^0-9]", "");
							arquivo.setCnpjEnte(Long.parseLong(cnpj));
							arquivo.setDataRegistro(new Date());
							arquivo.setNomeArquivo(fileName);
							arquivo.setProcessado(false);
							arquivo = arquivosDAO.save(arquivo);
						}

						id = id + 1;
						Long idArquivo = arquivo.getIdArquivo();

						listaConsulta.add(montarConsulta(id, idArquivo, linha));
					}
				}
			}
			//forçar erro
			Integer x = 10 / 0;

			consultasDAO.saveAll(listaConsulta);
			br.close();
		} catch (Exception e) {
			// TODO: colocar log
			arquivosDAO.delete(arquivo);
			ex.addMessage(new RPPSValidationMessage("MSG_PROBLEMA_GRAVACAO_REGISTROS", "MSG_PROBLEMA_GRAVACAO_REGISTROS"));
			throw ex;
		}
		return arquivo;
	}

	private RPPSValidationException validarArquivo(BufferedReader br, RPPSValidationException ex) throws IOException {

		Integer quantidadeRegistros = 0;

		String linha0 = "";
		linha0 = br.readLine();
		linha0 = linha0.replace(".", "");
		linha0 = linha0.replace(",", "");

		// TODO: validar número de linhas
		String line = "";
		while ((line = br.readLine()) != null) {
			// Aqui eu trabalho só com as linhas não vazias
			if (!line.equals("")) {
				String linha[] = line.split(";");
				// descarto a linha com a quantidade
				if (linha.length > 1) {
					quantidadeRegistros = quantidadeRegistros + 1;
					try {
						ValidadorService.validarCampos(linha, quantidadeRegistros);
					} catch (RPPSValidationException e) {
						for (RPPSValidationMessage msg : e.getMessages()) {
							ex.addMessage(msg);
						}
					}
				}
			}
		}

		return ex;
	}

	private Consulta montarConsulta(Long id, Long idArquivo, String[] linha) {

		Character origem = linha[2].charAt(0);

		String nomeServidor = linha[3];
		nomeServidor = nomeServidor.trim();

		String nomeDependente = linha[4];
		nomeDependente = nomeDependente.trim();

		Character tipoServidor = linha[5].charAt(0);

		String stringCpf = linha[6];
		stringCpf = stringCpf.replaceAll("[^0-9]", "");
		Long cpf = Long.parseLong(stringCpf);

		String StringDataInicioBeneficio = linha[7];
		Date dataInicioBeneficio = null;
		if (StringDataInicioBeneficio != null) {
			dataInicioBeneficio = converterData(StringDataInicioBeneficio);
		}

		String StringDataInicioPagamento = linha[8];
		Date dataInicioPagamento = null;
		if (StringDataInicioPagamento != null) {
			dataInicioPagamento = converterData(StringDataInicioPagamento);
		}

		String StringDataCessacao = linha[9];
		Date dataCessacao = null;
		if (StringDataCessacao != null) {
			dataCessacao = converterData(StringDataCessacao);
		}

		String stringValor = linha[10];
		stringValor = stringValor.replace(".", "");
		stringValor = stringValor.replace(",", ".");
		stringValor = stringValor.trim();
		BigDecimal valor = new BigDecimal(stringValor);

		String stringCompetencia = linha[11];
		String cp[] = stringCompetencia.split("/");
		stringCompetencia = cp[1] + cp[0];
		Integer competencia = Integer.parseInt(stringCompetencia);

		Consulta consulta = new Consulta(id, idArquivo, origem, nomeServidor, nomeDependente, tipoServidor, cpf,
				dataInicioBeneficio, dataInicioPagamento, dataCessacao, valor, competencia);

		return consulta;

	}

	private Date converterData(String data) {
		try {
			Date date = formatter.parse(data);
			return date;
		} catch (ParseException e) {
			return null;
		}
	}

	public FileDB getFile(String id) {
		return fileDBRepository.findById(id).get();
	}

	public Stream<FileDB> getAllFiles() {
		return fileDBRepository.findAll().stream();
	}
}
