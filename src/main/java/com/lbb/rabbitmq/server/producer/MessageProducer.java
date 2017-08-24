package com.lbb.rabbitmq.server.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.lbb.rabbitmq.server.manager.MessageCommonConfigure;
import com.lbb.rabbitmq.server.util.HessionCodecFactory;

@Component
@Configuration
@ComponentScan(basePackages = "com.lbb.rabbitmq.server.producer")
public class MessageProducer extends MessageCommonConfigure{

	private Logger logger = LoggerFactory.getLogger(MessageProducer.class);

	public void sendMessage(String queueName,Object message) throws Exception {			
		initQueus(queueName);
		logger.info("to send message:{}", message);
		rabbitTemplate().convertAndSend(queueName, HessionCodecFactory.serialize(message));
	}



}
