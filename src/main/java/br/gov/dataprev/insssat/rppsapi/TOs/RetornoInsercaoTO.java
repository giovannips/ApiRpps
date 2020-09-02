package br.gov.dataprev.insssat.rppsapi.TOs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.gov.dataprev.insssat.rppsapi.entidades.Arquivo;

public class RetornoInsercaoTO {

    private Boolean sucesso;
    private List<String> mensagensErro;
    private Arquivo arquivo;

    public RetornoInsercaoTO() {
        // propositadamente deixado em branco
    }

    public Boolean isSucesso() {
        return this.sucesso;
    }

    public Boolean getSucesso() {
        return this.sucesso;
    }

    public void setSucesso(Boolean sucesso) {
        this.sucesso = sucesso;
    }

    public List<String> getMensagensErro() {
        return this.mensagensErro;
    }

    public void setMensagensErro(List<String> mensagensErro) {
        this.mensagensErro = mensagensErro;
    }

    public Arquivo getArquivo() {
        return this.arquivo;
    }

    public void setArquivo(Arquivo arquivo) {
        this.arquivo = arquivo;
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
        if (!(o instanceof RetornoInsercaoTO)) {
            return false;
        }
        RetornoInsercaoTO retornoInsercaoTO = (RetornoInsercaoTO) o;
        return Objects.equals(sucesso, retornoInsercaoTO.sucesso)
                && Objects.equals(mensagensErro, retornoInsercaoTO.mensagensErro)
                && Objects.equals(arquivo, retornoInsercaoTO.arquivo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sucesso, mensagensErro, arquivo);
    }

    @Override
    public String toString() {
        return "{" + " sucesso='" + isSucesso() + "'" + ", mensagensErro='" + getMensagensErro() + "'" + ", arquivo='"
                + getArquivo() + "'" + "}";
    }

}