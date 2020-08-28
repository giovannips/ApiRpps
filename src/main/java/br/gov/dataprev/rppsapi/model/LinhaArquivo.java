package br.gov.dataprev.rppsapi.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "arquivos_linhas")
@IdClass(IdsConsultas.class)
public class LinhaArquivo implements Serializable {

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
	private Character tipoServidor;
	private Character tipoBeneficio;
	private String nomeDependente;
	private Character tipoDependente;
	private Long cpf;
	private Date dataInicioBeneficio;
	private Date dataInicioPagamento;
	private Date dataCessacao;
	private BigDecimal valor;
	private Integer competencia;

	public LinhaArquivo() {
		// propositadamente deixado em branco
	}

	public LinhaArquivo(Long idConsulta, Long idArquivo, Character origem, String nomeServidor, Character tipoServidor,
			Character tipoBeneficio, String nomeDependente, Character tipoDependente, Long cpf,
			Date dataInicioBeneficio, Date dataInicioPagamento, Date dataCessacao, BigDecimal valor,
			Integer competencia) {
		this.idConsulta = idConsulta;
		this.idArquivo = idArquivo;
		this.origem = origem;
		this.nomeServidor = nomeServidor;
		this.tipoServidor = tipoServidor;
		this.tipoBeneficio = tipoBeneficio;
		this.nomeDependente = nomeDependente;
		this.tipoDependente = tipoDependente;
		this.cpf = cpf;
		this.dataInicioBeneficio = dataInicioBeneficio;
		this.dataInicioPagamento = dataInicioPagamento;
		this.dataCessacao = dataCessacao;
		this.valor = valor;
		this.competencia = competencia;
	}

	public Long getIdConsulta() {
		return this.idConsulta;
	}

	public void setIdConsulta(Long idConsulta) {
		this.idConsulta = idConsulta;
	}

	public Long getIdArquivo() {
		return this.idArquivo;
	}

	public void setIdArquivo(Long idArquivo) {
		this.idArquivo = idArquivo;
	}

	public Character getOrigem() {
		return this.origem;
	}

	public void setOrigem(Character origem) {
		this.origem = origem;
	}

	public String getNomeServidor() {
		return this.nomeServidor;
	}

	public void setNomeServidor(String nomeServidor) {
		this.nomeServidor = nomeServidor;
	}

	public Character getTipoBeneficio() {
		return this.tipoBeneficio;
	}

	public void setTipoBeneficio(Character tipoBeneficio) {
		this.tipoBeneficio = tipoBeneficio;
	}

	public String getNomeDependente() {
		return this.nomeDependente;
	}

	public void setNomeDependente(String nomeDependente) {
		this.nomeDependente = nomeDependente;
	}

	public Character getTipoDependente() {
		return this.tipoDependente;
	}

	public void setTipoDependente(Character tipoDependente) {
		this.tipoDependente = tipoDependente;
	}

	public Character getTipoServidor() {
		return this.tipoServidor;
	}

	public void setTipoServidor(Character tipoServidor) {
		this.tipoServidor = tipoServidor;
	}

	public Long getCpf() {
		return this.cpf;
	}

	public void setCpf(Long cpf) {
		this.cpf = cpf;
	}

	public Date getDataInicioBeneficio() {
		return this.dataInicioBeneficio;
	}

	public void setDataInicioBeneficio(Date dataInicioBeneficio) {
		this.dataInicioBeneficio = dataInicioBeneficio;
	}

	public Date getDataInicioPagamento() {
		return this.dataInicioPagamento;
	}

	public void setDataInicioPagamento(Date dataInicioPagamento) {
		this.dataInicioPagamento = dataInicioPagamento;
	}

	public Date getDataCessacao() {
		return this.dataCessacao;
	}

	public void setDataCessacao(Date dataCessacao) {
		this.dataCessacao = dataCessacao;
	}

	public BigDecimal getValor() {
		return this.valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Integer getCompetencia() {
		return this.competencia;
	}

	public void setCompetencia(Integer competencia) {
		this.competencia = competencia;
	}

	public LinhaArquivo dataInicioBeneficio(Date dataInicioBeneficio) {
		this.dataInicioBeneficio = dataInicioBeneficio;
		return this;
	}

	public LinhaArquivo dataInicioPagamento(Date dataInicioPagamento) {
		this.dataInicioPagamento = dataInicioPagamento;
		return this;
	}

	public LinhaArquivo dataCessacao(Date dataCessacao) {
		this.dataCessacao = dataCessacao;
		return this;
	}

	public LinhaArquivo valor(BigDecimal valor) {
		this.valor = valor;
		return this;
	}

	public LinhaArquivo competencia(Integer competencia) {
		this.competencia = competencia;
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof LinhaArquivo)) {
			return false;
		}
		LinhaArquivo linhaArquivo = (LinhaArquivo) o;
		return Objects.equals(idConsulta, linhaArquivo.idConsulta) && Objects.equals(idArquivo, linhaArquivo.idArquivo)
				&& Objects.equals(origem, linhaArquivo.origem) && Objects.equals(nomeServidor, linhaArquivo.nomeServidor)
				&& Objects.equals(tipoBeneficio, linhaArquivo.tipoBeneficio)
				&& Objects.equals(nomeDependente, linhaArquivo.nomeDependente)
				&& Objects.equals(tipoDependente, linhaArquivo.tipoDependente)
				&& Objects.equals(tipoServidor, linhaArquivo.tipoServidor) && Objects.equals(cpf, linhaArquivo.cpf)
				&& Objects.equals(dataInicioBeneficio, linhaArquivo.dataInicioBeneficio)
				&& Objects.equals(dataInicioPagamento, linhaArquivo.dataInicioPagamento)
				&& Objects.equals(dataCessacao, linhaArquivo.dataCessacao) && Objects.equals(valor, linhaArquivo.valor)
				&& Objects.equals(competencia, linhaArquivo.competencia);
	}

	@Override
	public int hashCode() {
		return Objects.hash(idConsulta, idArquivo, origem, nomeServidor, tipoBeneficio, nomeDependente, tipoDependente,
				tipoServidor, cpf, dataInicioBeneficio, dataInicioPagamento, dataCessacao, valor, competencia);
	}

	@Override
	public String toString() {
		return "{" + " idConsulta='" + getIdConsulta() + "'" + ", idArquivo='" + getIdArquivo() + "'" + ", origem='"
				+ getOrigem() + "'" + ", nomeServidor='" + getNomeServidor() + "'" + ", tipoBeneficio='"
				+ getTipoBeneficio() + "'" + ", nomeDependente='" + getNomeDependente() + "'" + ", tipoDependente='"
				+ getTipoDependente() + "'" + ", tipoServidor='" + getTipoServidor() + "'" + ", cpf='" + getCpf() + "'"
				+ ", dataInicioBeneficio='" + getDataInicioBeneficio() + "'" + ", dataInicioPagamento='"
				+ getDataInicioPagamento() + "'" + ", dataCessacao='" + getDataCessacao() + "'" + ", valor='"
				+ getValor() + "'" + ", competencia='" + getCompetencia() + "'" + "}";
	}

}
