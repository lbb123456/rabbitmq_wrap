package com.lbb.rabbitmq.server.consumer.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.lbb.rabbitmq.server.bean.MessageBody;
import com.lbb.rabbitmq.server.consumer.IMessageProcess;
import com.lbb.rabbitmq.server.consumer.IMessageReceiver;
import com.lbb.rabbitmq.server.util.HessionCodecFactory;

@Component
public class MessageReceiver implements IMessageReceiver{

	private Logger logger = LoggerFactory.getLogger(MessageReceiver.class);
	
	public void receiveMessage(MessageBody message) {
		
		StringBuilder queueBuild=new StringBuilder();
		queueBuild.append(message.getExchangeName());
		queueBuild.append("|");
		queueBuild.append(message.getQueueName());
		IMessageProcess messageProcess=MessageConsumer.consumerMap.get(queueBuild.toString());
		
		Object obj=null;
		try {
			obj=HessionCodecFactory.deSerialize(message.getMessageData());
		} catch (IOException e) {
			logger.error("hession fail: {}",e);
		}
		messageProcess.process(obj);
		
    }
}
