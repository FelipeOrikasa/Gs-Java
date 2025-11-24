package com.globalsolution.workfuture.service.ai;

import com.globalsolution.workfuture.model.CareerPath;
import com.globalsolution.workfuture.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CareerAIService {
    
    private final Optional<ChatClient.Builder> chatClientBuilder;
    
    public String generateCareerAnalysis(User user, CareerPath careerPath, 
                                        Double compatibilityScore, 
                                        Double futureDemandScore) {
        String userInfo = user != null ? 
            String.format("Profissional: %s, Cargo Atual: %s, Empresa: %s, Experiência: %d anos, Modo de Trabalho: %s",
                user.getFullName(), 
                user.getCurrentPosition() != null ? user.getCurrentPosition() : "Não informado",
                user.getCurrentCompany() != null ? user.getCurrentCompany() : "Não informado",
                user.getYearsOfExperience() != null ? user.getYearsOfExperience() : 0,
                user.getWorkMode() != null ? user.getWorkMode() : "Não informado") :
            "Análise geral de carreira";
        
        String careerInfo = careerPath != null ?
            String.format("Carreira: %s, Tipo: %s, Salário Médio: R$ %.2f, Crescimento: %d%%, Carreira do Futuro: %s",
                careerPath.getTitle(), careerPath.getType(), 
                careerPath.getAverageSalary() != null ? careerPath.getAverageSalary() : 0.0,
                careerPath.getJobGrowth() != null ? careerPath.getJobGrowth() : 0,
                careerPath.getFutureCareer() ? "Sim" : "Não") :
            "Carreira não especificada";
        
        String promptText = String.format(
            "Como especialista em desenvolvimento de carreira e futuro do trabalho, analise os seguintes dados " +
            "e forneça uma análise preditiva completa (máximo 300 palavras) em português brasileiro:\n\n" +
            "%s\n" +
            "%s\n" +
            "Compatibilidade: %.1f%%\n" +
            "Demanda Futura: %.1f%%\n\n" +
            "Forneça:\n" +
            "1. Análise de compatibilidade do profissional com a carreira\n" +
            "2. Recomendações de upskilling/reskilling necessárias\n" +
            "3. Potencial de crescimento e oportunidades\n" +
            "4. Plano de ação para transição de carreira\n" +
            "5. Habilidades prioritárias a desenvolver\n" +
            "6. Riscos e desafios a considerar",
            userInfo, careerInfo, compatibilityScore, futureDemandScore
        );
        
        if (chatClientBuilder.isPresent()) {
            try {
                ChatClient chatClient = chatClientBuilder.get().build();
                String response = chatClient.prompt(promptText).call().content();
                return response.length() > 3000 ? response.substring(0, 3000) : response;
            } catch (Exception e) {
                log.warn("Error calling AI service, using fallback analysis", e);
            }
        }
        
        // Fallback analysis when AI is not available
        return String.format(
            "Análise gerada automaticamente: Com base na compatibilidade de %.1f%% e demanda futura de %.1f%%, " +
            "esta carreira apresenta potencial interessante. Recomenda-se foco em desenvolvimento contínuo de habilidades, " +
            "especialmente em tecnologias emergentes e soft skills. Considere buscar treinamentos certificados e " +
            "experiências práticas para fortalecer seu perfil profissional.",
            compatibilityScore, futureDemandScore
        );
    }
}

