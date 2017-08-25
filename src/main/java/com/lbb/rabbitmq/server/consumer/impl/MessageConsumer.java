package com.lbb.rabbitmq.server.consumer.impl;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.lbb.rabbitmq.server.constant.MessageConstant;
import com.lbb.rabbitmq.server.consumer.IMessageProcess;
import com.lbb.rabbitmq.server.consumer.Queuename;
import com.lbb.rabbitmq.server.manager.MessageCommonConfigure;
import com.lbb.rabbitmq.server.util.PackageUtil;

@Component
@Configuration
@ComponentScan(basePackages = "com.lbb.rabbitmq.server.consumer")
public class MessageConsumer extends MessageCommonConfigure {

	private static final Logger logger = LoggerFactory.getLogger(MessageConsumer.class);

	public static ConcurrentMap<String, IMessageProcess<?>> consumerMap=new ConcurrentHashMap<String, IMessageProcess<?>>();

	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
			MessageListenerAdapter listenerAdapter) throws Exception {

		//初始化容器
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);		
		//查找要往容器添加的队列
		List<String> classNames = PackageUtil.getClassName(MessageConstant.DEFAULT_SCAN_PACKAGE_NAME);
		String queueName=MessageConstant.DEFAULT_QUEUE_NAME;
		for(String className:classNames){
			logger.debug(className);
			try {
				if(Class.forName(className).isAnnotationPresent(Queuename.class)){
					Queuename annotation=(Queuename)Class.forName(className).getAnnotation(Queuename.class);
					IMessageProcess<?> messageProcess=(IMessageProcess<?>) Class.forName(className).newInstance();
					queueName=annotation.name();
					StringBuilder queueBuild=new StringBuilder();
					queueBuild.append(MessageConstant.DEFAULT_EXCHANGE_NAME);
					queueBuild.append("|");
					queueBuild.append(queueName);
					consumerMap.putIfAbsent(queueBuild.toString(), messageProcess);
					initQueus(queueName);
					container.addQueueNames(queueName);;
					logger.debug("queueName: {}",annotation.name());
				}
			} catch (ClassNotFoundException e) {
				logger.error("class load queue fail:{}",e);
				throw new ClassNotFoundException("class load queue fail:",e);
			}
		}
		if(MessageConstant.DEFAULT_QUEUE_NAME.equals(queueName)){
			logger.warn("queueName is required !!");
			initQueus(queueName);
			container.addQueueNames(queueName);;
		}
		container.setMessageListener(listenerAdapter);
		return container;
	}

	@Bean
	MessageListenerAdapter listenerAdapter(MessageReceiver receiver) {
		return new MessageListenerAdapter(receiver, MessageConstant.DEFAULT_RECEIVE_MESSAGE_METHOD);
	}



}
