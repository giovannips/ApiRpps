package br.gov.dataprev.rppsapi.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "consultas")
@IdClass(IdsConsultas.class)
public class Consulta implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private Long idConsulta;

	@Id
	private Long idArquivo;

	private Character origem;
	private String nomeServidor;
	private String nomeDependente;
	private Character tipoServidor;
	private Long cpf;
	private Date dataInicioBeneficio;
	private Date dataInicioPagamento;
	private Date dataCessacao;
	private BigDecimal valor;
	private Integer competencia;

	public Consulta() {

	}

	public Consulta(Long idConsulta, Long idArquivo, Character origem, String nomeServidor, String nomeDependente,
			Character tipoServidor, Long cpf, Date dataInicioBeneficio, Date dataInicioPagamento,
			Date dataCessacao, BigDecimal valor, Integer competencia) {
		this.idConsulta = idConsulta;
		this.idArquivo = idArquivo;
		this.origem = origem;
		this.nomeServidor = nomeServidor;
		this.nomeDependente = nomeDependente;
		this.tipoServidor = tipoServidor;
		this.cpf = cpf;
		this.dataInicioBeneficio = dataInicioBeneficio;
		this.dataInicioPagamento = dataInicioPagamento;
		this.dataCessacao = dataCessacao;
		this.valor = valor;
		this.competencia = competencia;
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

	public Character getOrigem() {
		return origem;
	}

	public void setOrigem(Character origem) {
		this.origem = origem;
	}

	public String getNomeServidor() {
		return nomeServidor;
	}

	public void setNomeServidor(String nomeServidor) {
		this.nomeServidor = nomeServidor;
	}

	public String getNomeDependente() {
		return nomeDependente;
	}

	public void setNomeDependente(String nomeDependente) {
		this.nomeDependente = nomeDependente;
	}

	public Character getTipoServidor() {
		return tipoServidor;
	}

	public void setTipoServidor(Character tipoServidor) {
		this.tipoServidor = tipoServidor;
	}

	public Long getCpf() {
		return cpf;
	}

	public void setCpf(Long cpf) {
		this.cpf = cpf;
	}

	public Date getDataInicioBeneficio() {
		return dataInicioBeneficio;
	}

	public void setDataInicioBeneficio(Date dataInicioBeneficio) {
		this.dataInicioBeneficio = dataInicioBeneficio;
	}

	public Date getDataInicioPagamento() {
		return dataInicioPagamento;
	}

	public void setDataInicioPagamento(Date dataInicioPagamento) {
		this.dataInicioPagamento = dataInicioPagamento;
	}

	public Date getDataCessacao() {
		return dataCessacao;
	}

	public void setDataCessacao(Date dataCessacao) {
		this.dataCessacao = dataCessacao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Integer getCompetencia() {
		return competencia;
	}

	public void setCompetencia(Integer competencia) {
		this.competencia = competencia;
	}

}
