package br.gov.dataprev.insssat.rppsapi.TOs;

import java.util.ArrayList;
import java.util.List;

import br.gov.dataprev.insssat.rppsapi.entidades.LinhaArquivo;

public class RetornoListaLinhasTO {

    private List<String> mensagensErro;
    private List<LinhaArquivo> listaLinhas;

    public RetornoListaLinhasTO() {
        // propositadamente deixado em branco
    }

	public List<String> getMensagensErro() {
		return mensagensErro;
	}

	public void setMensagensErro(List<String> mensagensErro) {
		this.mensagensErro = mensagensErro;
	}

	public List<LinhaArquivo> getListaLinhas() {
		return listaLinhas;
	}

	public void setListaLinhas(List<LinhaArquivo> listaLinhas) {
		this.listaLinhas = listaLinhas;
	}
	
    public void adicionarMensagemErro(String mensagem) {
        if (this.mensagensErro == null) {
            this.mensagensErro = new ArrayList<String>();
        }
        this.mensagensErro.add(mensagem);
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((listaLinhas == null) ? 0 : listaLinhas.hashCode());
		result = prime * result + ((mensagensErro == null) ? 0 : mensagensErro.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RetornoListaLinhasTO other = (RetornoListaLinhasTO) obj;
		if (listaLinhas == null) {
			if (other.listaLinhas != null)
				return false;
		} else if (!listaLinhas.equals(other.listaLinhas))
			return false;
		if (mensagensErro == null) {
			if (other.mensagensErro != null)
				return false;
		} else if (!mensagensErro.equals(other.mensagensErro))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RetornoListaLinhasTO [mensagensErro=" + mensagensErro + ", listaLinhas=" + listaLinhas + "]";
	}
	
	
}
