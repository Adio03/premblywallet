package org.springcorepractice.walletapplication.domain.service.identity;

import org.springcorepractice.walletapplication.application.input.identity.IdentityVerificationUseCase;
import org.springcorepractice.walletapplication.domain.model.identity.IdentityVerification;
import org.springcorepractice.walletapplication.infrastructure.adapters.input.rest.data.response.premblyresponses.PremblyResponse;
import org.springframework.stereotype.Service;

@Service
public class IdentityVerificationService implements IdentityVerificationUseCase {

    @Override
    public PremblyResponse verifyNinIdentity(IdentityVerification identityVerification) {
        return null;
    }

    @Override
    public PremblyResponse verifyBvnIdentity(IdentityVerification identityVerification) {
        return null;
    }

    @Override
    public PremblyResponse verifyLiveliness() {
        return null;
    }
}
