package br.gov.dataprev.rppsapi.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.gov.dataprev.rppsapi.enums.OrigemEnum;
import br.gov.dataprev.rppsapi.enums.TipoBeneficioEnum;
import br.gov.dataprev.rppsapi.enums.TipoDependenteEnum;
import br.gov.dataprev.rppsapi.enums.TipoServidorEnum;
import br.gov.dataprev.rppsapi.exception.RPPSValidationException;
import br.gov.dataprev.rppsapi.exception.RPPSValidationMessage;
import br.gov.dataprev.rppsapi.model.LinhaArquivo;

public class ValidadorService {

	private static final int[] pesoCPF = { 11, 10, 9, 8, 7, 6, 5, 4, 3, 2 };
	private static final int[] pesoCNPJ = { 6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };
	private static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	private static Integer QUANT_COLUNAS = 14;

	private static int calcularDigito(String str, int[] peso) {
		int soma = 0;
		for (int indice = str.length() - 1, digito; indice >= 0; indice--) {
			digito = Integer.parseInt(str.substring(indice, indice + 1));
			soma += digito * peso[peso.length - str.length() + indice];
		}
		soma = 11 - soma % 11;
		return soma > 9 ? 0 : soma;
	}

	public static LinhaArquivo validarCampos(String[] linha, Long numeroLinhaCalculado, Long idArquivo)
			throws RPPSValidationException {

		RPPSValidationException ex = new RPPSValidationException();

		if (linha.length != QUANT_COLUNAS) {
			ex.addMessage(new RPPSValidationMessage("MSG_QUANT_COLUNAS_INVALIDO", "MSG_QUANT_COLUNAS_INVALIDO",
					numeroLinhaCalculado, linha.length, QUANT_COLUNAS));
		} else {

			if (!org.apache.commons.lang3.StringUtils.isNumeric(linha[0])
					|| Long.parseLong(linha[0]) != numeroLinhaCalculado.longValue()) {
				ex.addMessage(new RPPSValidationMessage("MSG_NUMERO_LINHA_INVALIDO", "MSG_NUMERO_LINHA_INVALIDO",
						numeroLinhaCalculado));
			}

			// só valida CNPJ
			obterCNPJ(linha[1], numeroLinhaCalculado, ex);

			Long cpf = obterCPF(linha[8], numeroLinhaCalculado, ex);

			Character origem = obterOrigem(linha[2], numeroLinhaCalculado, ex);

			String nomeServidor = obterNome(linha[3], numeroLinhaCalculado, ex, "MSG_NOME_SERVIDOR_INVALIDO", false);

			Character tipoServidor = obterTipoServidor(linha[4], numeroLinhaCalculado, ex);

			Character tipoBeneficio = obterTipoBeneficio(linha[5], numeroLinhaCalculado, ex);

			String nomeDependente = obterNome(linha[6], numeroLinhaCalculado, ex, "MSG_NOME_DEPENDENTE_INVALIDO",
					!TipoBeneficioEnum.valorDe(tipoBeneficio).equals(TipoBeneficioEnum.PENSAO));

			Character tipoDependente = obterTipoDependente(linha[7], numeroLinhaCalculado, ex, tipoBeneficio);

			Date dataInicioBeneficio = obterData(linha[9], numeroLinhaCalculado, ex, false,
					"MSG_DATA_INICIO_BENEFICIO_INVALIDA");

			Date dataInicioPagamento = obterData(linha[10], numeroLinhaCalculado, ex, false,
					"MSG_DATA_INICIO_PAGAMENTO_INVALIDA");

			Date dataCessacao = obterData(linha[11], numeroLinhaCalculado, ex, true, "MSG_DATA_CESSACAO_INVALIDA");

			BigDecimal valor = obterValor(linha[12], numeroLinhaCalculado, ex);

			Integer competencia = obterCompetencia(linha[13], numeroLinhaCalculado, ex);

			if (ex.isEmpty()){
			return new LinhaArquivo(numeroLinhaCalculado, idArquivo, origem, nomeServidor, tipoServidor, tipoBeneficio,
					nomeDependente, tipoDependente, cpf, dataInicioBeneficio, dataInicioPagamento, dataCessacao, valor,
					competencia);
			}
		}
		throw ex;
	}

