package org.springcorepractice.walletapplication.domain.model.email;
import lombok.Getter;
import lombok.Setter;
import org.thymeleaf.context.Context;
@Getter
@Setter
public class Email {
    private String email;
    private String firstName;
    private String subject;
    private String htmlContent;
    private String to;
    private String template;
    private Context context;
}
