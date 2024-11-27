package org.springcorepractice.walletapplication.infrastructure.adapter.output.paymentmanager;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.springcorepractice.walletapplication.application.output.paymentmanager.PaymentPayManagerOutPutPort;
import org.springcorepractice.walletapplication.domain.enums.constants.TransactionMessage;
import org.springcorepractice.walletapplication.domain.exceptions.IdentityManagerException;
import org.springcorepractice.walletapplication.domain.exceptions.TransactionException;
import org.springcorepractice.walletapplication.infrastructure.adapters.input.rest.data.request.PaystackRequest;
import org.springcorepractice.walletapplication.infrastructure.adapters.input.rest.data.response.PaystackResponse;
import org.springcorepractice.walletapplication.infrastructure.adapters.input.rest.data.response.PaystackVerificationResponse;
import org.springcorepractice.walletapplication.infrastructure.adapters.output.paymentmanager.PaystackAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
@Slf4j
class PaystackAdapterTest {
    @Autowired
    private PaymentPayManagerOutPutPort paymentPayManagerOutPutPort;
    @Mock
    private PaystackAdapter paystackAdapter;

    private PaystackRequest paystackRequest;

    @BeforeEach
    void setUp() {
        paystackRequest = PaystackRequest.builder().email("exceptionaloriented@gmail.com").
                amount(new BigDecimal("10000").multiply(new BigDecimal("100"))).build();
    }

    @Test
    void initializePayment() throws IdentityManagerException, TransactionException {

            PaystackResponse paystackResponse = paymentPayManagerOutPutPort.initialisePayment(paystackRequest);
            log.info("RESPONSE---> {}", paystackResponse);
            assertNotNull(paystackResponse);
            assertNotNull(paystackResponse.getData());

    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {
            StringUtils.SPACE,
            StringUtils.EMPTY,
            "email@gmail.com.com",
            "123456",
            "  email@gmail.com",
            "email@gmail.com  ",
            "  email@gmail.com  "
    })
    void initializePaymentEmailValidation(String email) throws IdentityManagerException, TransactionException {
        paystackRequest.setEmail(email);
        doThrow(new IdentityManagerException("Invalid email format"))
                .when(paystackAdapter).initialisePayment(paystackRequest);
        assertThrows(IdentityManagerException.class, () ->
                paystackAdapter.initialisePayment(paystackRequest)
        );
    }

    @Test
    void verifyPayment() throws IdentityManagerException {

        String reference = "ii9bpgjx13";
        PaystackVerificationResponse paystackVerificationResponse = paymentPayManagerOutPutPort.verifyPayment(reference);
        assertNotNull(paystackVerificationResponse);
        log.info("OverAll {}", paystackVerificationResponse);
        assertNotNull(paystackVerificationResponse.getData());
        log.info("OverAll {}", paystackVerificationResponse.getData());
        assertNotNull(paystackVerificationResponse.getData().getAuthorization());
        log.info("OverAll {}", paystackVerificationResponse.getData().getAuthorization());
        assertNotNull(paystackVerificationResponse.getData().getCustomer());
        log.info("OverAll {}", paystackVerificationResponse.getData().getCustomer());
        assertNotNull(paystackVerificationResponse.getData().getLog());


    }
    @ParameterizedTest
    @ValueSource(strings = {"-1.0", "-0.01"})
    void validAmount(String amount) throws IdentityManagerException, TransactionException {
        paystackRequest.setAmount(new BigDecimal(amount));
        doThrow(new TransactionException(TransactionMessage.AMOUNT_CAN_NOT_BE_ZERO.getMessage())).
                when(paystackAdapter).initialisePayment(paystackRequest);
        TransactionException exception =
                assertThrows(TransactionException.class,()-> paystackAdapter.initialisePayment(paystackRequest));
        assertEquals(TransactionMessage.AMOUNT_CAN_NOT_BE_ZERO.getMessage(),exception.getMessage());
    }


    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {StringUtils.EMPTY, StringUtils.SPACE, "  reference  ", "reference   ", "  reference"})
    void verifyPaymentValidation(String refernce) throws IdentityManagerException {
        doThrow(new IdentityManagerException("Invalid email format"))
                .when(paystackAdapter).verifyPayment(refernce);

        assertThrows(IdentityManagerException.class, () ->
                paystackAdapter.verifyPayment(refernce)
        );
    }
}