	private static Long obterCNPJ(String strCNPJ, Long numeroLinha, RPPSValidationException ex) {
		strCNPJ = strCNPJ.replaceAll("[^0-9]", "");
		if (strCNPJ.equals("")) {
			ex.addMessage(new RPPSValidationMessage("MSG_CNPJ_INVALIDO", "MSG_CNPJ_INVALIDO", numeroLinha, strCNPJ));
		} else {
			strCNPJ = String.format("%014d", Long.parseLong(strCNPJ.toString()));
			Integer digito1 = calcularDigito(strCNPJ.substring(0, 12), pesoCNPJ);
			Integer digito2 = calcularDigito(strCNPJ.substring(0, 12) + digito1, pesoCNPJ);
			if (strCNPJ.equals(strCNPJ.substring(0, 12) + digito1.toString() + digito2.toString())) {
				return Long.parseLong(strCNPJ);
			} else {
				ex.addMessage(
						new RPPSValidationMessage("MSG_CNPJ_INVALIDO", "MSG_CNPJ_INVALIDO", numeroLinha, strCNPJ));
			}
		}
		return null;
	}

	private static Long obterCPF(String strCPF, Long numeroLinha, RPPSValidationException ex) {

		strCPF = strCPF.replaceAll("[^0-9]", "");
		if (strCPF.equals("")) {
			ex.addMessage(new RPPSValidationMessage("MSG_CPF_INVALIDO", "MSG_CPF_INVALIDO", numeroLinha, strCPF));
		} else {
			strCPF = String.format("%011d", Long.parseLong(strCPF.toString()));
			Integer digito1 = calcularDigito(strCPF.substring(0, 9), pesoCPF);
			Integer digito2 = calcularDigito(strCPF.substring(0, 9) + digito1, pesoCPF);

			if (strCPF.equals(strCPF.substring(0, 9) + digito1.toString() + digito2.toString())) {
				return Long.parseLong(strCPF);
			} else {
				ex.addMessage(new RPPSValidationMessage("MSG_CPF_INVALIDO", "MSG_CPF_INVALIDO", numeroLinha, strCPF));
			}
		}
		return null;
	}

	private static Character obterOrigem(String strOrigem, Long numeroLinha, RPPSValidationException ex) {
		if (strOrigem.equals("")) {
			ex.addMessage(
					new RPPSValidationMessage("MSG_ORIGEM_INVALIDA", "MSG_ORIGEM_INVALIDA", numeroLinha, strOrigem));
		} else {
			Character origem = strOrigem.charAt(0);
			if (OrigemEnum.valorDe(origem) != null) {
				return origem;
			} else {
				ex.addMessage(new RPPSValidationMessage("MSG_ORIGEM_INVALIDA", "MSG_ORIGEM_INVALIDA", numeroLinha,
						strOrigem));
			}
		}
		return null;
	}

	private static Character obterTipoServidor(String strTipoServidor, Long numeroLinha, RPPSValidationException ex) {
		if (strTipoServidor.equals("")) {
			ex.addMessage(new RPPSValidationMessage("MSG_TIPO_SERVIDOR_INVALIDO", "MSG_TIPO_SERVIDOR_INVALIDO",
					numeroLinha, strTipoServidor));
		} else {
			Character tipoServidor = strTipoServidor.charAt(0);
			if (TipoServidorEnum.valorDe(tipoServidor) != null) {
				return tipoServidor;
			} else {
				ex.addMessage(new RPPSValidationMessage("MSG_TIPO_SERVIDOR_INVALIDO", "MSG_TIPO_SERVIDOR_INVALIDO",
						numeroLinha, strTipoServidor));
			}
		}
		return null;
	}

	private static Character obterTipoBeneficio(String strTipoBeneficio, Long numeroLinha, RPPSValidationException ex) {
		if (strTipoBeneficio.equals("")) {
			ex.addMessage(new RPPSValidationMessage("MSG_TIPO_BENEFICIO_INVALIDO", "MSG_TIPO_BENEFICIO_INVALIDO",
					numeroLinha, strTipoBeneficio));
		} else {
			Character tipoBeneficio = strTipoBeneficio.charAt(0);
			if (TipoBeneficioEnum.valorDe(tipoBeneficio) != null) {
				return tipoBeneficio;
			} else {
				ex.addMessage(new RPPSValidationMessage("MSG_TIPO_BENEFICIO_INVALIDO", "MSG_TIPO_BENEFICIO_INVALIDO",
						numeroLinha, strTipoBeneficio));
			}
		}
		return null;
	}

