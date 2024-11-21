package org.springcorepractice.walletapplication.domain.validator;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.keycloak.broker.provider.IdentityBrokerException;
import org.springcorepractice.walletapplication.domain.enums.constants.IdentityMessage;
import org.springcorepractice.walletapplication.domain.exceptions.IdentityManagerException;
import org.springcorepractice.walletapplication.domain.model.identity.IdentityVerification;
import org.springcorepractice.walletapplication.domain.model.identity.UserIdentity;
import org.springcorepractice.walletapplication.domain.model.wallet.WalletIdentity;
import org.springcorepractice.walletapplication.infrastructure.adapters.input.rest.data.request.PaystackRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;
@Slf4j
public class IdentityValidator {


    public static void validateDataInput(UserIdentity userIdentity) throws IdentityManagerException {
        if(userIdentity == null) {
            throw new IdentityManagerException(IdentityMessage.USER_IDENTITY_CANNOT_BE_NULL.getMessage());
        }
        validateDataElement(userIdentity.getFirstName());
        validateDataElement(userIdentity.getLastName());
        validateDataElement(userIdentity.getPassword());
        validatePassword(userIdentity.getPassword());
        validateUserEmail(userIdentity.getEmail());


    }

    public static void validateUserIdentity(UserIdentity userIdentity) throws IdentityManagerException {
        log.info("Started validating for user identity in validation class : {}", userIdentity);
        IdentityValidator.validateObjectInstance(userIdentity);
        if (ObjectUtils.isEmpty(userIdentity.getIdentityRole())|| StringUtils.isEmpty(userIdentity.getIdentityRole().name()))
            throw new IdentityManagerException(IdentityMessage.INVALID_VALID_ROLE.getMessage());

        validateUserEmail(userIdentity.getEmail());
        validateDataElement(userIdentity.getFirstName());
        validateDataElement(userIdentity.getLastName());
        validateDataElement(userIdentity.getId());

    }
    public static void validateUserInput(UserIdentity userIdentity) throws IdentityManagerException {
        log.info("Started validating for user identity in validation class : {}", userIdentity);
        IdentityValidator.validateObjectInstance(userIdentity);
        if (ObjectUtils.isEmpty(userIdentity.getIdentityRole())|| StringUtils.isEmpty(userIdentity.getIdentityRole().name()))
            throw new IdentityManagerException(IdentityMessage.INVALID_VALID_ROLE.getMessage());

        validateUserEmail(userIdentity.getEmail());
        validateDataElement(userIdentity.getFirstName());
        validateDataElement(userIdentity.getLastName());
        validateDataElement(userIdentity.getPassword());


    }

    public static void validateUserIdentityId(String dataElement) throws IdentityManagerException {
        if(StringUtils.isEmpty(dataElement)){
            throw new IdentityManagerException(IdentityMessage.USER_NOT_FOUND.getMessage());
        }
    }

    public static void validateDataElement(String dataElement) throws IdentityManagerException {
        String trimmedDataElement = dataElement != null ? dataElement.trim() : StringUtils.EMPTY;
        if (isEmptyString(trimmedDataElement)) {
            throw new IdentityManagerException(IdentityMessage.EMPTY_INPUT_FIELD_ERROR.getMessage());
        }
    }

    public static void validateObjectInstance(Object instance) throws IdentityManagerException {
        if(ObjectUtils.isEmpty(instance)){
            throw new IdentityManagerException(IdentityMessage.INVALID_OBJECT.getMessage());
        }

    }
    public static void validateUserIdentityObject(UserIdentity userIdentity) throws IdentityManagerException {
        validateObjectInstance(userIdentity);
    }

    public static void validatEmail(String email) throws IdentityManagerException {
        validateDataElement(email);
        validateUserEmail(email);
    }

    private static boolean isEmptyString(String dataElement) {
        return StringUtils.isEmpty(dataElement) || StringUtils.isBlank(dataElement);
    }

