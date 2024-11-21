package org.springcorepractice.walletapplication.infrastructure.adapters.output.persistence.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document
public class PremblyResponseEntity {
    @Id
    private String id;
    private boolean status;
    private String detail;
    private String responseCode;
    private NinData ninData;
    private FaceData faceData;
    private Verification verification;
    private Object session;
    private String userId;
    private String endpointName;

    @Data
    public static class NinData {
        private String title;
        private String lastname;
        private String firstname;
        private String gender;
        private String birthCountry;
        private String birthDate;
        private String birthLGA;
        private String birthState;
        private String centralID;
        private String educationalLevel;
        private String email;
        private String nin;
        private String employmentStatus;
        private String height;
        private String maritalStatus;
        private String photo;
        private String middleName;
        private String religion;
        private String telephoneNo;
        private String residenceAddress;
        private String residenceLGA;
        private String residenceState;
        private String residenceTown;
        private String residenceStatus;
        private String selfOriginLGA;
        private String selfOriginPlace;
        private String selfOriginState;
        private String signature;
        private String spokenLanguage;
        private String nokAddress1;
        private String nokAddress2;
        private String nokFirstName;
        private String nokLGA;
        private String nokMiddleName;
        private String nokPostalCode;
        private String nokState;
        private String nokSurname;
        private String nokTown;
        private String oSpokenLang;
        private String pFirstName;
        private String pMiddleName;
        private String profession;
        private String pSurname;
        private String trackingId;
        private String userId;
        private String vnin;
    }

    @Data
    public static class FaceData {
        private boolean status;
        private String message;
        private String confidence;
        private String responseCode;
    }

    @Data
    public static class Verification {
        private String status;
        private String reference;
    }
}
