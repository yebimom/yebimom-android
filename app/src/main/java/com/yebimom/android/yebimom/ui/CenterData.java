package com.yebimom.android.yebimom.ui;

import java.io.Serializable;

/**
 * 센터 데이터
 */
public class CenterData implements Serializable {

    private String centerName;
    private String centerHashId;
    private String centerImageUrl;
    private String centerAddress;
    private String centerPhoneNumber;

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getCenterHashId() {
        return centerHashId;
    }

    public void setCenterHashId(String centerHashId) {
        this.centerHashId = centerHashId;
    }

    public String getCenterImageUrl() {
        return centerImageUrl;
    }

    public void setCenterImageUrl(String centerImageUrl) {
        this.centerImageUrl = centerImageUrl;
    }

    public String getCenterAddress() {
        return centerAddress;
    }

    public void setCenterAddress(String centerAddress) {
        this.centerAddress = centerAddress;
    }

    public String getCenterPhoneNumber() {
        return centerPhoneNumber;
    }

    public void setCenterPhoneNumber(String centerPhoneNumber) {
        this.centerPhoneNumber = centerPhoneNumber;
    }
}
