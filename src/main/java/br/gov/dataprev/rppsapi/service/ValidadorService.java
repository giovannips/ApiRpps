package br.gov.dataprev.rppsapi.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import br.gov.dataprev.rppsapi.enums.OrigemEnum;
import br.gov.dataprev.rppsapi.enums.TipoServidorEnum;
import br.gov.dataprev.rppsapi.exception.RPPSValidationException;
import br.gov.dataprev.rppsapi.exception.RPPSValidationMessage;

public class ValidadorService {

	private static final int[] pesoCPF = { 11, 10, 9, 8, 7, 6, 5, 4, 3, 2 };
	private static final int[] pesoCNPJ = { 6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };
	private static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	private static Integer QUANT_COLUNAS = 12;

	private static int calcularDigito(String str, int[] peso) {
		int soma = 0;
		for (int indice = str.length() - 1, digito; indice >= 0; indice--) {
			digito = Integer.parseInt(str.substring(indice, indice + 1));
			soma += digito * peso[peso.length - str.length() + indice];
		}
		soma = 11 - soma % 11;
		return soma > 9 ? 0 : soma;
	}

	private static boolean isValidCPF(String cpf) {
		if ((cpf == null) || (cpf.length() != 11))
			return false;

		Integer digito1 = calcularDigito(cpf.substring(0, 9), pesoCPF);
		Integer digito2 = calcularDigito(cpf.substring(0, 9) + digito1, pesoCPF);
		return cpf.equals(cpf.substring(0, 9) + digito1.toString() + digito2.toString());
	}

	private static boolean isValidCNPJ(String cnpj) {
		if ((cnpj == null) || (cnpj.length() != 14))
			return false;

		Integer digito1 = calcularDigito(cnpj.substring(0, 12), pesoCNPJ);
		Integer digito2 = calcularDigito(cnpj.substring(0, 12) + digito1, pesoCNPJ);
		return cnpj.equals(cnpj.substring(0, 12) + digito1.toString() + digito2.toString());
	}

	private static boolean isValidOrigem(Character origem) {
		if (OrigemEnum.valorDe(origem) != null) {
			return true;
		} else {
			return false;
		}
	}

