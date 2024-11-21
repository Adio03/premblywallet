package org.springcorepractice.walletapplication.infrastructure.adapters.config;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfiguration {


    @Value("${cloud.api.key}")
    private String apiKey;
    @Value("${cloud.api.secret}")
    private String apiSecret;
    @Value("${cloud.api.name}")
    private String cloudName;

    private static final String API_KEY = "api_key";
    private static final String CLOUD_NAME = "cloud_name";
    private static final String API_SECRET = "api_secret";
    private static final String SECURE = "secure";
    private static final String TRUE = "true";
    private static final String FOLDER = "folder";
    private static final String FOLDER_NAME = "folder";


    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                CLOUD_NAME, cloudName,
                API_KEY, apiKey,
                API_SECRET, apiSecret,
                SECURE, TRUE,
                FOLDER, FOLDER_NAME
        ));
    }

}
