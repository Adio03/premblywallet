package org.springcorepractice.walletapplication.infrastructure.utilities;

import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class Base64MultipartFile implements MultipartFile {

    private final byte[] content;
    private final String name;
    private final String contentType;

    public Base64MultipartFile(String base64String) {
        if (base64String == null || base64String.isEmpty()) {
            throw new IllegalArgumentException("Base64 string cannot be null or empty.");
        }

        String base64Data = base64String;


        if (base64String.contains(",")) {
            String[] parts = base64String.split(",", 2);
            if (parts.length != 2) {
                throw new IllegalArgumentException("Base64 string does not contain valid data.");
            }
            base64Data = parts[1];
        }


        try {
            this.content = Base64.getDecoder().decode(base64Data);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid Base64 encoding.", e);
        }
        this.name = "uploaded_image";

        this.contentType = determineContentType(base64String);
    }

    private String determineContentType(String base64String) {
        if (base64String.contains("jpeg")) {
            return "image/jpeg";
        } else if (base64String.contains("png")) {
            return "image/png";
        } else if (base64String.contains("gif")) {
            return "image/gif";
        } else {
            return "application/octet-stream";
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getOriginalFilename() {
        return name + getExtensionFromContentType();
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public boolean isEmpty() {
        return content.length == 0;
    }

    @Override
    public long getSize() {
        return content.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return content;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(content);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        // Not implemented in this example
    }

    private String getExtensionFromContentType() {
        if ("image/jpeg".equals(contentType)) {
            return ".jpg";
        } else if ("image/png".equals(contentType)) {
            return ".png";
        } else if ("image/gif".equals(contentType)) {
            return ".gif";
        }
        return "";
    }
}
