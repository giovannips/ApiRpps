package br.gov.dataprev.insssat.rppsapi.exception;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class RPPSValidationMessage implements Serializable {

	private static final long serialVersionUID = 1L;
	private final String key;
	private final ArrayList<Object> params;
	private final String debugMessage;

	public RPPSValidationMessage(final String key, final String debugMessage, final Object... params) {
		if (key == null || key.equals("")) {
			throw new IllegalArgumentException("key nao pode ser nulo.");
		}

		if (debugMessage == null || debugMessage.equals("")) {
			throw new IllegalArgumentException("message nao pode ser nulo.");
		}

		this.key = key;
		this.params = new ArrayList<Object>(Arrays.asList(params));
		this.debugMessage = debugMessage;
	}

	public String getKey() {
		return this.key;
	}

	public Object[] getParams() {
		return params.toArray();
	}

	public String getDebugMessage() {
		return this.debugMessage;
	}
}
