package br.gov.dataprev.insssat.rppsapi.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

public class BaseController {

    protected static final String DATE_PATTERN = "dd/MM/yyyy";

    @Autowired
    MessageSource mensagens;

    protected String getMensagem(String mensagem,Object[] parametros){
       return mensagens.getMessage(mensagem,parametros,new Locale("pt", "BR"));
    }

    protected String getMensagem(String mensagem){
        return mensagens.getMessage(mensagem,null,new Locale("pt", "BR"));
    }

}
