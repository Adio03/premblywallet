package org.springcorepractice.walletapplication.infrastructure.adapters.output.ImageManager;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springcorepractice.walletapplication.application.output.ImageManager.ImageManagerOutputPort;
import org.springcorepractice.walletapplication.domain.exceptions.IdentityManagerException;
import org.springcorepractice.walletapplication.domain.validator.IdentityValidator;
import org.springcorepractice.walletapplication.infrastructure.utilities.Base64MultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryAdapter implements ImageManagerOutputPort {

    private final Cloudinary cloudinary;

    private static final String SECURE_URL = "secure_url";
    private static  final String RESOURCE_TYPE = "resource_type";
    private static final String AUTO = "auto";

    @Override
    public String uploadImage(String content) throws IdentityManagerException {
//       IdentityValidator.isValidMultipartType(multipart);

       MultipartFile multipart = new Base64MultipartFile(content);


        try {
            Map<?, ?> uploadResponse = cloudinary.uploader().upload(multipart.getBytes(),
                    ObjectUtils.asMap(
                            RESOURCE_TYPE, AUTO
                    ));
            return (String) uploadResponse.get(SECURE_URL);
        }catch (IOException exception){
            throw new IdentityManagerException(exception.getMessage());
        }

    }

}

