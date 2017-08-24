/*package com.lbb.rabbitmq.server.consumer.impl;

import com.lbb.rabbitmq.server.bean.Person;
import com.lbb.rabbitmq.server.consumer.IMessageProcess;
import com.lbb.rabbitmq.server.consumer.Queuename;

@Queuename(name="test_1")
public class MessageProcessTest implements IMessageProcess {

	@Override
	public void process(Object obj) {
		if(obj instanceof Person){
			Person person=(Person)obj;
			System.out.println(person.getName());
			System.out.println(person.getBirth());
		}
        System.out.println("Received <" + obj + ">");
	}

}
*/