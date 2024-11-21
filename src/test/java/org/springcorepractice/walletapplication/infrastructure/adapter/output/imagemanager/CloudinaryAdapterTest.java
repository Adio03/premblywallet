package org.springcorepractice.walletapplication.infrastructure.adapter.output.imagemanager;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springcorepractice.walletapplication.application.output.ImageManager.ImageManagerOutputPort;
import org.springcorepractice.walletapplication.domain.exceptions.IdentityManagerException;
import org.springcorepractice.walletapplication.infrastructure.utilities.FileConverterTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Slf4j
public class CloudinaryAdapterTest {
@Autowired
    private ImageManagerOutputPort imageManagerOutputPort;
    private static final String T_PAIN = "C:\\Users\\Semicolon\\IdeaProjects\\walletApplication\\src\\main\\resources\\assets\\T pain.jpg";

    private static final String AWWAL = "C:\\Users\\Semicolon\\IdeaProjects\\walletApplication\\src\\main\\resources\\assets\\WhatsApp.jpg";

    private static final String EBUKA= "C:\\Users\\Semicolon\\IdeaProjects\\walletApplication\\src\\main\\resources\\assets\\Ebuka.jpg";

    private static final String DIDDY= "C:\\Users\\Semicolon\\IdeaProjects\\walletApplication\\src\\main\\resources\\assets\\Diddy.jpg";

    @Test
    void uploadImage() throws IdentityManagerException, IOException {

        String response = imageManagerOutputPort.uploadImage(FileConverterTest.getFileTest(AWWAL));
        log.info("AWWAL {}", response);
        assertNotNull(response);
    }
    @Test
    void uploadTPain() throws IdentityManagerException, IOException {
        String response = imageManagerOutputPort.uploadImage(FileConverterTest.getFileTest(T_PAIN));
        log.info("T-pain {}", response);
        assertNotNull(response);
    }
    @Test
    void uploadCat() throws IdentityManagerException, IOException {
        String response = imageManagerOutputPort.uploadImage(FileConverterTest.getFileTest(EBUKA));
        log.info("Ebuke {}", response);
        assertNotNull(response);
    }
    @Test
    void uploadEbuka() throws IdentityManagerException, IOException {
        String response = imageManagerOutputPort.uploadImage(FileConverterTest.getFileTest(DIDDY));
        log.info("Image---> {}", response);
        assertNotNull(response);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {StringUtils.EMPTY, StringUtils.SPACE, "23245"})
    void uploadImageInput(String element){
        assertThrows(IdentityManagerException.class,()-> imageManagerOutputPort.uploadImage(FileConverterTest.getFileTest(element)));

    }




}
