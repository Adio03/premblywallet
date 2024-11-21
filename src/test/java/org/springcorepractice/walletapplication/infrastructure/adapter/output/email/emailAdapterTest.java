package org.springcorepractice.walletapplication.infrastructure.adapter.output.email;

import org.springcorepractice.walletapplication.application.output.email.EmailOutputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class emailAdapterTest {
    @Autowired
    private EmailOutputPort emailOutputPort;



}
