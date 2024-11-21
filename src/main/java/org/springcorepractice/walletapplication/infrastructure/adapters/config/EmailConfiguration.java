package org.springcorepractice.walletapplication.infrastructure.adapters.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Properties;
@Configuration
public class EmailConfiguration {

        @Value("${spring.mail.template.path}")
        private String path;
        @Value("${brevo.mail.host}")
        private String host;
        @Value("${brevo.mail.port}")
        private int port;
        @Value("${brevo.mail.username}")
        private String username;
        @Value("${brevo.mail.password}")
        private String password;

        @Bean
        public JavaMailSender javaMailSender() {
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setHost(host);
            mailSender.setPort(port);
            mailSender.setUsername(username);
            mailSender.setPassword(password);

            Properties props = mailSender.getJavaMailProperties();
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.timeout", "5000");
            props.put("mail.smtp.connectiontimeout", "5000");
            props.put("mail.smtp.writetimeout", "5000");
            props.put("mail.smtp.ssl.enable", "true");
            return mailSender;
        }

        @Bean
        public ITemplateResolver thymeleafTemplateResolver() {
            ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
            templateResolver.setPrefix(path);
            templateResolver.setSuffix(".html");
            templateResolver.setTemplateMode("HTML");
            templateResolver.setCharacterEncoding("UTF-8");
            return templateResolver;
        }

        @Bean
        public SpringTemplateEngine thymeleafTemplateEngine(@Qualifier("thymeleafTemplateResolver") ITemplateResolver templateResolver) {
            SpringTemplateEngine templateEngine = new SpringTemplateEngine();
            templateEngine.setTemplateResolver(templateResolver);
            return templateEngine;
        }
    }


