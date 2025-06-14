package com.letfate.aidiviner.util.yi_pay;

import lombok.Data;

@Data
public class YiPayResponse {
    private int code;
    private String msg;
    private String trade_no;
    private String pay_type;
    private String pay_info;
    private String timestamp;
    private String sign_type;
    private String sign;
}