package org.springcorepractice.walletapplication.application.output.identity;

import org.springcorepractice.walletapplication.domain.exceptions.IdentityManagerException;
import org.springcorepractice.walletapplication.domain.exceptions.IdentityVerificationException;
import org.springcorepractice.walletapplication.domain.model.identity.IdentityVerification;
import org.springcorepractice.walletapplication.infrastructure.adapters.input.rest.data.response.premblyresponses.PremblyResponse;

public interface IdentityVerificationManagerOutputPort {

    PremblyResponse verifyIdentity( IdentityVerification identityVerification) throws IdentityManagerException, IdentityVerificationException;

    PremblyResponse verifyLiveliness(IdentityVerification identityVerification);

    PremblyResponse verifyBvnLikeness(IdentityVerification identityVerification) throws IdentityManagerException, IdentityVerificationException;
    PremblyResponse verifyNinLikeness(IdentityVerification identityVerification) throws IdentityManagerException, IdentityVerificationException;
}
