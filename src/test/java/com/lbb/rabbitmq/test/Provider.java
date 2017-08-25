package com.lbb.rabbitmq.test;




import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.lbb.rabbitmq.server.producer.MessageProducer;
import com.lbb.rabbitmq.test.bean.Person;

/**
 * 
 * @描述: 启动Dubbo服务用的MainClass.
 * @作者:
 * @创建时间:
 * @版本: 1.0
 */
public class Provider {

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
			mp.sendMessage("isz.common.mq.test2",person);
			person.setName("hanmeimei");
			mp.sendMessage("isz.common.mq.test3",person);
		} catch (Exception e) {
			//logger.error("== DubboProvider context start error:", e);
			System.out.println(e.getMessage());
		}
	}

}