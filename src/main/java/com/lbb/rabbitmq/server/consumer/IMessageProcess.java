package com.lbb.rabbitmq.server.consumer;

public interface IMessageProcess<E> {

	void process(E obj);
}
