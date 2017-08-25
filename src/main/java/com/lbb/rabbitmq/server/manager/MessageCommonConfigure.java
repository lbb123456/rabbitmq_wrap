package com.lbb.rabbitmq.server.manager;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.regex.Pattern;

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

import com.lbb.rabbitmq.server.constant.MessageConstant;
import com.lbb.rabbitmq.server.exception.MQException;


@Component
@Configuration
public class MessageCommonConfigure {

	private static final Logger logger = LoggerFactory.getLogger(MessageCommonConfigure.class);

	private static final Set<String> binded = new CopyOnWriteArraySet<String>();
//	private static Set<String> exchanges = new ConcurrentHashSet<String>();

	/**
	 * 
	 * @Description: 为统一队列样式需要，方便业务排查
	 * @param destinationName 统一样式isz.*.*.*
	 * 第一个*是应用名，第二个*是模块名,第三个*是方法名,如isz.erp.contract.create
	 * @throws
	 */
	private void validateQueue(String queueName) throws MQException {
		String pattern = "^isz.\\w+.\\w+.\\w+$";
		boolean isMatch = Pattern.matches(pattern, queueName);
		if (!isMatch) {
			logger.error("destinationName统一样式isz.*.*.* 第一个*是应用名，第二个*是模块名,第三个*是方法名,如isz.erp.contract.create");
			throw new MQException("destinationName统一样式isz.*.*.* 第一个*是应用名，第二个*是模块名,第三个*是方法名,如isz.erp.contract.create");
		}
	}
	
	protected void initQueus(String... queueNames) throws MQException {
//		if (!binded.contains(exchangeName)) {
//			amqpAdmin().declareExchange(exchange());
//		}		
		for (String queueName : queueNames) {
			if (!binded.contains(queueName)) {
				validateQueue(queueName);
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
	protected ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory("192.168.15.9");
		logger.debug("...connectionFactory...");
		connectionFactory.setUsername("lbb");
		connectionFactory.setPassword("lbb123");
		connectionFactory.setVirtualHost("lbbHost");
		connectionFactory.setPort(5672);
		return connectionFactory;
	}

	@Bean
	protected RabbitTemplate rabbitTemplate() {
		logger.debug("...rabbitTemplate...");
		RabbitTemplate template = new RabbitTemplate(connectionFactory());
		template.setExchange(MessageConstant.DEFAULT_EXCHANGE_NAME);
		return template;
	}

	@Bean
	protected AmqpAdmin amqpAdmin() {
		logger.debug("...amqpAdmin...");
		return new RabbitAdmin(connectionFactory());
	}

	@Bean
	protected DirectExchange exchange() {
		logger.debug("...exchange...");
		return new DirectExchange(MessageConstant.DEFAULT_EXCHANGE_NAME, true, false);
	}

}
