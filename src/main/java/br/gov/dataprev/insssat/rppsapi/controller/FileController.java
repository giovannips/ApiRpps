package br.gov.dataprev.insssat.rppsapi.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.GsonBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.gov.dataprev.insssat.rppsapi.TOs.ArquivoFisicoTO;
import br.gov.dataprev.insssat.rppsapi.TOs.RetornoInsercaoTO;
import br.gov.dataprev.insssat.rppsapi.TOs.RetornoListaArquivosTO;
import br.gov.dataprev.insssat.rppsapi.TOs.RetornoListaLinhasTO;
import br.gov.dataprev.insssat.rppsapi.entidades.Arquivo;
import br.gov.dataprev.insssat.rppsapi.entidades.LinhaArquivo;
import br.gov.dataprev.insssat.rppsapi.exception.RPPSValidationException;
import br.gov.dataprev.insssat.rppsapi.exception.RPPSValidationMessage;
import br.gov.dataprev.insssat.rppsapi.service.ArquivoService;

@Controller
// @CrossOrigin(origins = "http://127.0.0.1:8080")
public class FileController extends BaseController {

	private static Logger LOGGER = LogManager.getLogger(FileController.class);

	@Autowired
	private ArquivoService arquivosService;

	@PostMapping("/rppsapi/upload/{cnpj}")
	public ResponseEntity<RetornoInsercaoTO> uploadFile(@RequestParam("file") MultipartFile file,
			@PathVariable String cnpj) {

		RetornoInsercaoTO retorno = new RetornoInsercaoTO();
		try {
			Arquivo arquivo = arquivosService.gravarDadosArquivo(file, cnpj);

			retorno.setSucesso(true);
			retorno.setArquivo(arquivo);
			LOGGER.debug("Enviou corretamente");
			return new ResponseEntity<RetornoInsercaoTO>(retorno, HttpStatus.OK);
		} catch (RPPSValidationException e) {
			for (RPPSValidationMessage mensagem : e.getMessages()) {
				retorno.adicionarMensagemErro(getMensagem(mensagem.getDebugMessage(), mensagem.getParams()));
				LOGGER.debug(getMensagem(mensagem.getDebugMessage(), mensagem.getParams()), e);
			}
			retorno.setSucesso(false);
			return new ResponseEntity<RetornoInsercaoTO>(retorno, HttpStatus.OK);
		} catch (IOException e) {
			//TODO verificar para enviar outro ResponseEntity
			LOGGER.error(e.getMessage(), e);
			retorno.adicionarMensagemErro(getMensagem("MSG_IO_EXCEPTION"));
			retorno.setSucesso(false);
			return new ResponseEntity<RetornoInsercaoTO>(retorno, HttpStatus.FORBIDDEN);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			retorno.adicionarMensagemErro(getMensagem("MSG_ERRO_GERAL"));
			retorno.setSucesso(false);
			return new ResponseEntity<RetornoInsercaoTO>(retorno, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/rppsapi/inserirArquivo/{cnpj}")
	public ResponseEntity<RetornoInsercaoTO> inserirArquivo(@RequestBody String json, @PathVariable String cnpj) {

		RetornoInsercaoTO retorno = new RetornoInsercaoTO();
		try {
			
			if (json == null || json.isEmpty()) {
				//TODO criar message JSON_INVALIDO
				retorno.adicionarMensagemErro(getMensagem("MSG_ERRO_GERAL"));
				return new ResponseEntity<RetornoInsercaoTO>(retorno, HttpStatus.BAD_REQUEST);
			}

			ArquivoFisicoTO arquivoFisicoTO = new GsonBuilder().create().fromJson(json, ArquivoFisicoTO.class);
			
			Arquivo arquivo = arquivosService.gravarDadosArquivo(arquivoFisicoTO, cnpj);

			retorno.setSucesso(true);
			retorno.setArquivo(arquivo);
			LOGGER.debug("Enviou corretamente");
			return new ResponseEntity<RetornoInsercaoTO>(retorno, HttpStatus.OK);
		} catch (RPPSValidationException e) {
			for (RPPSValidationMessage mensagem : e.getMessages()) {
				retorno.adicionarMensagemErro(getMensagem(mensagem.getDebugMessage(), mensagem.getParams()));
				LOGGER.debug(getMensagem(mensagem.getDebugMessage(), mensagem.getParams()), e);
			}
			retorno.setSucesso(false);
			return new ResponseEntity<RetornoInsercaoTO>(retorno, HttpStatus.OK);
		//TODO verificar erro parser json e retirar IOException
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
			retorno.adicionarMensagemErro(getMensagem("MSG_IO_EXCEPTION"));
			retorno.setSucesso(false);
			return new ResponseEntity<RetornoInsercaoTO>(retorno, HttpStatus.FORBIDDEN);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			retorno.adicionarMensagemErro(getMensagem("MSG_ERRO_GERAL"));
			retorno.setSucesso(false);
			return new ResponseEntity<RetornoInsercaoTO>(retorno, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}


	@GetMapping(path = "/rppsapi/obterListaArquivosPorCNPJ/{cnpj}")
	public ResponseEntity<RetornoListaArquivosTO> obterListaArquivosPorCNPJ(@PathVariable String cnpj) {

		RetornoListaArquivosTO retorno = new RetornoListaArquivosTO();

		try {
			List<Arquivo> listaArquivos = arquivosService.obterListaArquivosPorCNPJ(Long.parseLong(cnpj));
			retorno.setListaArquivos(listaArquivos);
			LOGGER.debug("Retornou lista de arquivos com " + listaArquivos.size() + " registros.");
			return new ResponseEntity<RetornoListaArquivosTO>(retorno, HttpStatus.OK);

		} catch (NumberFormatException e) {
			retorno.adicionarMensagemErro(getMensagem("MSG_CNPJ_CONSULTA_INVALIDO"));
			return new ResponseEntity<RetornoListaArquivosTO>(retorno, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			retorno.adicionarMensagemErro(getMensagem("MSG_ERRO_GERAL"));
			return new ResponseEntity<RetornoListaArquivosTO>(retorno, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping(path = "/rppsapi/obterListaLinhasPorCPF/{cpf}")
	public ResponseEntity<RetornoListaLinhasTO> obterListaLinhasPorCPF(@PathVariable String cpf) {

		RetornoListaLinhasTO retorno = new RetornoListaLinhasTO();

		try {
			List<LinhaArquivo> listaLinhas = arquivosService.obterLinhasPorCPF(Long.parseLong(cpf));
			retorno.setListaLinhas(listaLinhas);
			LOGGER.debug("Retornou lista de linhas com " + listaLinhas.size() + " registros.");
			return new ResponseEntity<RetornoListaLinhasTO>(retorno, HttpStatus.OK);

		} catch (NumberFormatException e) {
			retorno.adicionarMensagemErro(getMensagem("MSG_CNPJ_CONSULTA_INVALIDO"));
			return new ResponseEntity<RetornoListaLinhasTO>(retorno, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			retorno.adicionarMensagemErro(getMensagem("MSG_ERRO_GERAL"));
			return new ResponseEntity<RetornoListaLinhasTO>(retorno, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/rppsapi/downloadArquivo/{idArquivo}")
	public void exportCSV(HttpServletResponse response, @PathVariable String idArquivo) throws Exception {

		if (idArquivo == null || idArquivo.equals("a")) {
			response.sendError(500, "Necessário enviar idArquivo");
		} else {
			String filename = idArquivo + ".csv";
			response.setContentType("text/csv");
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");

			StatefulBeanToCsv<LinhaArquivo> writer = new StatefulBeanToCsvBuilder<LinhaArquivo>(response.getWriter())
					.withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).withSeparator(CSVWriter.DEFAULT_SEPARATOR)
					.withOrderedResults(false).build();

			writer.write(arquivosService.receberArquivo(idArquivo));
		}
	}

}
