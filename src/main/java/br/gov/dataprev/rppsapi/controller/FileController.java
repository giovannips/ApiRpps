package br.gov.dataprev.rppsapi.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.gov.dataprev.rppsapi.TOs.RetornoInsercaoTO;
import br.gov.dataprev.rppsapi.exception.RPPSValidationException;
import br.gov.dataprev.rppsapi.exception.RPPSValidationMessage;
import br.gov.dataprev.rppsapi.message.ResponseFile;
import br.gov.dataprev.rppsapi.model.FileDB;
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
			recebimentoArquivosService.store(file);

			retorno.setSucesso(true);
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

	@GetMapping("/files")
	public ResponseEntity<List<ResponseFile>> getListFiles() {
		List<ResponseFile> files = recebimentoArquivosService.getAllFiles().map(dbFile -> {
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/files/")
					.path((dbFile.getId()).toString()).toUriString();

			return new ResponseFile(dbFile.getName(), fileDownloadUri, dbFile.getType(), dbFile.getData().length);
		}).collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.OK).body(files);
	}

	@GetMapping("/files/{id}")
	public ResponseEntity<byte[]> getFile(@PathVariable String id) {
		FileDB fileDB = recebimentoArquivosService.getFile(id);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
				.body(fileDB.getData());
	}
}
