package org.springcorepractice.walletapplication.infrastructure.utilities;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

import static scala.collection.concurrent.Debug.log;

@Slf4j
public class Converter {

    public static void main(String[] args) throws IOException, InterruptedException {

        String imageUrl = "https://res.cloudinary.com/drhrd1xkn/image/upload/v1732098164/syntxmgyooxhdgmmcaro.webp";

        InputStream inputStream = downloadImage(imageUrl);
        String base64Image = convertInputStreamToBase64(inputStream);
        log.info("Base64....{}", base64Image);
    }
    public static InputStream downloadImage(String imageUrl) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(imageUrl))
                .GET()
                .build();

        HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
        if (response.statusCode() == 200) {
            return new ByteArrayInputStream(response.body());
        } else {
            throw new IOException("Failed to download image: HTTP " + response.statusCode());
        }
    }


    public static String convertInputStreamToBase64(InputStream inputStream) throws IOException {
        byte[] fileContent = IOUtils.toByteArray(inputStream);
        return Base64.getEncoder().encodeToString(fileContent);
    }
}
