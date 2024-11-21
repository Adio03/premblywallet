package org.springcorepractice.walletapplication.application.output.identity;

import org.springcorepractice.walletapplication.infrastructure.adapters.input.rest.data.response.PremblyNinResponse;
import org.springcorepractice.walletapplication.infrastructure.adapters.input.rest.data.response.PremblyResponse;

public interface IdentityVerificationOutputPort {
    PremblyNinResponse save(PremblyResponse premblyResponse);
}