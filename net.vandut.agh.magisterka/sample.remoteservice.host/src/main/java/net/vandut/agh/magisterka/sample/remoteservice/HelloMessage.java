package net.vandut.agh.magisterka.sample.remoteservice;

import java.io.Serializable;

/**
 * @since 3.0
 */
public class HelloMessage implements Serializable {

	private static final long serialVersionUID = -965328496590880418L;
	
	private String from;
	private String message;
	
	public HelloMessage(String from, String message) {
		this.from = from;
		this.message = message;
	}
	
	public String getFrom() {
		return from;
	}
	
	public String getMessage() {
		return message;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("HelloMessage[from=");
		builder.append(from);
		builder.append(", message=");
		builder.append(message);
		builder.append("]");
		return builder.toString();
	}
	
}
