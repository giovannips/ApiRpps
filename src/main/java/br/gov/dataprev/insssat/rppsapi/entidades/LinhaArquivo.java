package br.gov.dataprev.insssat.rppsapi.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import com.opencsv.bean.CsvIgnore;

import br.gov.dataprev.insssat.rppsapi.enums.OrigemEnum;
import br.gov.dataprev.insssat.rppsapi.enums.TipoBeneficioEnum;
import br.gov.dataprev.insssat.rppsapi.enums.TipoDependenteEnum;
import br.gov.dataprev.insssat.rppsapi.enums.TipoServidorEnum;

@Entity
@Table(name = "arquivos_linhas")
@IdClass(IdsLinhasArquivos.class)
public class LinhaArquivo implements Serializable {

	private static final long serialVersionUID = 1L;

//	@CsvBindByPosition(position = 0)
	@CsvIgnore
	@Id
	@Column(name = "id_linha")
	private Long idLinha;

	@CsvIgnore
	@Id
	@Column(name = "id_arquivo")
	private Long idArquivo;
	
	@Column(name = "nu_cnpj")
	private Long cnpj;

	@Column(name = "in_origem")
	private Character origem;

	@Column(name = "in_tipo_servidor")
	private Character tipoServidor;

	@Column(name = "nu_cpf_servidor")
	private Long cpfServidor;

	@Column(name = "nm_servidor")
	private String nomeServidor;

	@Column(name = "in_tipo_beneficio")
	private Character tipoBeneficio;

	@Column(name = "nu_beneficio")
	private String numeroBeneficio;

	@Column(name = "in_tipo_dependente")
	private Character tipoDependente;

	@Column(name = "nu_cpf_dependente")
	private Long cpfDependente;

	@Column(name = "nm_dependente")
	private String nomeDependente;

//	@CsvBindByPosition(position = 1)
	@CsvDate(value = "dd/MM/yyyy")
	@Column(name = "dt_inicio_beneficio")
	private Date dataInicioBeneficio;

	@CsvDate(value = "dd/MM/yyyy")
	@Column(name = "dt_inicio_pagamento")
	private Date dataInicioPagamento;

	@CsvDate(value = "dd/MM/yyyy")
	@Column(name = "dt_cessacao")
	private Date dataCessacao;

	@Column(name = "nu_valor_beneficio")
	private BigDecimal valorBeneficio;

	@Column(name = "nu_valor_salario")
	private BigDecimal valorSalario;

	@Column(name = "nu_competencia")
	private Integer competencia;
	
	
	@SuppressWarnings("unused")
	private String valorOrigem;
	

	@SuppressWarnings("unused")
	private String valorTipoServidor;
	

	@SuppressWarnings("unused")
	private String valorTipoBeneficio;
	

	@SuppressWarnings("unused")
	private String valorTipoDependente;

	public LinhaArquivo() {
		// propositadamente deixado em branco
	}

	public LinhaArquivo(Long idLinha, Long idArquivo, Long cnpj, Character origem, Character tipoServidor, Long cpfServidor,
			String nomeServidor, Character tipoBeneficio, String numeroBeneficio, Character tipoDependente,
			Long cpfDependente, String nomeDependente, Date dataInicioBeneficio, Date dataInicioPagamento,
			Date dataCessacao, BigDecimal valorBeneficio, BigDecimal valorSalario, Integer competencia) {
		this.idLinha = idLinha;
		this.idArquivo = idArquivo;
		this.cnpj = cnpj;
		this.origem = origem;
		this.tipoServidor = tipoServidor;
		this.cpfServidor = cpfServidor;
		this.nomeServidor = nomeServidor;
		this.tipoBeneficio = tipoBeneficio;
		this.numeroBeneficio = numeroBeneficio;
		this.tipoDependente = tipoDependente;
		this.cpfDependente = cpfDependente;
		this.nomeDependente = nomeDependente;
		this.dataInicioBeneficio = dataInicioBeneficio;
		this.dataInicioPagamento = dataInicioPagamento;
		this.dataCessacao = dataCessacao;
		this.valorBeneficio = valorBeneficio;
		this.valorSalario = valorSalario;
		this.competencia = competencia;
	}

	public Long getCnpj() {
		return cnpj;
	}

	public void setCnpj(Long cnpj) {
		this.cnpj = cnpj;
	}

	public Long getIdLinha() {
		return this.idLinha;
	}

