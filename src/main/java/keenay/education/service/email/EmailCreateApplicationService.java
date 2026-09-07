package keenay.education.service.email;

import keenay.education.config.RabbitMqConfig;
import keenay.education.dto.email.EmailDTO;
import keenay.education.entity.EmailsUser;
import keenay.education.entity.Users;
import keenay.education.repository.EmailsUserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailCreateApplicationService {
    private final RabbitMqConfig rabbitMqConfig;
    private final RabbitTemplate rabbitTemplate;
    private final EmailsUserRepository emailsUserRepository;

    @Value("${rabbitmq.queue.name}")
    private String nameQueue;

    @Value("${rabbitmq.queue.retry-attempt}")
    private Integer retryAttempt;

    private EmailsUser createEmailsUsers(Users user, String text, String subject) {
        EmailsUser emailsUser = new EmailsUser();
        emailsUser.setUser(user);
        emailsUser.setSubject(subject);
        emailsUser.setText(text);
        emailsUser.setAttempt(retryAttempt);
        return emailsUserRepository.save(emailsUser);
    }

    public void sendEmailFor(Users user, String text, String subject) {
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setUserId(user.getId());
        emailDTO.setEmail(user.getEmail());
        emailDTO.setSubject(subject);
        emailDTO.setText(text);
        emailDTO.setAttempt(retryAttempt);
        emailDTO.setEmailsUserId(createEmailsUsers(user, text, subject).getId());
        rabbitTemplate.convertAndSend(rabbitMqConfig.EMAIL_EXCHANGE, nameQueue, emailDTO);
    }
}
