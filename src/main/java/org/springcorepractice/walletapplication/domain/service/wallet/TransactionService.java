package org.springcorepractice.walletapplication.domain.service.wallet;

import lombok.RequiredArgsConstructor;
import org.springcorepractice.walletapplication.application.input.wallet.TransactionUseCase;
import org.springcorepractice.walletapplication.application.input.wallet.WalletUseCase;
import org.springcorepractice.walletapplication.application.output.identity.UserIdentityOutputPort;
import org.springcorepractice.walletapplication.application.output.paymentmanager.PaymentPayManagerOutPutPort;
import org.springcorepractice.walletapplication.application.output.wallet.TransactionOutputPort;
import org.springcorepractice.walletapplication.application.output.wallet.WalletIdentityOutputPort;
import org.springcorepractice.walletapplication.domain.exceptions.IdentityManagerException;
import org.springcorepractice.walletapplication.domain.model.identity.UserIdentity;
import org.springcorepractice.walletapplication.domain.model.wallet.TransactionIdentity;
import org.springcorepractice.walletapplication.domain.model.wallet.WalletIdentity;
import org.springcorepractice.walletapplication.infrastructure.adapters.input.rest.data.request.PaystackRequest;
import org.springcorepractice.walletapplication.infrastructure.adapters.input.rest.data.response.PaystackResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransactionService implements TransactionUseCase {
    private final PaymentPayManagerOutPutPort paymentPayManagerOutPutPort;
    private final WalletIdentityOutputPort walletIdentityOutputPort;
    private final UserIdentityOutputPort userIdentityOutputPort;
    private final TransactionOutputPort transactionOutputPort;
    private static final BigDecimal KOBO_VALUE = new BigDecimal("100");


    @Override
    public TransactionIdentity processTransaction(TransactionIdentity transactionIdentity) throws IdentityManagerException {
        WalletIdentity walletIdentity = walletIdentityOutputPort.findById(transactionIdentity.getWalletId());
        UserIdentity userIdentity = userIdentityOutputPort.findById(walletIdentity.getUserId());
        PaystackRequest paystackRequest = PaystackRequest.builder().
                email(userIdentity.getEmail()).
                amount(transactionIdentity.getAmount().multiply(new BigDecimal(String.valueOf(KOBO_VALUE)))).build();
        PaystackResponse paystackResponse = paymentPayManagerOutPutPort.initialisePayment(paystackRequest);
        validatePayStackResponse(paystackResponse);

        TransactionIdentity transaction = TransactionIdentity.builder()
                .type(transactionIdentity.getType())
                .walletId(walletIdentity.getId())
                .amount(transactionIdentity.getAmount())
                .description(transactionIdentity.getDescription())
                .paystackReference(paystackResponse.getData().getReference())
                .authorizationUrl(paystackResponse.getData().getAuthorization_url())
                .build();
        transactionOutputPort.save(transaction);
        return transaction;
    }

    private void validatePayStackResponse(PaystackResponse paystackResponse) throws IdentityManagerException {
        if(paystackResponse == null
                || !paystackResponse.isStatus()
                || paystackResponse.getData().getAuthorization_url() == null){
            throw new IdentityManagerException("Payment initialization failed with Paystack.");
        }
    }
}