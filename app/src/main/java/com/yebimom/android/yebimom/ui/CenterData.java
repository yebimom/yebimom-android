package com.yebimom.android.yebimom.ui;

import java.io.Serializable;

/**
 * com.yebimom.android.yebimom.ui Need Comment!
 */
public class CenterData implements Serializable {

    private String centerName;
    private String centerImageUrl;

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getCenterImageUrl() {
        return centerImageUrl;
    }

    public void setCenterImageUrl(String centerImageUrl) {
        this.centerImageUrl = centerImageUrl;
    }
}
