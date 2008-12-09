package org.emftext.runtime.resource.impl;

import org.eclipse.emf.common.util.URI;

public class PositionBasedTextDiagnosticImpl extends AbstractTextDiagnostic {

	private final URI uri;

	protected int column;
	protected int line;
	protected int charStart;
	protected int charEnd;
	protected String message;

	protected PositionBasedTextDiagnosticImpl(URI uri, String message,
			int column, int line, int charStart, int charEnd, TextDiagnosticType type) {
		
		super(type);

		this.uri = uri;
		this.column = column;
		this.line = line;
		this.charStart = charStart;
		this.charEnd = charEnd;
		this.message = message;
	}

	public int getCharStart() {
		return charStart;
	}

	public int getCharEnd() {
		return charEnd;
	}

	public int getColumn() {
		return column;
	}

	public int getLine() {
		return line;
	}

	public String getLocation() {
		return uri.toString();
	}

	public String getMessage() {
		return message;
	}
}