package org.springcorepractice.walletapplication.application.output.paymentmanager;

import org.springcorepractice.walletapplication.domain.exceptions.IdentityManagerException;
import org.springcorepractice.walletapplication.infrastructure.adapters.input.rest.data.request.PaystackRequest;
import org.springcorepractice.walletapplication.infrastructure.adapters.input.rest.data.response.PaystackResponse;
import org.springcorepractice.walletapplication.infrastructure.adapters.input.rest.data.response.PaystackVerificationResponse;

public interface PaymentPayManagerOutPutPort {
    PaystackResponse initialisePayment(PaystackRequest paystackRequest) throws IdentityManagerException;

    PaystackVerificationResponse verifyPayment(String reference) throws IdentityManagerException;
}
