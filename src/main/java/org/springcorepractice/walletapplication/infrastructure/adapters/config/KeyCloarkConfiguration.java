package org.springcorepractice.walletapplication.infrastructure.adapters.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;

@Configuration
public class KeyCloarkConfiguration {
    private static final String REALM = "master";
    private static final String CLIENT_ID = "admin-cli";

    @Value("${keycloak.auth.user}")
    private  String KEYCLOAK_USERNAME;

    @Value("${keycloak.auth.password}")
    private String KEYCLOAK_PASSWORD;

    @Value("${keycloak.client.secret}")
    private String CLIENT_SECRET;
    @Value("${keycloak.grant_type}")
    private String GRANT_PASSWORD;

    @Value("${keycloak.server.url}")
    private String serverUrl;



    @Bean
    public Keycloak keycloak() {

        return KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .username(KEYCLOAK_USERNAME)
                .password(KEYCLOAK_PASSWORD)
                .grantType(GRANT_PASSWORD)
                .realm(REALM)
                .clientId(CLIENT_ID)
                .clientSecret(CLIENT_SECRET)
                .build();
    }
}
