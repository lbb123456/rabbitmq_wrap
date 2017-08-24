package com.rabbitmq.test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.lbb.rabbitmq.server.consumer.impl.MessageConsumer;

public class Provider {

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
		synchronized (Consummer.class) {
			while (true) {
				try {
					Consummer.class.wait();
				} catch (InterruptedException e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}

}
