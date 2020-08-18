package br.gov.dataprev.rppsapi.model;

import java.io.Serializable;


public class IdsConsultas implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idConsulta;
	
	private Long idArquivo;
	

	public IdsConsultas(Long idConsulta, Long idArquivo) {
		this.idConsulta = idConsulta;
		this.idArquivo = idArquivo;
	}

	public IdsConsultas() {
		//proposital
	}

	public Long getIdConsulta() {
		return idConsulta;
	}

	public void setIdConsulta(Long idConsulta) {
		this.idConsulta = idConsulta;
	}

	public Long getIdArquivo() {
		return idArquivo;
	}

	public void setIdArquivo(Long idArquivo) {
		this.idArquivo = idArquivo;
	}

	
}