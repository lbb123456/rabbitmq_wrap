package com.lbb.rabbitmq.server.manager;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


@Component
@Configuration
public class MessageCommonConfigure {

	private Logger logger = LoggerFactory.getLogger(MessageCommonConfigure.class);

	private static final String exchangeName = "isz-common-exchange";
	private static Set<String> binded = new CopyOnWriteArraySet<String>();
//	private static Set<String> exchanges = new ConcurrentHashSet<String>();

	public void initQueus(String... queueNames) {
//		if (!binded.contains(exchangeName)) {
//			amqpAdmin().declareExchange(exchange());
//		}
		for (String queueName : queueNames) {
			if (!binded.contains(queueName)) {
				createSingleQueue(queueName);
			}
		}

	}

	private void createSingleQueue(String queueName) {
		Queue queue = new Queue(queueName, true, false, false);
		amqpAdmin().declareQueue(queue);
		amqpAdmin().declareBinding(BindingBuilder.bind(queue).to(exchange()).with(queueName));
		binded.add(queueName);
	}

	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory("192.168.15.9");
		logger.debug("...connectionFactory...");
		connectionFactory.setUsername("lbb");
		connectionFactory.setPassword("lbb123");
		connectionFactory.setVirtualHost("lbbHost");
		connectionFactory.setPort(5672);
		return connectionFactory;
	}

	@Bean
	public RabbitTemplate rabbitTemplate() {
		logger.debug("...rabbitTemplate...");
		RabbitTemplate template = new RabbitTemplate(connectionFactory());
		template.setExchange(exchangeName);
		return template;
	}

	@Bean
	public AmqpAdmin amqpAdmin() {
		logger.debug("...amqpAdmin...");
		return new RabbitAdmin(connectionFactory());
	}

	@Bean
	public DirectExchange exchange() {
		logger.debug("...exchange...");
		return new DirectExchange(exchangeName, true, false);
	}

}
