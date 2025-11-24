package com.globalsolution.workfuture.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    
    public static final String TRAINING_QUEUE = "workfuture.training.queue";
    public static final String WELLBEING_QUEUE = "workfuture.wellbeing.queue";
    public static final String PREDICTION_QUEUE = "workfuture.prediction.queue";
    public static final String NOTIFICATION_QUEUE = "workfuture.notification.queue";
    
    @Bean
    public Queue trainingQueue() {
        return new Queue(TRAINING_QUEUE, true);
    }
    
    @Bean
    public Queue wellbeingQueue() {
        return new Queue(WELLBEING_QUEUE, true);
    }
    
    @Bean
    public Queue predictionQueue() {
        return new Queue(PREDICTION_QUEUE, true);
    }
    
    @Bean
    public Queue notificationQueue() {
        return new Queue(NOTIFICATION_QUEUE, true);
    }
    
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
    
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jsonMessageConverter());
        return factory;
    }
}

