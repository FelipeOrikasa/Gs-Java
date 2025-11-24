package com.globalsolution.workfuture.service.messaging;

import com.globalsolution.workfuture.dto.CareerPredictionDTO;
import com.globalsolution.workfuture.dto.TrainingDTO;
import com.globalsolution.workfuture.dto.WellbeingDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import static com.globalsolution.workfuture.config.RabbitMQConfig.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkFutureMessageConsumer {
    
    @RabbitListener(queues = TRAINING_QUEUE)
    public void processTraining(TrainingDTO training) {
        log.info("Processing training message: {} - {} hours", 
            training.getTitle(), training.getDurationHours());
        // Aqui você pode adicionar lógica de processamento assíncrono
        // como atualização de estatísticas, notificações, etc.
    }
    
    @RabbitListener(queues = WELLBEING_QUEUE)
    public void processWellbeing(WellbeingDTO wellbeing) {
        log.info("Processing wellbeing message: User {} - Mental Health Score: {}", 
            wellbeing.getUserId(), wellbeing.getMentalHealthScore());
        // Processar dados de bem-estar assincronamente
    }
    
    @RabbitListener(queues = PREDICTION_QUEUE)
    public void processPrediction(CareerPredictionDTO prediction) {
        log.info("Processing prediction message: {} - Compatibility: {}%", 
            prediction.getType(), prediction.getCompatibilityScore());
    }
    
    @RabbitListener(queues = NOTIFICATION_QUEUE)
    public void processNotification(String message) {
        log.info("Processing notification: {}", message);
    }
}

