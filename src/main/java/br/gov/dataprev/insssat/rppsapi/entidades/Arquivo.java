package br.gov.dataprev.insssat.rppsapi.entidades;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "arquivos")
public class Arquivo {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
	@SequenceGenerator(name = "generator", sequenceName = "sq_arquivos", allocationSize = 1)
	@Column(name = "id_arquivo")
	private Long idArquivo;
	
	@Column(name = "cnpj_ente")
	private Long cnpjEnte;
	
	@Column(name = "data_registro")
	private Date dataRegistro;
	
	@Column(name = "nome_arquivo")
	private String nomeArquivo;
	
	private Boolean processado;

	
	public Arquivo() {
		
	}
	
	public Arquivo(Long idArquivo, Long cnpjEnte, Date dataRegistro, String nomeArquivo, Boolean processado) {
		super();
		this.idArquivo = idArquivo;
		this.cnpjEnte = cnpjEnte;
		this.dataRegistro = dataRegistro;
		this.nomeArquivo = nomeArquivo;
		this.processado = processado;
	}

	public Long getIdArquivo() {
		return idArquivo;
	}

	public void setIdArquivo(Long idArquivo) {
		this.idArquivo = idArquivo;
	}

	public Long getCnpjEnte() {
		return cnpjEnte;
	}

	public void setCnpjEnte(Long cnpjEnte) {
		this.cnpjEnte = cnpjEnte;
	}

	public Date getDataRegistro() {
		return dataRegistro;
	}

	public void setDataRegistro(Date dataRegistro) {
		this.dataRegistro = dataRegistro;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public Boolean getProcessado() {
		return processado;
	}

	public void setProcessado(Boolean processado) {
		this.processado = processado;
	}

	





}