	private static Character obterTipoDependente(String strTipoDependente, Long numeroLinha, RPPSValidationException ex,
			Character tipoBeneficio) {
		strTipoDependente = strTipoDependente.trim();
		if (strTipoDependente.equals("")) {
			if (TipoBeneficioEnum.valorDe(tipoBeneficio).equals(TipoBeneficioEnum.PENSAO)) {
				ex.addMessage(new RPPSValidationMessage("MSG_TIPO_DEPENDENTE_OBRIGATORIO",
						"MSG_TIPO_DEPENDENTE_OBRIGATORIO", numeroLinha));
			} else {
				return null;
			}
		} else {
			Character tipoDependente = strTipoDependente.charAt(0);
			if (TipoDependenteEnum.valorDe(tipoDependente) != null) {
				return tipoDependente;
			} else {
				ex.addMessage(new RPPSValidationMessage("MSG_TIPO_DEPENDENTE_INVALIDO", "MSG_TIPO_DEPENDENTE_INVALIDO",
						numeroLinha, strTipoDependente));
			}
		}
		return null;
	}

	private static String obterNome(String nome, Long numeroLinha, RPPSValidationException ex, String msg,
			Boolean podeNulo) {
		nome = nome.trim();
		if (podeNulo && nome.equals("")) {
			return null;
		}

		if (nome.length() > 5 && nome.length() < 100) {
			return nome;
		} else {
			ex.addMessage(new RPPSValidationMessage(msg, msg, numeroLinha, nome));
		}
		return null;
	}

	private static Date obterData(String strData, Long numeroLinha, RPPSValidationException ex, Boolean podeNulo,
			String msg) {
		strData = strData.trim();
		if (podeNulo && strData.equals("")) {
			return null;
		}

		try {
			Date data = formatter.parse(strData);
			return data;
		} catch (ParseException e) {
			ex.addMessage(new RPPSValidationMessage(msg, msg, numeroLinha, strData));
			return null;
		}
	}

	private static BigDecimal obterValor(String strValor, Long numeroLinha, RPPSValidationException ex) {
		strValor = strValor.replace(".", "");
		strValor = strValor.replace(",", ".");
		strValor = strValor.trim();

		if (strValor.equals("")) {
			ex.addMessage(new RPPSValidationMessage("MSG_VALOR_NULO", "MSG_VALOR_NULO", numeroLinha));
		}
		try {
			return new BigDecimal(strValor);
		} catch (Exception e) {
			ex.addMessage(new RPPSValidationMessage("MSG_VALOR_INVALIDO", "MSG_VALOR_INVALIDO", numeroLinha, strValor));
		}
		return null;
	}

	private static Integer obterCompetencia(String strCompetencia, Long numeroLinhaCalculado,
			RPPSValidationException ex) {

		if (strCompetencia.matches("([0-9]{2})/([0-9]{4})")) {
			String cp[] = strCompetencia.split("/");
			Integer mes = Integer.parseInt(cp[0]);
			Integer ano = Integer.parseInt(cp[1]);
			if (mes <= 12 && ano >= 1900 && ano <= 2100) {
				String strNovaComp = cp[1] + cp[0];
				return Integer.parseInt(strNovaComp);
			}
		}
		ex.addMessage(new RPPSValidationMessage("MSG_COMPETENCIA_INVALIDA", "MSG_COMPETENCIA_INVALIDA",
				numeroLinhaCalculado, strCompetencia));
		return null;
	}

	public static void validarNumeroLinhas(BufferedReader br, RPPSValidationException ex)
			throws IOException, RPPSValidationException {

		String line = "";
		String lastLine = null;
		// inicializo negativo para retirar a última linha da quantidade
		Integer quantidadeRegistros = -1;
		Integer numeroRegistrosInformado;

		while ((line = br.readLine()) != null) {
			if (!line.equals("")) {
				quantidadeRegistros = quantidadeRegistros + 1;
				lastLine = line;
			}
		}

		if (lastLine != null && org.apache.commons.lang3.StringUtils.isNumeric(lastLine)) {
			numeroRegistrosInformado = Integer.parseInt(lastLine);
		} else {
			ex.addMessage(new RPPSValidationMessage("MSG_SEM_NUMERO_REGISTROS", "MSG_SEM_NUMERO_REGISTROS"));
			throw ex;
		}

		if (numeroRegistrosInformado.intValue() != quantidadeRegistros.intValue()) {
			ex.addMessage(new RPPSValidationMessage("MSG_QUANT_REGISTROS_INVALIDO", "MSG_QUANT_REGISTROS_INVALIDO",
					numeroRegistrosInformado, quantidadeRegistros));
			throw ex;
		}
	}



}