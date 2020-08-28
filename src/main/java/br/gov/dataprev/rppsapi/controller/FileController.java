package br.gov.dataprev.rppsapi.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import br.gov.dataprev.rppsapi.TOs.RetornoInsercaoTO;
import br.gov.dataprev.rppsapi.exception.RPPSValidationException;
import br.gov.dataprev.rppsapi.exception.RPPSValidationMessage;
import br.gov.dataprev.rppsapi.model.Arquivo;
import br.gov.dataprev.rppsapi.service.RecebimentoArquivoService;

@Controller
//@CrossOrigin(origins = "http://127.0.0.1:8080")
public class FileController extends BaseController {

	@Autowired
	private RecebimentoArquivoService recebimentoArquivosService;

	@PostMapping("/upload")
	public ResponseEntity<RetornoInsercaoTO> uploadFile(@RequestParam("file") MultipartFile file) {
		
		RetornoInsercaoTO retorno = new RetornoInsercaoTO();
		try {
			Arquivo arquivo = recebimentoArquivosService.store(file);

			retorno.setSucesso(true);
			retorno.setArquivo(arquivo);
			return new ResponseEntity<RetornoInsercaoTO>(retorno, HttpStatus.OK);
		}
		catch (RPPSValidationException e) {
			for (RPPSValidationMessage mensagem : e.getMessages()) {
				retorno.adicionarMensagemErro(getMensagem(mensagem.getDebugMessage(), mensagem.getParams()));
			}
			retorno.setSucesso(false);
			return new ResponseEntity<RetornoInsercaoTO>(retorno, HttpStatus.BAD_REQUEST);
		}
		catch(IOException e){
			//TODO:fazer LOG
			//LOG.error(e.getMessage(), e);
			retorno.adicionarMensagemErro(getMensagem("MSG_IO_EXCEPTION"));
			retorno.setSucesso(false);
			return new ResponseEntity<RetornoInsercaoTO>(retorno, HttpStatus.FORBIDDEN);
		}
		catch (Exception e) {
			//TODO:fazer LOG
			//LOG.error(e.getMessage(), e);
			retorno.adicionarMensagemErro(getMensagem("MSG_ERRO_GERAL"));
			retorno.setSucesso(false);
			return new ResponseEntity<RetornoInsercaoTO>(retorno, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
