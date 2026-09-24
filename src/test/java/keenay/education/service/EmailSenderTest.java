package keenay.education.service;

import keenay.education.service.email.EmailSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailSenderTest {

    private static final String FROM = "noreply@example.com";
    private static final String TO = "user@example.com";
    private static final String SUBJECT = "Subject";
    private static final String TEXT = "Hello";

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailSender emailSender;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(emailSender, "fromEmail", FROM);
    }

    @Test
    @DisplayName("Тест sendSimpleEmail")
    public void testSendSimpleEmailSuccess() {
        emailSender.sendSimpleEmail(TO, SUBJECT, TEXT);

        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender).send(captor.capture());
        SimpleMailMessage message = captor.getValue();
        assertEquals(FROM, message.getFrom());
        assertArrayEquals(new String[]{TO}, message.getTo());
        assertEquals(SUBJECT, message.getSubject());
        assertEquals(TEXT, message.getText());
    }

    @Test
    @DisplayName("Тест sendSimpleEmail ошибка")
    public void testSendSimpleEmailThrowsException() {
        doThrow(new RuntimeException("Mail error")).when(mailSender).send(any(SimpleMailMessage.class));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> emailSender.sendSimpleEmail(TO, SUBJECT, TEXT));

        assertTrue(exception.getMessage().contains("Failed to send email"));
        verify(mailSender).send(any(SimpleMailMessage.class));
    }
}