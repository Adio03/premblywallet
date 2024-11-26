package org.springcorepractice.walletapplication.application.input.identity;

import org.springcorepractice.walletapplication.domain.model.identity.IdentityVerification;
import org.springcorepractice.walletapplication.infrastructure.adapters.input.rest.data.response.premblyresponses.PremblyResponse;

public interface IdentityVerificationUseCase {
    PremblyResponse verifyNinIdentity(IdentityVerification identityVerification);
    PremblyResponse verifyBvnIdentity(IdentityVerification identityVerification);
    PremblyResponse verifyLiveliness();
}
