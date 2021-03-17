package com.example.easy_okhttp;

public class ResposeResult {

    private String reason;
//    private String resultcode;
//    private int error_code;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

//    public int getError_code() {
//        return error_code;
//    }
//
//    public void setError_code(int error_code) {
//        this.error_code = error_code;
//    }
//
//    public String getResultcode() {
//        return resultcode;
//    }
//
//    public void setResultcode(String resultcode) {
//        this.resultcode = resultcode;
//    }


    @Override
    public String toString() {
        return "ResposeResult{" +
                "reason='" + reason + '\'' +
                '}';
    }
}
