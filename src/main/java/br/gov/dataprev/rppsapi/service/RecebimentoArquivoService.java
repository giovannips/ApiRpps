package br.gov.dataprev.rppsapi.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
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

	public void store(MultipartFile file) throws IOException {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		// FileDB FileDB = new FileDB(fileName, file.getContentType(), file.getBytes());

		String stringArquivo = new String(file.getBytes(), StandardCharsets.UTF_8);

		try (BufferedReader br = new BufferedReader(new StringReader(stringArquivo))) {
			String line = "";
			Long id = 0L;
			List<Consulta> listaConsulta = new ArrayList<Consulta>();
			Arquivo arquivo = new Arquivo();
			while ((line = br.readLine()) != null) {
				String linha[] = line.split(";");
				ValidadorService.validarCampos(linha);

				// Grava o arquivo pegando o CNPJ da primeira linha
				if (id == 0) {
					arquivo.setCnpjEnte(Long.parseLong(linha[0]));
					arquivo.setDataRegistro(new Date());
					arquivo.setNomeArquivo(fileName);
					arquivo.setProcessado(false);
					arquivo = arquivosDAO.save(arquivo);
				}

				id = id + 1;
				Long idArquivo = arquivo.getIdArquivo();

				listaConsulta.add(montarConsulta(id, idArquivo, linha));
			}
			consultasDAO.saveAll(listaConsulta);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private Consulta montarConsulta(Long id, Long idArquivo, String[] linha) {

		Character origem = linha[1].charAt(0);
		String nomeServidor = linha[2];
		String nomeDependente = linha[3];
		Character tipoServidor = linha[4].charAt(0);
		Long cpf = Long.parseLong(linha[5]);
		String dataInicio = linha[6];
		String dataInicioPagamento = linha[7];
		String dataCessacao = linha[8];
		Long valor = Long.parseLong(linha[9]);
		String competencia = linha[10];

		Consulta consulta = new Consulta(id, idArquivo, origem, nomeServidor, nomeDependente, tipoServidor, cpf,
				dataInicio, dataInicioPagamento, dataCessacao, valor, competencia);

		return consulta;

	}
	public FileDB getFile(String id) {
		return fileDBRepository.findById(id).get();
	}

	public Stream<FileDB> getAllFiles() {
		return fileDBRepository.findAll().stream();
	}
}
