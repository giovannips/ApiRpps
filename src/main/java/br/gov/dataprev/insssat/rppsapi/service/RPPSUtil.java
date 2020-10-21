package br.gov.dataprev.insssat.rppsapi.service;

import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import org.apache.tomcat.util.codec.binary.Base64;

class RPPSUtil {

	private RPPSUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static String descompacta(String expressao) throws DataFormatException {
		byte[] result = null;
		int faltante = -1;
		StringBuilder sb = new StringBuilder();
		byte[] aSerDescompactado = Base64.decodeBase64(expressao.getBytes());

		String s;
		Inflater decompresser = new Inflater();
		decompresser.setInput(aSerDescompactado);

		while (true) {
			result = new byte[1024];
			faltante = decompresser.inflate(result);
			if (faltante == 0) {
				break;
			}

			s = new String(result);
			sb.append(s);
		}
		decompresser.end();
	
	return sb.toString().trim();
}}
