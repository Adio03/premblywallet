package org.springcorepractice.walletapplication.domain.enums.constants;

import lombok.Getter;

@Getter
public enum IdentityMessage {
    USER_IDENTITY_CANNOT_BE_NULL("User identity cannot be null"),
    EMPTY_INPUT_FIELD_ERROR("field cannot be null or empty"),
    INVALID_EMAIL_ADDRESS("email address is not valid"),
    PASSWORD_PATTERN("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$"),
    WEAK_PASSWORD("Password should be up to 8 characters and must contain at least 1 alphabet, number and special characters."),
    INVALID_VALID_ROLE("Invalid role for the user"),
    USER_IDENTITY_ALREADY_EXISTS("UserIdentity already exists"),
    USER_NOT_FOUND("User not found"),
    INVALID_OBJECT("Object not found"),
    INVALID_EMAIL_OR_PASSWORD("Invalid email or password"),
    INVALID_FILE_PATH("In valid file path"),
    INVALID_CONTENT_TYPE("Invalid content type"),
    INVALID_REFERENCE("reference can not be null or empty"),
    WALLET_NOT_FOUND("wallet not found"),
    AMOUNT_CAN_NOT_BE_NULL("Amount cannot be null"),
    AMOUNT_CAN_NOT_EMPTY("Amount cannot be empty or contain only spaces"),
    AMOUNT_CAN_NOT_BE_ZERO("Amount cannot be less than zero"),
    CONTEXT_TOKEN("token"),
    CONTEXT_FIRST_NAME("firstName"),
    CONTEXT_CURRENT_YEAR("currentYear"),
    ENCODING_VALUE("utf-8"),
    WALLET_ALREADY_EXIST("wallet already exist");

    private final String message;

    IdentityMessage(String message){
        this.message = message;
    }
}
