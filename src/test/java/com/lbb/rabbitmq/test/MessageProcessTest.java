package com.lbb.rabbitmq.test;

import com.lbb.rabbitmq.server.consumer.IMessageProcess;
import com.lbb.rabbitmq.server.consumer.Queuename;
import com.lbb.rabbitmq.test.bean.Person;

@Queuename(name="isz.common.mq.test3")
public class MessageProcessTest implements IMessageProcess<Person> {

	@Override
	public void process(Person person) {
		System.out.println(person.getName());
		System.out.println(person.getBirth());		
        System.out.println("1_Received <" + person + ">");
	}

}
