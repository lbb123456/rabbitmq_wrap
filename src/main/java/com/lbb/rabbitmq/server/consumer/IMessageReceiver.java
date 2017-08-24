package com.lbb.rabbitmq.server.consumer;

public interface IMessageReceiver {

	void receiveMessage(Object message);
}
