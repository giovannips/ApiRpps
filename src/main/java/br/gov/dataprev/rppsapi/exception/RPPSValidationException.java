package br.gov.dataprev.rppsapi.exception;

import java.util.ArrayList;
import java.util.List;

public class RPPSValidationException extends Exception {

	private static final long serialVersionUID = 1L;

	private final List<RPPSValidationMessage> messages = new ArrayList<RPPSValidationMessage>();

	public RPPSValidationException() {
		super();
	}

	public RPPSValidationException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public RPPSValidationException(String message) {
		super(message);
	}

	public RPPSValidationException(final String key, final String debugMessage, final Object... params) {
		super(debugMessage);
		addMessage(key, debugMessage, params);
	}

	public void addMessage(final String key, final String debugMessage, final Object... params) {
		final RPPSValidationMessage vm = new RPPSValidationMessage(key, debugMessage, params);
		this.messages.add(vm);
	}

	public void addMessage(final RPPSValidationMessage validationMessage) {
		this.messages.add(validationMessage);
	}

	public void addMessage(final String debugMessage) {
		final RPPSValidationMessage vm = new RPPSValidationMessage(debugMessage, debugMessage, new Object[] {});
		this.messages.add(vm);
	}

	public boolean isEmpty() {
		return this.messages.isEmpty();
	}

	public List<RPPSValidationMessage> getMessages() {
		return this.messages;
	}

	public String getMessage() {
		String mensagemRetorno = (null == super.getMessage()) ? "" : super.getMessage();
		if (!mensagemRetorno.isEmpty()) {
			return mensagemRetorno;
		}
		for (RPPSValidationMessage mensagem : this.messages) {
			mensagemRetorno = mensagemRetorno + mensagem.getDebugMessage() + "; ";
		}
		return mensagemRetorno;
	}

}
