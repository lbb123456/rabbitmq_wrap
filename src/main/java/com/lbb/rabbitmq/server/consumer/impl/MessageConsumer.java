package com.lbb.rabbitmq.server.consumer.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.lbb.rabbitmq.server.consumer.IMessageProcess;
import com.lbb.rabbitmq.server.consumer.Queuename;
import com.lbb.rabbitmq.server.exception.MQException;
import com.lbb.rabbitmq.server.manager.MessageCommonConfigure;
import com.lbb.rabbitmq.server.util.PackageUtil;

@Component
@Configuration
@ComponentScan(basePackages = "com.lbb.rabbitmq.server.consumer")
public class MessageConsumer extends MessageCommonConfigure {

	private Logger logger = LoggerFactory.getLogger(MessageConsumer.class);
	
	public static IMessageProcess messageProcess;
	private static final String DEFAULT_QUEUE_NAME="isz.common.mq.test";

	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
			MessageListenerAdapter listenerAdapter) throws Exception {

		
		List<String> classNames = PackageUtil.getClassName("com");
		String queueNames=DEFAULT_QUEUE_NAME;
		int queueAnnotationCount=0;
		for(String className:classNames){
			logger.debug(className);
			try {
				if(Class.forName(className).isAnnotationPresent(Queuename.class)){
					Queuename annotation=(Queuename)Class.forName(className).getAnnotation(Queuename.class);
					messageProcess=(IMessageProcess) Class.forName(className).newInstance();
					queueNames=annotation.name();
					logger.debug("queueName: {}",annotation.name());
					queueAnnotationCount++;
				}
			} catch (ClassNotFoundException e) {
				logger.error("class load queue fail:{}",e);
				throw new ClassNotFoundException("class load queue fail:",e);
			}
		}
		if(queueAnnotationCount>1){
			logger.warn("queuename of annotation count must 1");
			throw new MQException("queuename of annotation count must 1");
		}
		if(DEFAULT_QUEUE_NAME.equals(queueNames)){
			logger.warn("queueName is required !!");
			//throw new MQException("queueName is required.");
		}
		
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		int queueCount=queueNames.split(",").length;
		logger.debug("queue count {}",queueCount);
		if(queueCount>5){
			logger.error("max support queue count 5");
			throw new MQException("max support queue count 5");
		}
		String queueName_0="",queueName_1="",queueName_2="",queueName_3="";
		if(1==queueCount){
			queueName_0=queueNames.split(",")[0];
			initQueus(queueName_0);
			container.setQueueNames(queueName_0);
		}
		if(2==queueCount){
			queueName_0=queueNames.split(",")[0];
			queueName_1=queueNames.split(",")[1];
			initQueus(queueName_0,queueName_1);
			container.setQueueNames(queueName_0,queueName_1);
		}
		if(3==queueCount){
			queueName_0=queueNames.split(",")[0];
			queueName_1=queueNames.split(",")[1];
			queueName_2=queueNames.split(",")[2];
			initQueus(queueName_0,queueName_1,queueName_2);
			container.setQueueNames(queueName_0,queueName_1,queueName_2);
		}
		if(4==queueCount){
			queueName_0=queueNames.split(",")[0];
			queueName_1=queueNames.split(",")[1];
			queueName_2=queueNames.split(",")[2];
			queueName_3=queueNames.split(",")[3];
			initQueus(queueName_0,queueName_1,queueName_2,queueName_3);
			container.setQueueNames(queueName_0,queueName_1,queueName_2,queueName_3);
		}	
		container.setMessageListener(listenerAdapter);
		return container;

	}

	@Bean
	MessageListenerAdapter listenerAdapter(MessageReceiver receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}


}
