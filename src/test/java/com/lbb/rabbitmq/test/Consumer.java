package com.lbb.rabbitmq.test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.lbb.rabbitmq.server.consumer.impl.MessageConsumer;

public class Consumer {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		try {

			//ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
			//		"classpath:spring/spring-context.xml");
			new AnnotationConfigApplicationContext(MessageConsumer.class);  
			
			
		} catch (Exception e) {
			//logger.error("== DubboProvider context start error:", e);
			System.out.println(e.getMessage());
		}
		synchronized (Provider.class) {
			while (true) {
				try {
					Provider.class.wait();
				} catch (InterruptedException e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}

}
