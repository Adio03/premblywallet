package org.springcorepractice.walletapplication.infrastructure.utilities;

import org.junit.jupiter.api.Test;
import org.springcorepractice.walletapplication.infrastructure.exceptions.ImageConverterException;
import org.springcorepractice.walletapplication.infrastructure.utilities.ImageConverter;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ImageConverterTest {

    @Test
    void nullMultipartFileThrowsExceptionTest() {
        assertThrows(ImageConverterException.class, () -> ImageConverter.convertImageToBase64(null));
    }

    @Test
    void throwsExceptionIfNotImageTest() {
        byte[] noImage = "some random string".getBytes();
        MockMultipartFile mockMultipartFile = new MockMultipartFile("mockfile", noImage);
        assertThrows(ImageConverterException.class, () -> ImageConverter.convertImageToBase64(mockMultipartFile));
    }

    @Test
    void throwsExceptionIfFileTypeIsNotImageTest() throws ImageConverterException, IOException {
        MockMultipartFile imageFile = new MockMultipartFile("Our Image", "", "video/3gpp", "noImage".getBytes());
        assertThrows(ImageConverterException.class, () -> ImageConverter.convertImageToBase64(imageFile));
    }

    @Test
    void convertsToBase64IfImageIsValidTest() throws ImageConverterException, IOException {
        String image = "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";
        MockMultipartFile imageFile = new MockMultipartFile("Our Image", "", "image/jpeg", java.util.Base64.getDecoder().decode(image.getBytes()));
        assertEquals(image, ImageConverter.convertImageToBase64(imageFile));
    }
}
