package org.springcorepractice.walletapplication.application.output.identity;

import org.springcorepractice.walletapplication.domain.exceptions.IdentityManagerException;
import org.springcorepractice.walletapplication.domain.model.identity.IdentityVerification;
import org.springcorepractice.walletapplication.infrastructure.adapters.input.rest.data.response.PremblyResponse;

public interface IdentityVerificationManagerOutputPort {

    PremblyResponse verifyIdentity(IdentityVerification identityVerification) throws IdentityManagerException;
}
