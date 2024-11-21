package org.springcorepractice.walletapplication.infrastructure.enums.prembly;

import lombok.Getter;

@Getter
    public enum PremblyParameter {
    NIN_URL("/vnin"),
    APP_ID("app-id"),
    API_KEY("x-api-key"),
    ACCEPT("accept"),
    BVN_NUMBER("number"),
    NIN_NUMBER("number"),
    APPLICATION_JSON("application/json"),
    NIN_FACE_URL("/nin_w_face"),
    NIN_IMAGE("image");

    private final String value;

    PremblyParameter(String value) {
        this.value = value;
    }


}