package br.gov.dataprev.rppsapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import br.gov.dataprev.rppsapi.TOs.RetornoInsercaoTO;
import br.gov.dataprev.rppsapi.controller.BaseController;



@ControllerAdvice
public class FileUploadExceptionAdvice extends BaseController {

  @ExceptionHandler(MaxUploadSizeExceededException.class)
  public ResponseEntity<RetornoInsercaoTO> handleMaxSizeException(MaxUploadSizeExceededException exc) {

    RetornoInsercaoTO retorno = new RetornoInsercaoTO();
    retorno.adicionarMensagemErro(getMensagem("MSG_TAMANHO_EXCEDIDO"));
    retorno.setSucesso(false);
    return new ResponseEntity<RetornoInsercaoTO>(retorno, HttpStatus.EXPECTATION_FAILED);
  }
}
