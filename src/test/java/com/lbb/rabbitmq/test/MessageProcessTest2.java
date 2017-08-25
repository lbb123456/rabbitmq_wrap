package com.lbb.rabbitmq.test;

import com.lbb.rabbitmq.server.consumer.IMessageProcess;
import com.lbb.rabbitmq.server.consumer.Queuename;
import com.lbb.rabbitmq.test.bean.Person;

@Queuename(name="isz.common.mq.test2")
public class MessageProcessTest2 implements IMessageProcess<Person> {

	@Override
	public void process(Person person) {
		System.out.println(person.getName());
		System.out.println(person.getBirth());	
        System.out.println("2_Received <" + person + ">");
	}

}
