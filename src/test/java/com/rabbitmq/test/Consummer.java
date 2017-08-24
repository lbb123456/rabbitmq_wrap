package com.rabbitmq.test;




import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.lbb.rabbitmq.server.bean.Person;
import com.lbb.rabbitmq.server.producer.MessageProducer;

/**
 * 
 * @描述: 启动Dubbo服务用的MainClass.
 * @作者:
 * @创建时间:
 * @版本: 1.0
 */
public class Consummer {

	//private static Logger logger = LoggerFactory.getLogger(DubboProvider.class); 

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		try {

			//ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
			//		"classpath:spring/spring-context.xml");
			ApplicationContext context=new AnnotationConfigApplicationContext(MessageProducer.class);  
			
			MessageProducer mp=(MessageProducer) context.getBean("messageProducer");
			Person person=new Person();
			person.setAge(1);
			person.setName("lilei");
			person.setBirth(new Date());
			mp.sendMessage("test_0",person);
			mp.sendMessage("test_1","hello world");
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