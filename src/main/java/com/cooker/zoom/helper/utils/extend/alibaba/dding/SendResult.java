package com.cooker.zoom.helper.utils.extend.alibaba.dding;

import com.cooker.zoom.helper.utils.convert.JacksonUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by dustin on 2017/3/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SendResult {
    private boolean isSuccess;
    private Integer errorCode;
    private String errorMsg;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String toString(){
        return JacksonUtils.toJSON(this);
    }
}