	public void setIdLinha(Long idLinha) {
		this.idLinha = idLinha;
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

	public Character getTipoServidor() {
		return this.tipoServidor;
	}

	public void setTipoServidor(Character tipoServidor) {
		this.tipoServidor = tipoServidor;
	}

	public Long getCpfServidor() {
		return this.cpfServidor;
	}

	public void setCpfServidor(Long cpfServidor) {
		this.cpfServidor = cpfServidor;
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

	public String getNumeroBeneficio() {
		return this.numeroBeneficio;
	}

	public void setNumeroBeneficio(String numeroBeneficio) {
		this.numeroBeneficio = numeroBeneficio;
	}

	public Character getTipoDependente() {
		return this.tipoDependente;
	}

	public void setTipoDependente(Character tipoDependente) {
		this.tipoDependente = tipoDependente;
	}

	public Long getCpfDependente() {
		return this.cpfDependente;
	}

	public void setCpfDependente(Long cpfDependente) {
		this.cpfDependente = cpfDependente;
	}

	public String getNomeDependente() {
		return this.nomeDependente;
	}

	public void setNomeDependente(String nomeDependente) {
		this.nomeDependente = nomeDependente;
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

	public BigDecimal getValorBeneficio() {
		return this.valorBeneficio;
	}

	public void setValorBeneficio(BigDecimal valorBeneficio) {
		this.valorBeneficio = valorBeneficio;
	}

	public BigDecimal getValorSalario() {
		return this.valorSalario;
	}

	public void setValorSalario(BigDecimal valorSalario) {
		this.valorSalario = valorSalario;
	}

	public Integer getCompetencia() {
		return this.competencia;
	}

	public void setCompetencia(Integer competencia) {
		this.competencia = competencia;
	}

	public String getValorOrigem() {
		if (OrigemEnum.valorDe(this.origem) != null) {
			return OrigemEnum.valorDe(this.origem).name();
		}
		return null;
	}

	public void setValorOrigem(String valorOrigem) {
		this.valorOrigem = valorOrigem;
	}

	public String getValorTipoServidor() {
		if (TipoServidorEnum.valorDe(this.tipoServidor) != null) {
			return TipoServidorEnum.valorDe(this.tipoServidor).name();			
		}
		return null;
	}

	public void setValorTipoServidor(String valorTipoServidor) {
		this.valorTipoServidor = valorTipoServidor;
	}

	public String getValorTipoBeneficio() {
		if (TipoBeneficioEnum.valorDe(this.tipoBeneficio) != null) {
			return TipoBeneficioEnum.valorDe(this.tipoBeneficio).name();
		}
		return null;
	}

	public void setValorTipoBeneficio(String valorTipoBeneficio) {
		this.valorTipoBeneficio = valorTipoBeneficio;
	}

	public String getValorTipoDependente() {
		if (TipoDependenteEnum.valorDe(this.tipoDependente) != null) {
			return TipoDependenteEnum.valorDe(this.tipoDependente).name();
		}
		return null;
	}

	public void setValorTipoDependente(String valorTipoDependente) {
		this.valorTipoDependente = valorTipoDependente;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof LinhaArquivo)) {
			return false;
		}
		LinhaArquivo linhaArquivo = (LinhaArquivo) o;
		return Objects.equals(idLinha, linhaArquivo.idLinha) && Objects.equals(idArquivo, linhaArquivo.idArquivo)
				&& Objects.equals(cnpj, linhaArquivo.cnpj)
				&& Objects.equals(origem, linhaArquivo.origem)
				&& Objects.equals(tipoServidor, linhaArquivo.tipoServidor)
				&& Objects.equals(cpfServidor, linhaArquivo.cpfServidor)
				&& Objects.equals(nomeServidor, linhaArquivo.nomeServidor)
				&& Objects.equals(tipoBeneficio, linhaArquivo.tipoBeneficio)
				&& Objects.equals(numeroBeneficio, linhaArquivo.numeroBeneficio)
				&& Objects.equals(tipoDependente, linhaArquivo.tipoDependente)
				&& Objects.equals(cpfDependente, linhaArquivo.cpfDependente)
				&& Objects.equals(nomeDependente, linhaArquivo.nomeDependente)
				&& Objects.equals(dataInicioBeneficio, linhaArquivo.dataInicioBeneficio)
				&& Objects.equals(dataInicioPagamento, linhaArquivo.dataInicioPagamento)
				&& Objects.equals(dataCessacao, linhaArquivo.dataCessacao)
				&& Objects.equals(valorBeneficio, linhaArquivo.valorBeneficio)
				&& Objects.equals(valorSalario, linhaArquivo.valorSalario)
				&& Objects.equals(competencia, linhaArquivo.competencia);
	}

	@Override
	public int hashCode() {
		return Objects.hash(idLinha, idArquivo, cnpj, origem, tipoServidor, cpfServidor, nomeServidor, tipoBeneficio,
				numeroBeneficio, tipoDependente, cpfDependente, nomeDependente, dataInicioBeneficio,
				dataInicioPagamento, dataCessacao, valorBeneficio, valorSalario, competencia);
	}

	@Override
	public String toString() {
		return "{" + " idLinha='" + getIdLinha() + "'" + ", idArquivo='" + getIdArquivo() + "'" + ", cnpj='" + getCnpj() + "'" + ", origem='"
				+ getOrigem() + "'" + ", tipoServidor='" + getTipoServidor() + "'" + ", cpfServidor='"
				+ getCpfServidor() + "'" + ", nomeServidor='" + getNomeServidor() + "'" + ", tipoBeneficio='"
				+ getTipoBeneficio() + "'" + ", numeroBeneficio='" + getNumeroBeneficio() + "'" + ", tipoDependente='"
				+ getTipoDependente() + "'" + ", cpfDependente='" + getCpfDependente() + "'" + ", nomeDependente='"
				+ getNomeDependente() + "'" + ", dataInicioBeneficio='" + getDataInicioBeneficio() + "'"
				+ ", dataInicioPagamento='" + getDataInicioPagamento() + "'" + ", dataCessacao='" + getDataCessacao()
				+ "'" + ", valorBeneficio='" + getValorBeneficio() + "'" + ", valorSalario='" + getValorSalario() + "'"
				+ ", competencia='" + getCompetencia() + "'" + "}";
	}

}