	private static boolean isValidValor(String valor) {
		valor = valor.replace(".", "");
		valor = valor.replace(",", ".");
		valor = valor.trim();

		if (valor.equals("")) {
			return false;
		}
		try {
			new BigDecimal(valor);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private static boolean isValidTipoServidor(Character tipo) {
		{
			if (TipoServidorEnum.valorDe(tipo) != null) {
				return true;
			} else {
				return false;
			}
		}
	}

	private static boolean isValidData(String data) {
		try {
			formatter.parse(data);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	private static boolean isValidNome(String nome) {
		if (nome.length() > 5 && nome.length() < 100) {
			return true;
		}
		return false;
	}

	private static boolean isValidCompetencia (String competencia){


		if (competencia.matches("([0-9]{2})/([0-9]{4})")){
			String cp[] = competencia.split("/");
			Integer mes  = Integer.parseInt(cp[0]);
			Integer ano = Integer.parseInt(cp[1]);
			if (mes <=12 && ano >= 1900 && ano <=2100){
				return true;
			}
		}
		return false;
	}


	public static void validarCampos(String[] linha, Integer numeroLinhaCalculado) throws RPPSValidationException {

		RPPSValidationException ex = new RPPSValidationException();

		Integer numeroLinha = 0;

		// TODO:validar se linha é vazia e se tem o número de campos esperados

		// TODO: validar numeroLinha

		if (org.apache.commons.lang3.StringUtils.isNumeric(linha[0])) {
			numeroLinha = Integer.parseInt(linha[0]);
		} else {
			ex.addMessage(new RPPSValidationMessage("MSG_NUMERO_LINHA_INVALIDO", "MSG_NUMERO_LINHA_INVALIDO",
					numeroLinhaCalculado));
		}

		if (linha.length != QUANT_COLUNAS) {
			ex.addMessage(new RPPSValidationMessage("MSG_QUANT_COLUNAS_INVALIDO", "MSG_QUANT_COLUNAS_INVALIDO",
					numeroLinhaCalculado, linha.length, QUANT_COLUNAS));
		} else {

			String cnpj = linha[1];
			cnpj = cnpj.replaceAll("[^0-9]", "");
			cnpj = String.format("%014d", Long.parseLong(cnpj.toString()));

			Character origem = linha[2].charAt(0);

			String nomeServidor = linha[3];
			nomeServidor = nomeServidor.trim();

			String nomeDependente = linha[4];
			nomeDependente = nomeDependente.trim();

			Character tipoServidor = linha[5].charAt(0);

			String cpf = linha[6];
			cpf = cpf.replaceAll("[^0-9]", "");
			cpf = String.format("%011d", Long.parseLong(cpf.toString()));

			String dataInicioBeneficio = linha[7];

			String dataInicioPagamento = linha[8];

			String dataCessacao = linha[9];

			String valor = linha[10];

			String competencia = linha[11];

			if (!isValidOrigem(origem)) {
				ex.addMessage(
						new RPPSValidationMessage("MSG_ORIGEM_INVALIDA", "MSG_ORIGEM_INVALIDA", numeroLinha, origem));
			}

			if (!isValidCNPJ(cnpj)) {
				ex.addMessage(new RPPSValidationMessage("MSG_CNPJ_INVALIDO", "MSG_CNPJ_INVALIDO", numeroLinha, cnpj));
			}

			if (!isValidCPF(cpf)) {
				ex.addMessage(new RPPSValidationMessage("MSG_CPF_INVALIDO", "MSG_CPF_INVALIDO", numeroLinha, cpf));
			}

			if (!isValidNome(nomeServidor)) {
				ex.addMessage(new RPPSValidationMessage("MSG_NOME_SERVIDOR_INVALIDO", "MSG_NOME_SERVIDOR_INVALIDO",
						numeroLinha, nomeServidor));
			}

			if (nomeDependente.length() > 0 && !isValidNome(nomeDependente)) {
				ex.addMessage(new RPPSValidationMessage("MSG_NOME_SERVIDOR_INVALIDO", "MSG_NOME_SERVIDOR_INVALIDO",
						numeroLinha, nomeDependente));
			}

			if (!isValidTipoServidor(tipoServidor)) {
				ex.addMessage(new RPPSValidationMessage("MSG_TIPO_SERVIDOR_INVALIDO", "MSG_TIPO_SERVIDOR_INVALIDO",
						numeroLinha, tipoServidor));
			}

			if (!isValidData(dataInicioBeneficio)) {
				ex.addMessage(new RPPSValidationMessage("MSG_DATA_INICIO_BENEFICIO_INVALIDA",
						"MSG_DATA_INICIO_BENEFICIO_INVALIDA", numeroLinha, dataInicioBeneficio));
			}

			if (!isValidData(dataInicioPagamento)) {
				ex.addMessage(new RPPSValidationMessage("MSG_DATA_INICIO_PAGAMENTO_INVALIDA",
						"MSG_DATA_INICIO_PAGAMENTO_INVALIDA", numeroLinha, dataInicioPagamento));
			}

			if (!isValidData(dataCessacao)) {
				ex.addMessage(new RPPSValidationMessage("MSG_DATA_CESSACAO_INVALIDA", "MSG_DATA_CESSACAO_INVALIDA",
						numeroLinha, dataCessacao));
			}

			if (!isValidValor(valor)) {
				ex.addMessage(
						new RPPSValidationMessage("MSG_VALOR_INVALIDO", "MSG_VALOR_INVALIDO", numeroLinha, valor));
			}
			if (!isValidCompetencia(competencia)) {
				ex.addMessage(
						new RPPSValidationMessage("MSG_COMPETENCIA_INVALIDA", "MSG_COMPETENCIA_INVALIDA", numeroLinha, competencia));
			}
		}

		if (!ex.isEmpty()) {
			throw ex;
		}

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

		if (numeroRegistrosInformado != quantidadeRegistros) {
			ex.addMessage(new RPPSValidationMessage("MSG_QUANT_REGISTROS_INVALIDO", "MSG_QUANT_REGISTROS_INVALIDO",
					numeroRegistrosInformado, quantidadeRegistros));
			throw ex;
		}
	}

}