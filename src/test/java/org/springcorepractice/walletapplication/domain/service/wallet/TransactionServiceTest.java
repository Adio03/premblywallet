package org.springcorepractice.walletapplication.domain.service.wallet;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springcorepractice.walletapplication.domain.enums.constants.IdentityMessage;
import org.springcorepractice.walletapplication.domain.enums.constants.TransactionMessage;
import org.springcorepractice.walletapplication.domain.exceptions.IdentityManagerException;
import org.springcorepractice.walletapplication.domain.exceptions.TransactionException;
import org.springcorepractice.walletapplication.domain.model.wallet.TransactionIdentity;
import org.springcorepractice.walletapplication.infrastructure.adapters.input.rest.data.request.PaystackRequest;
import org.springcorepractice.walletapplication.infrastructure.adapters.input.rest.data.response.PaystackResponse;
import org.springcorepractice.walletapplication.application.output.paymentmanager.PaymentPayManagerOutPutPort;
import org.springcorepractice.walletapplication.application.output.wallet.TransactionOutputPort;

import java.math.BigDecimal;
@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private PaymentPayManagerOutPutPort paymentPayManagerOutPutPort;
    @Mock
    private TransactionOutputPort transactionOutputPort;

    @InjectMocks
    private TransactionService transactionService;
    private TransactionIdentity transactionIdentity;
    private PaystackResponse paystackResponse;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        transactionIdentity = TransactionIdentity.builder().
                userEmail("test@example.com").
                amount(new BigDecimal("1000")).build();

        PaystackResponse.PaystackResponses responses = new PaystackResponse.PaystackResponses();
        responses.setReference("abcd1234");
        responses.setAccess_code("1234567");
        responses.setAuthorization_url("https://paystack.com/authorization");

        paystackResponse = new PaystackResponse();
        paystackResponse.setStatus(true);
        paystackResponse.setData(responses);
    }

    @Test
    public void testProcessTransactionValid() throws IdentityManagerException, TransactionException {
        when(paymentPayManagerOutPutPort.initialisePayment(any(PaystackRequest.class))).thenReturn(paystackResponse);
        when(transactionOutputPort.save(any(TransactionIdentity.class))).thenReturn(transactionIdentity);

        TransactionIdentity processTransaction = transactionService.processTransaction(transactionIdentity);
        assertNotNull(processTransaction);
        assertEquals("test@example.com", processTransaction.getUserEmail());
        verify(paymentPayManagerOutPutPort, times(1)).initialisePayment(any(PaystackRequest.class));
        verify(transactionOutputPort, times(1)).save(any(TransactionIdentity.class));
    }

    @Test
    public void testProcessTransaction_PaystackResponseFailure() throws IdentityManagerException, TransactionException {
        PaystackResponse paystackResponse = new PaystackResponse();
        paystackResponse.setStatus(false);

        when(paymentPayManagerOutPutPort.initialisePayment(any(PaystackRequest.class))).thenReturn(paystackResponse);
        TransactionException thrown = assertThrows(TransactionException.class, () -> {
            transactionService.processTransaction(transactionIdentity);
        });
        assertEquals(TransactionMessage.FAIL_TRANSACTION.getMessage(), thrown.getMessage());
        verify(paymentPayManagerOutPutPort, times(1)).initialisePayment(any(PaystackRequest.class));
        verify(transactionOutputPort, never()).save(any(TransactionIdentity.class));
    }
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {StringUtils.EMPTY,StringUtils.SPACE})
    void invalidEmailTest(String email) {
        transactionIdentity.setUserEmail(email);
        TransactionException exception =
                assertThrows(TransactionException.class,()-> transactionService.processTransaction(transactionIdentity));
        assertEquals(TransactionMessage.EMPTY_INPUT_FIELD_ERROR.getMessage(),exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"-1.0", "-0.01"})
    void invalidAmount(String amount) throws IdentityManagerException, TransactionException {
        transactionIdentity.setAmount(new BigDecimal(amount));
        doThrow(new TransactionException(TransactionMessage.AMOUNT_CAN_NOT_BE_ZERO.getMessage()))
                .when(transactionService)
                .processTransaction(transactionIdentity);
        TransactionException exception =
                assertThrows(TransactionException.class,()-> transactionService.processTransaction(transactionIdentity));
        assertEquals(TransactionMessage.AMOUNT_CAN_NOT_BE_ZERO.getMessage(),exception.getMessage());
    }

}
