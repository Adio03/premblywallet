package org.springcorepractice.walletapplication.infrastructure.adapters.output.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springcorepractice.walletapplication.application.output.email.EmailOutputPort;
import org.springcorepractice.walletapplication.domain.enums.constants.IdentityMessage;
import org.springcorepractice.walletapplication.domain.exceptions.IdentityManagerException;
import org.springcorepractice.walletapplication.domain.model.email.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;

@Slf4j
@RequiredArgsConstructor
public class EmailAdapter implements EmailOutputPort {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    @Value("${FRONTEND_URL}")
    private String link;
    @Value("${brevo.mail.username}")
    private String mailSender;
    @Override
    public void sendMail(Email email) throws IdentityManagerException {
        try {
            String emailContent = templateEngine.process(email.getTemplate(), email.getContext());
            MimeMessage mailMessage = getMimeMessage(email, emailContent);
            mailMessage.setFrom(mailSender);
            javaMailSender.send(mailMessage);
        } catch (MessagingException | MailException | UnsupportedEncodingException exception) {
            throw new IdentityManagerException(exception.getMessage());
        }
    }


    private MimeMessage getMimeMessage(Email email, String emailContent) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mailMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mailMessage, IdentityMessage.ENCODING_VALUE.getMessage());
        mimeMessageHelper.setSubject(email.getSubject());
        mimeMessageHelper.setTo(email.getTo());
        mimeMessageHelper.setFrom(mailSender);
        mimeMessageHelper.setText(emailContent, true);
        log.info("{} ===>",mailMessage);
        log.info("{} ===>",mimeMessageHelper);

        return mailMessage;
    }

    @Override
    public Context getNameAndLinkContext(String link, String firstName){
        Context context = new Context();
        context.setVariable(IdentityMessage.CONTEXT_TOKEN.getMessage(), link);
        context.setVariable(IdentityMessage.CONTEXT_FIRST_NAME.getMessage(), firstName);
        context.setVariable(IdentityMessage.CONTEXT_CURRENT_YEAR.getMessage(), LocalDate.now().getYear());
        return context;
    }
}