    private static void validateUserEmail(String email) throws IdentityManagerException {
        if (StringUtils.isEmpty(email) || !EmailValidator.getInstance().isValid(email)) {
            throw new IdentityManagerException(IdentityMessage.INVALID_EMAIL_ADDRESS.getMessage());
        }
    }

    public static void validatePassword(String password) throws IdentityManagerException {
        validateDataElement(password);
       Pattern pattern = Pattern.compile(IdentityMessage.PASSWORD_PATTERN.getMessage());
//        if (!pattern.matcher(password).matches()){
//            throw new IdentityManagerException(IdentityMessage.WEAK_PASSWORD.getMessage());
//        }
    }

    public static void validatePathFiles(String filePath) throws IOException, IdentityManagerException {
        if (StringUtils.isBlank(filePath)) {
            throw new IdentityManagerException(IdentityMessage.INVALID_FILE_PATH.getMessage());
        }
        Path path = Paths.get(filePath);
        if (!Files.exists(path) || !Files.isReadable(path) || !Files.isRegularFile(path)) {
            throw new IdentityManagerException(IdentityMessage.INVALID_FILE_PATH.getMessage());

        }

        String contentType = Files.probeContentType(path);
        if (StringUtils.isEmpty(contentType)) {
            throw new IdentityManagerException(IdentityMessage.INVALID_VALID_ROLE.getMessage());
        }

    }
      public static boolean isValidMultipartType(MultipartFile multipartFile) throws IdentityManagerException {
        if(multipartFile == null){
            throw new IdentityManagerException(IdentityMessage.INVALID_CONTENT_TYPE.getMessage());
        }
        String contentType = multipartFile.getContentType();

        if(!(contentType == null ||
                contentType.startsWith("image/") ||
                contentType.startsWith("application/") ||
                contentType.equals("text/plain") || contentType.endsWith("jpeg"))){
            throw new IdentityManagerException(IdentityMessage.INVALID_CONTENT_TYPE.getMessage());
        }
        return true;
    }

    public static void validatePaystackInput(PaystackRequest paystackRequest) throws IdentityManagerException {
        if(paystackRequest == null){
            throw new IdentityManagerException(IdentityMessage.EMPTY_INPUT_FIELD_ERROR.getMessage());
        }
        validateUserEmail(paystackRequest.getEmail());
    }

    public static void validatePaystackReference(String reference) throws IdentityManagerException {
        if(StringUtils.isEmpty(reference) || StringUtils.isBlank(reference)){
            throw new IdentityManagerException(IdentityMessage.INVALID_REFERENCE.getMessage());
        }
    }

        public static void validateTransactionAmount(BigDecimal amount) throws IdentityManagerException {
            if (amount == null) {
                throw new IdentityManagerException(IdentityMessage.AMOUNT_CAN_NOT_BE_NULL.getMessage());
            }

            String amountString = amount.toString().trim();

            if (amountString.isEmpty()) {
                throw new IdentityManagerException(IdentityMessage.AMOUNT_CAN_NOT_EMPTY.getMessage());
            }

            BigDecimal trimmedAmount = new BigDecimal(amountString);

            if (trimmedAmount.compareTo(BigDecimal.ZERO) < 0) {
                throw new IdentityManagerException(IdentityMessage.AMOUNT_CAN_NOT_BE_ZERO.getMessage());
            }
            if (trimmedAmount.compareTo(BigDecimal.ZERO) == 0) {
                throw new IdentityManagerException(IdentityMessage.AMOUNT_CAN_NOT_BE_ZERO.getMessage());
            }
        }

       public static void validateIdentityVerificationRequest(IdentityVerification identityVerification) throws  IdentityManagerException {
        if(identityVerification == null){
            throw new IdentityManagerException("credentials should not be empty");
        }
        validateDataElement(identityVerification.getNin());
        validateDataElement(identityVerification.getImageUrl());

        }
    public static void validateWalletIdentity(WalletIdentity walletIdentity) throws IdentityManagerException {
        if(walletIdentity == null){
            throw new IdentityManagerException(IdentityMessage.WALLET_NOT_FOUND.getMessage());
        }
        validateDataElement(walletIdentity.getUserId());
    }

    }










