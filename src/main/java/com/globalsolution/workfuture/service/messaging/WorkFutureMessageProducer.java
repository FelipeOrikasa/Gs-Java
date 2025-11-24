package com.globalsolution.workfuture.service.messaging;

import com.globalsolution.workfuture.dto.CareerPredictionDTO;
import com.globalsolution.workfuture.dto.TrainingDTO;
import com.globalsolution.workfuture.dto.WellbeingDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static com.globalsolution.workfuture.config.RabbitMQConfig.*;

@Service
@RequiredArgsConstructor
public class WorkFutureMessageProducer {
    
    private final RabbitTemplate rabbitTemplate;
    
    public void sendTrainingMessage(TrainingDTO training) {
        rabbitTemplate.convertAndSend(TRAINING_QUEUE, training);
    }
    
    public void sendWellbeingMessage(WellbeingDTO wellbeing) {
        rabbitTemplate.convertAndSend(WELLBEING_QUEUE, wellbeing);
    }
    
    public void sendPredictionMessage(CareerPredictionDTO prediction) {
        rabbitTemplate.convertAndSend(PREDICTION_QUEUE, prediction);
    }
    
    public void sendNotification(String message) {
        rabbitTemplate.convertAndSend(NOTIFICATION_QUEUE, message);
    }
}

