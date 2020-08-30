package br.gov.dataprev.rppsapi.model;

import java.io.Serializable;

public class IdsLinhasArquivos implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idLinha;

	private Long idArquivo;

	public IdsLinhasArquivos(Long idLinha, Long idArquivo) {
		this.idLinha = idLinha;
		this.idArquivo = idArquivo;
	}

	public IdsLinhasArquivos() {
		// proposital
	}

	public Long getIdLinha() {
		return idLinha;
	}

	public void setIdLinha(Long idLinha) {
		this.idLinha = idLinha;
	}

	public Long getIdArquivo() {
		return idArquivo;
	}

	public void setIdArquivo(Long idArquivo) {
		this.idArquivo = idArquivo;
	}

}