package br.gov.dataprev.rppsapi.service;

public class ValidadorService {

	   private static final int[] pesoCPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
	   private static final int[] pesoCNPJ = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

	   private static int calcularDigito(String str, int[] peso) {
	      int soma = 0;
	      for (int indice=str.length()-1, digito; indice >= 0; indice-- ) {
	         digito = Integer.parseInt(str.substring(indice,indice+1));
	         soma += digito*peso[peso.length-str.length()+indice];
	      }
	      soma = 11 - soma % 11;
	      return soma > 9 ? 0 : soma;
	   }

	   private static boolean isValidCPF(String cpf) {
	      if ((cpf==null) || (cpf.length()!=11)) return false;

	      Integer digito1 = calcularDigito(cpf.substring(0,9), pesoCPF);
	      Integer digito2 = calcularDigito(cpf.substring(0,9) + digito1, pesoCPF);
	      return cpf.equals(cpf.substring(0,9) + digito1.toString() + digito2.toString());
	   }

	   private static boolean isValidCNPJ(String cnpj) {
	      if ((cnpj==null)||(cnpj.length()!=14)) return false;

	      Integer digito1 = calcularDigito(cnpj.substring(0,12), pesoCNPJ);
	      Integer digito2 = calcularDigito(cnpj.substring(0,12) + digito1, pesoCNPJ);
	      return cnpj.equals(cnpj.substring(0,12) + digito1.toString() + digito2.toString());
	   }
	   
	   
	   public static void validarCampos(String[] campos) {
		   Boolean invalido = false;
		   
		   if (campos[0] == null) {invalido = true;};
		   if (campos[1] == null) {invalido = true;};
		   if (campos[2] == null) {invalido = true;};
		   if (campos[3] == null) {invalido = true;};
		   if (campos[4] == null) {invalido = true;};
		   if (campos[5] == null) {invalido = true;};
		   if (campos[6] == null) {invalido = true;};
		   if (campos[7] == null) {invalido = true;};
		   if (campos[8] == null) {invalido = true;};
		   if (campos[9] == null) {invalido = true;};
		   if (campos[10] == null) {invalido = true;};
		   
		   isValidCNPJ(campos[0]);
		   isValidCPF(campos[5]);
		   
	   }
	   
}