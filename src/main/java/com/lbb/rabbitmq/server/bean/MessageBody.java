package com.lbb.rabbitmq.server.bean;

import java.io.Serializable;
import java.util.Arrays;


public class MessageBody implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -183590713561101259L;

	private String queueName;
	
	private String exchangeName;
	
	private byte[] messageData;

	public MessageBody(String queueName, String exchangeName, byte[] messageData) {
		this.queueName = queueName;
		this.exchangeName = exchangeName;
		this.messageData=messageData;
	}
	
	public MessageBody() {
	}	

	public String getQueueName() {
		return queueName;
	}

	public String getExchangeName() {
		return exchangeName;
	}

	public byte[] getMessageData() {
		return messageData;
	}

	@Override
	public String toString() {
		return "MessageBody [queueName=" + queueName + ", exchangeName="
				+ exchangeName + ", messageData=" + Arrays.toString(getMessageData())
				+ "]";
	}

}
