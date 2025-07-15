package  com.tiendita.tienditaventas.config;

import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	public static final String MAIN_QUEUE = "ventas";
	public static final String MAIN_EXCHANGE = "ventas-exchange";
	public static final String MAIN_ROUTING_KEY = "ventas.created";
	public static final String DLX_EXCHANGE = "dlx-exchange";
	public static final String DLX_QUEUE = "dlx-queue";
	public static final String DLX_ROUTING_KEY = "dlx-routing-key";

	@Bean
	Jackson2JsonMessageConverter messageConverter() {

		return new Jackson2JsonMessageConverter();
	}

	@Bean
	CachingConnectionFactory connectionFactory() {

		CachingConnectionFactory factory = new CachingConnectionFactory();
		factory.setHost("34.237.247.129");
		factory.setPort(5672);
		factory.setUsername("guest");
		factory.setPassword("guest");
		return factory;
	}

	@Bean
	Queue ventas() {
		return new Queue(MAIN_QUEUE, true, false, false,Map.of("x-dead-letter-exchange", DLX_EXCHANGE, "x-dead-letter-routing-key", DLX_ROUTING_KEY));
	}

	@Bean
	DirectExchange dlxExchange() {
		return new DirectExchange(DLX_EXCHANGE);
	}

	@Bean
	Queue dlxQueue() {
		return new Queue(DLX_QUEUE);
	}

	@Bean
	Binding dlxBinding() {
		return BindingBuilder.bind(dlxQueue()).to(dlxExchange()).with(DLX_ROUTING_KEY);
	}

	@Bean
	DirectExchange ventasExchange() {
		return new DirectExchange(MAIN_EXCHANGE);
	}

	@Bean
	Binding ventasBinding() {
		return BindingBuilder.bind(ventas()).to(ventasExchange()).with(MAIN_ROUTING_KEY);
	}

	@Bean
	RabbitTemplate rabbitTemplate() {
		RabbitTemplate template = new RabbitTemplate(connectionFactory());
		template.setMessageConverter(messageConverter());
		return template;
	}

	@Bean
	RabbitAdmin rabbitAdmin() {
		RabbitAdmin admin = new RabbitAdmin(connectionFactory());
		
		// Declarar explícitamente todos los elementos con logging
		System.out.println("Declarando exchanges y queues...");
		
		admin.declareExchange(ventasExchange());
		System.out.println("Exchange 'ventas-exchange' declarado");
		
		admin.declareExchange(dlxExchange());
		System.out.println("Exchange 'dlx-exchange' declarado");
		
		admin.declareQueue(ventas());
		System.out.println("Queue 'ventas' declarada");
		
		admin.declareQueue(dlxQueue());
		System.out.println("Queue 'dlx-queue' declarada");
		
		admin.declareBinding(ventasBinding());
		System.out.println("Binding ventas-exchange -> ventas declarado");
		
		admin.declareBinding(dlxBinding());
		System.out.println("Binding dlx-exchange -> dlx-queue declarado");
		
		return admin;
	}
}
