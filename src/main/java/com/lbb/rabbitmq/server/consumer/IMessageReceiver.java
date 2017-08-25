package com.lbb.rabbitmq.server.consumer;

import com.lbb.rabbitmq.server.bean.MessageBody;

public interface IMessageReceiver {

	void receiveMessage(MessageBody message);
}
