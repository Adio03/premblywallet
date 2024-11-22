package org.springcorepractice.walletapplication.domain.service.identityverification;

import lombok.extern.slf4j.Slf4j;
import org.springcorepractice.walletapplication.application.input.identity.IdentityVerificationUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class IdentitiyVerificationServiceTest {
    @Autowired
    private IdentityVerificationUseCase verificationUseCase;
}
