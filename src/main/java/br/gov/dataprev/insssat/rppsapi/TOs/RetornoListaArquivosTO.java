package br.gov.dataprev.insssat.rppsapi.TOs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.gov.dataprev.insssat.rppsapi.entidades.Arquivo;

public class RetornoListaArquivosTO {


    private List<String> mensagensErro;
    private List<Arquivo> listaArquivos;

    public RetornoListaArquivosTO() {
        // propositadamente deixado em branco
    }

    public List<String> getMensagensErro() {
        return this.mensagensErro;
    }

    public void setMensagensErro(List<String> mensagensErro) {
        this.mensagensErro = mensagensErro;
    }

    public List<Arquivo> getListaArquivos() {
        return this.listaArquivos;
    }

    public void setListaArquivos(List<Arquivo> listaArquivos) {
        this.listaArquivos = listaArquivos;
    }
    
    public void adicionarMensagemErro(String mensagem) {
        if (this.mensagensErro == null) {
            this.mensagensErro = new ArrayList<String>();
        }
        this.mensagensErro.add(mensagem);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof RetornoListaArquivosTO)) {
            return false;
        }
        RetornoListaArquivosTO retornoListaArquivosTO = (RetornoListaArquivosTO) o;
        return Objects.equals(mensagensErro, retornoListaArquivosTO.mensagensErro) && Objects.equals(listaArquivos, retornoListaArquivosTO.listaArquivos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mensagensErro, listaArquivos);
    }

    @Override
    public String toString() {
        return "{" +
            " mensagensErro='" + getMensagensErro() + "'" +
            ", listaArquivos='" + getListaArquivos() + "'" +
            "}";
    }
  

}