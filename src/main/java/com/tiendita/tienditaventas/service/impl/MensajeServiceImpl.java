package com.tiendita.tienditaventas.service.impl;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.rabbitmq.client.Channel;

import com.tiendita.tienditaventas.config.RabbitMQConfig;
import  com.tiendita.tienditaventas.service.MensajeService;

@Service
public class MensajeServiceImpl implements MensajeService {

	private final RabbitTemplate rabbitTemplate;

	public MensajeServiceImpl(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	@Override
	public void enviarMensaje(String mensaje) {
		System.out.println("Enviando mensaje: " + mensaje);
		
		// Verificar que el exchange existe antes de enviar
		try {
			rabbitTemplate.convertAndSend(RabbitMQConfig.MAIN_EXCHANGE, RabbitMQConfig.MAIN_ROUTING_KEY, mensaje);
			System.out.println("Mensaje enviado exitosamente al exchange: " + RabbitMQConfig.MAIN_EXCHANGE + " con routing key: " + RabbitMQConfig.MAIN_ROUTING_KEY);
		} catch (Exception e) {
			System.err.println("Error enviando mensaje: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void enviarObjeto(Object objeto) {
		System.out.println("Enviando objeto: " + objeto);
		
		try {
			rabbitTemplate.convertAndSend(RabbitMQConfig.MAIN_EXCHANGE, RabbitMQConfig.MAIN_ROUTING_KEY, objeto);
			System.out.println("Objeto enviado exitosamente al exchange: " + RabbitMQConfig.MAIN_EXCHANGE + " con routing key: " + RabbitMQConfig.MAIN_ROUTING_KEY);
		} catch (Exception e) {
			System.err.println("Error enviando objeto: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/*
//	@RabbitListener(id = "listener-myQueue", queues = RabbitMQConfig.MAIN_QUEUE)
	@Override
	public void recibirMensaje(Object objeto) {
		System.out.println("Mensaje recibido en myQueue: " + objeto);
	}

	@RabbitListener(id = "listener-dlx-queue", queues = RabbitMQConfig.DLX_QUEUE)
	@Override
	public void recibirDeadLetter(Object objeto) {
		System.out.println("Mensaje recibido en DLQ: " + objeto);
	}

	@RabbitListener(id = "listener-myQueue", queues = RabbitMQConfig.MAIN_QUEUE, ackMode = "MANUAL")
	@Override
	public void recibirMensajeConAckManual(Message mensaje, Channel canal) throws IOException {

		try {
			System.out.println("Mensaje recibido: " + new String(mensaje.getBody()));
			Thread.sleep(10000);

			canal.basicAck(mensaje.getMessageProperties().getDeliveryTag(), false);
			System.out.println("Acknowledge OK enviado");
		} catch (Exception e) {
			canal.basicNack(mensaje.getMessageProperties().getDeliveryTag(), false, false);
			System.out.println("Acknowledge NO OK enviado");
		}
	}
		*/
}
