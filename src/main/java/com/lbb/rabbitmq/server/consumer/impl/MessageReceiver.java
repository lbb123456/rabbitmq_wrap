package com.lbb.rabbitmq.server.consumer.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.lbb.rabbitmq.server.consumer.IMessageReceiver;
import com.lbb.rabbitmq.server.util.HessionCodecFactory;

@Component
public class MessageReceiver implements IMessageReceiver{

	private Logger logger = LoggerFactory.getLogger(MessageReceiver.class);
	
	public void receiveMessage(Object message) {
		try {
			message=HessionCodecFactory.deSerialize((byte[])message);
		} catch (IOException e) {
			logger.error("hession fail: {}",e);
		}
		MessageConsumer.messageProcess.process(message);
		
    }
}
