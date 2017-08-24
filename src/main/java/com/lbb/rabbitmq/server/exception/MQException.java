package com.lbb.rabbitmq.server.exception;

public class MQException extends Exception {

	private static final long serialVersionUID = -353381215177462719L;

	public MQException(String msg) {
		super(msg);
	}

	public MQException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public MQException(Throwable cause) {
		super(cause);
	}

}
