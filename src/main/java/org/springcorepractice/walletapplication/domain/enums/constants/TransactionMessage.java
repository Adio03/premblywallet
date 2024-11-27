package org.springcorepractice.walletapplication.domain.enums.constants;

import lombok.Getter;

@Getter
public enum TransactionMessage {
    FAIL_TRANSACTION("Payment initialization failed with Paystack."),
    NULL_OBJECT("The object is null"),
    EMPTY_INPUT_FIELD_ERROR("field cannot be null or empty"),
    AMOUNT_CAN_NOT_BE_NULL("Amount cannot be null"),
    AMOUNT_CAN_NOT_EMPTY("Amount cannot be empty or contain only spaces"),
    AMOUNT_CAN_NOT_BE_ZERO("Amount cannot be less than zero");

    private final String message;

    TransactionMessage(String message){
        this.message = message;
    }
}
