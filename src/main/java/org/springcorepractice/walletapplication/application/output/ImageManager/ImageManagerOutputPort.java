package org.springcorepractice.walletapplication.application.output.ImageManager;

import org.springcorepractice.walletapplication.domain.exceptions.IdentityManagerException;
import org.springframework.web.multipart.MultipartFile;

public interface ImageManagerOutputPort {
    String uploadImage(MultipartFile content) throws IdentityManagerException;
}
