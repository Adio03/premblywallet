package org.springcorepractice.walletapplication.infrastructure.enums.constants;

import lombok.Getter;

@Getter
public enum ControllerConstant {
    RESPONSE_IS_SUCCESSFUL("Response is successful"),
    DELETED_SUCCESSFULLY("Deleted successfully");
    private final String message;

    ControllerConstant(String message) {
        this.message = message;
    }
}
