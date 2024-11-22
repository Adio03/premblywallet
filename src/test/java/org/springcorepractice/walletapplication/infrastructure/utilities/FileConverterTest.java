package org.springcorepractice.walletapplication.infrastructure.utilities;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springcorepractice.walletapplication.domain.exceptions.IdentityManagerException;
import org.springcorepractice.walletapplication.domain.validator.IdentityValidator;
import org.springcorepractice.walletapplication.infrastructure.exceptions.FileException;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
    public class FileConverterTest {

        private static final String FILE_LOCATION = "C:\\Users\\Semicolon\\IdeaProjects\\walletApplication\\src\\main\\resources\\assets\\flower.jpeg";

        @Test
        void testGetTestFile_Success() {
            try {

                Path path = Paths.get(FILE_LOCATION);
                String expectedFileName = path.toAbsolutePath().toString();
                log.info("file nam ----->>>>> {}", path.getFileName());
                log.info("absolutepath ----> {}", path.toAbsolutePath());
                log.info("EXPECTED ----> {}", expectedFileName);

                MultipartFile result = FileConverterTest.getFileTest(expectedFileName);
                log.info("originalfile name ----> {}",result.getOriginalFilename());

                assertNotNull(result, "The MultipartFile should not be null");
                assertEquals(expectedFileName, result.getOriginalFilename(), "File names should match");
                assertEquals(Files.readAllBytes(path).length, result.getSize(), "File sizes should match");
            } catch (IOException | IdentityManagerException exception) {
                throw new FileException(exception.getMessage());

            }
        }




    public static MultipartFile getFileTest(String fileLocation) throws IdentityManagerException, IOException {
        IdentityValidator.validatePathFiles(fileLocation);
        Path path = Paths.get(fileLocation);
        try (InputStream inputStream = Files.newInputStream(path)) {
            MultipartFile multipartFile = new MockMultipartFile(
                    path.getFileName().toString(),
                    path.toAbsolutePath().toString(),
                    Files.probeContentType(path),
                    inputStream
            );

            log.info("MULTIPART ----> {}", multipartFile.getOriginalFilename());
            log.info("Multipart ------> {}", multipartFile.getName());
            return multipartFile;
        } catch (IOException exception) {
            exception.printStackTrace();
            throw new RuntimeException(exception);
        }
    }

    public static void main(String[] args) throws IdentityManagerException, IOException {
            MultipartFile multipartFile = getFileTest(FILE_LOCATION);
            String byteImage = ImageConverter.convertImageToBase64(multipartFile);
            log.info("Multipath {}",byteImage);
            String decode = Arrays.toString(Base64.getDecoder().decode(byteImage));
             log.info("byte {}", decode);



            String image = ImageConverter.encodeImageToBase64(FILE_LOCATION);


            log.info("Image {}",image);

    }





}




