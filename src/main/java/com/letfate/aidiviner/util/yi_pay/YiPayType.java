package com.letfate.aidiviner.util.yi_pay;

public enum YiPayType {
    ALI("alipay"),
    WECHAT("wxpay");

    private String type;

    YiPayType(String type) {
        this.type = type;
    }

    public String getValue() {
        return this.type;
    }
}