package org.springcorepractice.walletapplication.domain.service.wallet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springcorepractice.walletapplication.application.input.wallet.TransactionUseCase;
import org.springcorepractice.walletapplication.application.output.paymentmanager.PaymentPayManagerOutPutPort;
import org.springcorepractice.walletapplication.application.output.wallet.TransactionOutputPort;
import org.springcorepractice.walletapplication.domain.enums.TransactionStatus;
import org.springcorepractice.walletapplication.domain.enums.constants.TransactionMessage;
import org.springcorepractice.walletapplication.domain.exceptions.IdentityManagerException;
import org.springcorepractice.walletapplication.domain.exceptions.TransactionException;
import org.springcorepractice.walletapplication.domain.model.wallet.TransactionIdentity;
import org.springcorepractice.walletapplication.infrastructure.adapters.input.rest.data.request.PaystackRequest;
import org.springcorepractice.walletapplication.infrastructure.adapters.input.rest.data.response.PaystackResponse;
import org.springcorepractice.walletapplication.infrastructure.adapters.input.rest.data.response.PaystackVerificationResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService implements TransactionUseCase {

    private final PaymentPayManagerOutPutPort paymentPayManagerOutPutPort;
    private final TransactionOutputPort transactionOutputPort;

    private static final BigDecimal KOBO_VALUE = new BigDecimal("100");

    @Override
    public TransactionIdentity processTransaction(TransactionIdentity transactionIdentity) throws IdentityManagerException, TransactionException {
        validateTransactionInput(transactionIdentity);
        log.info("Initializing payment for email: {}", transactionIdentity.getUserEmail());

        PaystackRequest paystackRequest = getPaystackRequest(transactionIdentity);
        PaystackResponse paystackResponse = paymentPayManagerOutPutPort.initialisePayment(paystackRequest);
        validatePaystackResponse(paystackResponse);

        TransactionIdentity transaction = transactionIdentityBuilder(transactionIdentity, paystackResponse);

        log.info("Transaction processed successfully: {}", transaction);
        return transactionOutputPort.save(transaction);
    }


    @Override
    public TransactionIdentity verifyTransaction(TransactionIdentity transactionIdentity) throws IdentityManagerException, TransactionException {
        TransactionIdentity transaction = transactionOutputPort.findByReference(transactionIdentity.getPaystackReference());
        PaystackVerificationResponse verificationResponse = paymentPayManagerOutPutPort.verifyPayment(transaction.getPaystackReference());
        validateVerificationResponse(verificationResponse);
        transaction.setAmount(verificationResponse.getData().getAmount());
        transaction.setPaystackVerificationResponse(verificationResponse);
        transaction.setTransactionStatus(verificationResponse.getData().getStatus());
        return transactionOutputPort.save(transaction);
    }

    private static PaystackRequest getPaystackRequest(TransactionIdentity transactionIdentity) {
        return PaystackRequest.builder()
                .email(transactionIdentity.getUserEmail())
                .amount(transactionIdentity.getAmount().multiply(KOBO_VALUE))
                .build();
    }


    private static TransactionIdentity transactionIdentityBuilder(TransactionIdentity transactionIdentity, PaystackResponse paystackResponse) {
        return TransactionIdentity.builder()
                .type(transactionIdentity.getType())
                .walletId(transactionIdentity.getWalletId())
                .transactionStatus(TransactionStatus.PENDING.toString())
                .description(transactionIdentity.getDescription())
                .amount(transactionIdentity.getAmount())
                .paystackReference(paystackResponse.getData().getReference())
                .authorizationUrl(paystackResponse.getData().getAuthorization_url())
                .build();
    }

    private void validatePaystackResponse(PaystackResponse paystackResponse) throws TransactionException {
        if (paystackResponse == null || !paystackResponse.isStatus() || paystackResponse.getData().getAuthorization_url() == null) {
            throw new TransactionException(TransactionMessage.FAIL_TRANSACTION.getMessage());
        }
    }

    private void validateVerificationResponse(PaystackVerificationResponse verificationResponse) throws IdentityManagerException {
        if (verificationResponse == null || !verificationResponse.isStatus()) {
            throw new IdentityManagerException(TransactionMessage.FAIL_TRANSACTION.getMessage());
        }
    }

    @Override
    public TransactionIdentity findById(String transactionIdentity) throws IdentityManagerException {
        return transactionOutputPort.findTransactionById(transactionIdentity);

    }

    private static void validateTransactionInput(TransactionIdentity transactionIdentity) throws TransactionException {
        if (transactionIdentity == null) {
            throw new TransactionException(TransactionMessage.NULL_OBJECT.getMessage());
        }
        validateInput(transactionIdentity.getUserEmail());
        validateInput(String.valueOf(transactionIdentity.getAmount()));
    }

    private static void validateInput(String element) throws TransactionException {
        if(StringUtils.isEmpty(element) || StringUtils.isBlank(element)){
            throw new TransactionException(TransactionMessage.EMPTY_INPUT_FIELD_ERROR.getMessage());
        }
    }
}
