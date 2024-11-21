package org.springcorepractice.walletapplication.application.output.email;

import org.springcorepractice.walletapplication.domain.exceptions.IdentityManagerException;
import org.springcorepractice.walletapplication.domain.model.email.Email;
import org.thymeleaf.context.Context;

public interface EmailOutputPort {
void sendMail(Email email) throws IdentityManagerException;
Context getNameAndLinkContext(String link, String firstName);
}
