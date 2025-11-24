package com.globalsolution.workfuture.config;

import com.globalsolution.workfuture.model.*;
import com.globalsolution.workfuture.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    
    private final UserRepository userRepository;
    private final SkillRepository skillRepository;
    private final CareerPathRepository careerPathRepository;
    private final TrainingRepository trainingRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            log.info("Initializing default data...");
            
            // Admin user
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@workfuture.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setFullName("Administrador");
            admin.setCurrentPosition("CTO");
            admin.setCurrentCompany("WorkFuture Inc");
            admin.setYearsOfExperience(10);
            admin.setWorkMode(User.WorkMode.HYBRID);
            admin.setRoles(Set.of(User.Role.ROLE_ADMIN, User.Role.ROLE_USER));
            admin.setActive(true);
            userRepository.save(admin);
            
            // HR user
            User hr = new User();
            hr.setUsername("hruser");
            hr.setEmail("hr@workfuture.com");
            hr.setPassword(passwordEncoder.encode("hr123"));
            hr.setFullName("Recursos Humanos");
            hr.setCurrentPosition("HR Manager");
            hr.setCurrentCompany("WorkFuture Inc");
            hr.setYearsOfExperience(5);
            hr.setWorkMode(User.WorkMode.REMOTE);
            hr.setRoles(Set.of(User.Role.ROLE_HR, User.Role.ROLE_USER));
            hr.setActive(true);
            userRepository.save(hr);
            
            // Regular user
            User user = new User();
            user.setUsername("user");
            user.setEmail("user@workfuture.com");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setFullName("Usuário");
            user.setCurrentPosition("Desenvolvedor");
            user.setCurrentCompany("Tech Corp");
            user.setYearsOfExperience(3);
            user.setWorkMode(User.WorkMode.REMOTE);
            user.setRoles(Set.of(User.Role.ROLE_USER));
            user.setActive(true);
            userRepository.save(user);
            
            // ========== HABILIDADES TÉCNICAS ==========
            Skill java = createSkill("Java", "Programação orientada a objetos com Java", 
                Skill.SkillCategory.TECHNOLOGY, Skill.SkillLevel.ADVANCED, true, true);
            
            Skill spring = createSkill("Spring Framework", "Framework Spring para desenvolvimento Java enterprise", 
                Skill.SkillCategory.TECHNOLOGY, Skill.SkillLevel.ADVANCED, true, true);
            
            Skill python = createSkill("Python", "Linguagem de programação para ciência de dados e IA", 
                Skill.SkillCategory.TECHNOLOGY, Skill.SkillLevel.INTERMEDIATE, true, true);
            
            Skill ai = createSkill("Inteligência Artificial", "Conhecimento em IA, Machine Learning e Deep Learning", 
                Skill.SkillCategory.AI_ML, Skill.SkillLevel.INTERMEDIATE, true, true);
            
            Skill machineLearning = createSkill("Machine Learning", "Algoritmos de aprendizado de máquina e modelos preditivos", 
                Skill.SkillCategory.AI_ML, Skill.SkillLevel.ADVANCED, true, true);
            
            Skill dataScience = createSkill("Data Science", "Análise de dados, estatística e visualização", 
                Skill.SkillCategory.DATA_ANALYTICS, Skill.SkillLevel.INTERMEDIATE, true, true);
            
            Skill cloudComputing = createSkill("Cloud Computing", "AWS, Azure, GCP e arquitetura em nuvem", 
                Skill.SkillCategory.TECHNOLOGY, Skill.SkillLevel.INTERMEDIATE, true, true);
            
            Skill devops = createSkill("DevOps", "CI/CD, Docker, Kubernetes e automação", 
                Skill.SkillCategory.TECHNOLOGY, Skill.SkillLevel.ADVANCED, true, true);
            
            Skill cybersecurity = createSkill("Cybersecurity", "Segurança da informação e proteção de dados", 
                Skill.SkillCategory.CYBERSECURITY, Skill.SkillLevel.INTERMEDIATE, true, true);
            
            Skill react = createSkill("React", "Framework JavaScript para desenvolvimento frontend", 
                Skill.SkillCategory.TECHNOLOGY, Skill.SkillLevel.INTERMEDIATE, true, true);
            
            Skill nodejs = createSkill("Node.js", "JavaScript no servidor e desenvolvimento backend", 
                Skill.SkillCategory.TECHNOLOGY, Skill.SkillLevel.INTERMEDIATE, true, true);
            
            // ========== SOFT SKILLS ==========
            Skill communication = createSkill("Comunicação", "Comunicação eficaz verbal e escrita", 
                Skill.SkillCategory.COMMUNICATION, Skill.SkillLevel.INTERMEDIATE, true, true);
            
            Skill leadership = createSkill("Liderança", "Liderança de equipes e gestão de pessoas", 
                Skill.SkillCategory.LEADERSHIP, Skill.SkillLevel.INTERMEDIATE, true, true);
            
            Skill criticalThinking = createSkill("Pensamento Crítico", "Análise crítica e resolução de problemas complexos", 
                Skill.SkillCategory.SOFT_SKILLS, Skill.SkillLevel.ADVANCED, true, true);
            
            Skill creativity = createSkill("Criatividade", "Inovação e pensamento criativo", 
                Skill.SkillCategory.SOFT_SKILLS, Skill.SkillLevel.INTERMEDIATE, true, true);
            
            Skill collaboration = createSkill("Colaboração", "Trabalho em equipe e colaboração remota", 
                Skill.SkillCategory.SOFT_SKILLS, Skill.SkillLevel.ADVANCED, true, true);
            
            Skill adaptability = createSkill("Adaptabilidade", "Adaptação a mudanças e aprendizado contínuo", 
                Skill.SkillCategory.SOFT_SKILLS, Skill.SkillLevel.ADVANCED, true, true);
            
            Skill emotionalIntelligence = createSkill("Inteligência Emocional", "Gestão de emoções e relacionamentos", 
                Skill.SkillCategory.SOFT_SKILLS, Skill.SkillLevel.INTERMEDIATE, true, true);
            
            // ========== CARREIRAS DO FUTURO ==========
            CareerPath dataScientist = createCareerPath("Cientista de Dados", 
                "Carreira em ciência de dados, análise estatística e machine learning. Trabalha com grandes volumes de dados para extrair insights valiosos.",
                CareerPath.CareerType.DATA_SCIENTIST, 3, 12000.0, 35, true,
                Set.of(python, dataScience, machineLearning, ai));
            
            CareerPath aiSpecialist = createCareerPath("Especialista em IA", 
                "Especialização em Inteligência Artificial, desenvolvimento de modelos de IA e implementação de soluções inteligentes.",
                CareerPath.CareerType.AI_SPECIALIST, 5, 15000.0, 40, true,
                Set.of(ai, machineLearning, python, criticalThinking));
            
            CareerPath cloudArchitect = createCareerPath("Arquiteto de Cloud", 
                "Projeta e implementa soluções em nuvem, garantindo escalabilidade, segurança e performance.",
                CareerPath.CareerType.TECHNOLOGY, 4, 14000.0, 30, true,
                Set.of(cloudComputing, devops, cybersecurity));
            
            CareerPath cybersecurityExpert = createCareerPath("Especialista em Cibersegurança", 
                "Protege sistemas e dados contra ameaças cibernéticas, essencial em um mundo cada vez mais digital.",
                CareerPath.CareerType.TECHNOLOGY, 4, 13000.0, 32, true,
                Set.of(cybersecurity, cloudComputing, criticalThinking));
            
            CareerPath fullStackDeveloper = createCareerPath("Desenvolvedor Full Stack", 
                "Desenvolve aplicações completas, do frontend ao backend, com conhecimento em múltiplas tecnologias.",
                CareerPath.CareerType.TECHNOLOGY, 2, 10000.0, 25, true,
                Set.of(java, spring, react, nodejs, collaboration));
            
            CareerPath techLeader = createCareerPath("Líder Técnico", 
                "Lidera equipes de desenvolvimento, define arquiteturas e estratégias tecnológicas.",
                CareerPath.CareerType.TECHNOLOGY, 6, 18000.0, 20, true,
                Set.of(leadership, java, spring, cloudComputing, communication));
            
            CareerPath sustainabilityManager = createCareerPath("Gestor de Sustentabilidade", 
                "Implementa práticas sustentáveis em empresas, essencial para o futuro do planeta.",
                CareerPath.CareerType.SUSTAINABILITY, 3, 11000.0, 28, true,
                Set.of(leadership, communication, criticalThinking, creativity));
            
            CareerPath digitalTransformation = createCareerPath("Consultor de Transformação Digital", 
                "Ajuda empresas a se adaptarem à era digital, implementando tecnologias e mudanças organizacionais.",
                CareerPath.CareerType.BUSINESS, 4, 13500.0, 33, true,
                Set.of(cloudComputing, leadership, communication, adaptability));
            
            CareerPath aiEthicist = createCareerPath("Especialista em Ética em IA", 
                "Garante que a IA seja desenvolvida e utilizada de forma ética e responsável.",
                CareerPath.CareerType.AI_SPECIALIST, 5, 14500.0, 38, true,
                Set.of(ai, criticalThinking, emotionalIntelligence, communication));
            
            CareerPath remoteWorkSpecialist = createCareerPath("Especialista em Trabalho Remoto", 
                "Otimiza processos e cultura para ambientes de trabalho remoto e híbrido.",
                CareerPath.CareerType.BUSINESS, 2, 9500.0, 42, true,
                Set.of(collaboration, communication, adaptability, leadership));
            
            // ========== TREINAMENTOS DE UPSKILLING/RESKILLING ==========
            createTraining("Spring Boot Completo", 
                "Curso completo de Spring Boot do básico ao avançado, incluindo Spring Security, JPA e REST APIs.",
                Training.TrainingType.UPSKILLING, Training.TrainingFormat.ONLINE, 40, "Alura", 
                "https://www.alura.com.br/curso-online-spring-boot", 299.90, false, true, spring, fullStackDeveloper, 1250, 4.8);
            
            createTraining("Machine Learning com Python", 
                "Aprenda Machine Learning do zero usando Python, scikit-learn e TensorFlow.",
                Training.TrainingType.RESKILLING, Training.TrainingFormat.ONLINE, 60, "Coursera", 
                "https://www.coursera.org/machine-learning", 0.0, true, true, machineLearning, dataScientist, 5000, 4.9);
            
            createTraining("Certificação AWS Cloud Practitioner", 
                "Prepare-se para a certificação AWS e domine os fundamentos da computação em nuvem.",
                Training.TrainingType.UPSKILLING, Training.TrainingFormat.ONLINE, 20, "AWS Training", 
                "https://aws.amazon.com/training", 0.0, true, true, cloudComputing, cloudArchitect, 3200, 4.7);
            
            createTraining("Bootcamp Full Stack JavaScript", 
                "Bootcamp intensivo de 12 semanas para se tornar desenvolvedor Full Stack.",
                Training.TrainingType.RESKILLING, Training.TrainingFormat.BOOTCAMPS, 480, "Trybe", 
                "https://www.betrybe.com", 0.0, true, true, react, fullStackDeveloper, 850, 4.6);
            
            createTraining("Liderança Ágil e Gestão de Equipes", 
                "Desenvolva habilidades de liderança para times ágeis e remotos.",
                Training.TrainingType.SOFT_SKILLS, Training.TrainingFormat.HYBRID, 16, "FIAP", 
                "https://www.fiap.com.br", 450.00, false, true, leadership, techLeader, 320, 4.8);
            
            createTraining("Cybersecurity Essentials", 
                "Fundamentos de segurança da informação e proteção contra ameaças cibernéticas.",
                Training.TrainingType.UPSKILLING, Training.TrainingFormat.ONLINE, 30, "Cisco Networking Academy", 
                "https://www.netacad.com", 0.0, true, true, cybersecurity, cybersecurityExpert, 2100, 4.5);
            
            createTraining("Inteligência Artificial Generativa", 
                "Domine ChatGPT, DALL-E e outras ferramentas de IA generativa para aplicações práticas.",
                Training.TrainingType.UPSKILLING, Training.TrainingFormat.ONLINE, 25, "Udemy", 
                "https://www.udemy.com/ia-generativa", 89.90, false, true, ai, aiSpecialist, 1800, 4.7);
            
            createTraining("Data Science com Python e Pandas", 
                "Análise de dados, visualização e estatística usando Python.",
                Training.TrainingType.RESKILLING, Training.TrainingFormat.SELF_PACED, 50, "DataCamp", 
                "https://www.datacamp.com", 29.90, false, true, dataScience, dataScientist, 950, 4.6);
            
            createTraining("Comunicação Eficaz no Trabalho Remoto", 
                "Melhore sua comunicação em ambientes virtuais e remotos.",
                Training.TrainingType.SOFT_SKILLS, Training.TrainingFormat.ONLINE, 8, "LinkedIn Learning", 
                "https://www.linkedin.com/learning", 0.0, true, false, communication, remoteWorkSpecialist, 1200, 4.4);
            
            createTraining("DevOps com Docker e Kubernetes", 
                "Aprenda a containerizar aplicações e orquestrar com Kubernetes.",
                Training.TrainingType.UPSKILLING, Training.TrainingFormat.ONLINE, 35, "Pluralsight", 
                "https://www.pluralsight.com", 0.0, true, true, devops, cloudArchitect, 750, 4.8);
            
            createTraining("Pensamento Crítico e Resolução de Problemas", 
                "Desenvolva habilidades de análise crítica e resolução de problemas complexos.",
                Training.TrainingType.SOFT_SKILLS, Training.TrainingFormat.ONLINE, 12, "Coursera", 
                "https://www.coursera.org/critical-thinking", 0.0, true, true, criticalThinking, aiEthicist, 600, 4.5);
            
            createTraining("Transformação Digital para Líderes", 
                "Como liderar transformações digitais em organizações.",
                Training.TrainingType.LEADERSHIP, Training.TrainingFormat.HYBRID, 24, "MIT Sloan", 
                "https://executive.mit.edu", 2500.00, false, true, leadership, digitalTransformation, 180, 4.9);
            
            createTraining("React Avançado e Hooks", 
                "Domine React avançado, Hooks, Context API e performance optimization.",
                Training.TrainingType.UPSKILLING, Training.TrainingFormat.ONLINE, 18, "Rocketseat", 
                "https://www.rocketseat.com.br", 0.0, true, true, react, fullStackDeveloper, 2100, 4.7);
            
            createTraining("Ética em Inteligência Artificial", 
                "Entenda os desafios éticos da IA e como desenvolver soluções responsáveis.",
                Training.TrainingType.TECHNICAL, Training.TrainingFormat.ONLINE, 15, "edX", 
                "https://www.edx.org", 0.0, true, true, ai, aiEthicist, 450, 4.6);
            
            createTraining("Sustentabilidade Corporativa", 
                "Implemente práticas sustentáveis e ESG em organizações.",
                Training.TrainingType.LEADERSHIP, Training.TrainingFormat.ONLINE, 20, "Harvard Business School", 
                "https://www.hbs.edu", 1800.00, false, true, leadership, sustainabilityManager, 280, 4.8);
            
            log.info("Default data created successfully! Skills: {}, Career Paths: {}, Trainings: {}", 
                skillRepository.count(), careerPathRepository.count(), trainingRepository.count());
        }
    }
    
    private Skill createSkill(String name, String description, Skill.SkillCategory category, 
                              Skill.SkillLevel level, boolean inDemand, boolean futureProof) {
        Skill skill = new Skill();
        skill.setName(name);
        skill.setDescription(description);
        skill.setCategory(category);
        skill.setLevel(level);
        skill.setInDemand(inDemand);
        skill.setFutureProof(futureProof);
        return skillRepository.save(skill);
    }
    
    private CareerPath createCareerPath(String title, String description, CareerPath.CareerType type,
                                       int estimatedYears, double averageSalary, int jobGrowth, 
                                       boolean futureCareer, Set<Skill> requiredSkills) {
        CareerPath careerPath = new CareerPath();
        careerPath.setTitle(title);
        careerPath.setDescription(description);
        careerPath.setType(type);
        careerPath.setEstimatedYears(estimatedYears);
        careerPath.setAverageSalary(averageSalary);
        careerPath.setJobGrowth(jobGrowth);
        careerPath.setFutureCareer(futureCareer);
        careerPath.setRequiredSkills(requiredSkills);
        return careerPathRepository.save(careerPath);
    }
    
    private Training createTraining(String title, String description, Training.TrainingType type,
                                   Training.TrainingFormat format, int durationHours, String provider,
                                   String url, double price, boolean isFree, boolean isCertified,
                                   Skill skill, CareerPath careerPath, int enrollmentCount, double averageRating) {
        Training training = new Training();
        training.setTitle(title);
        training.setDescription(description);
        training.setType(type);
        training.setFormat(format);
        training.setDurationHours(durationHours);
        training.setProvider(provider);
        training.setUrl(url);
        training.setPrice(price);
        training.setIsFree(isFree);
        training.setIsCertified(isCertified);
        training.setSkill(skill);
        training.setCareerPath(careerPath);
        training.setEnrollmentCount(enrollmentCount);
        training.setAverageRating(averageRating);
        return trainingRepository.save(training);
    }
}

