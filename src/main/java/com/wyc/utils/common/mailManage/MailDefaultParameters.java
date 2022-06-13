package com.wyc.utils.common.mailManage;

public enum MailDefaultParameters {

    ALIBABA_PERSON("smtp.aliyun.com",465,"smtps"),
    ALIBABA_COMPANY("smtp.temp.temp",465,"smtps"),
    TENCENT_PERSON("smtp.qq.com",465,"smtps"),
    TENCENT_COMPANY("smtp.exmail.qq.com",465,"smtps"),
    NETEASE_163("smtp.163.com",465,"smtps"),
    GMAIL_PERSON("smtp.gmail.com",465,"smtps");

    private final String host;
    private final Integer port;
    private final String protocol;

    MailDefaultParameters(String host, Integer port, String protocol) {
        this.host = host;
        this.port = port;
        this.protocol = protocol;
    }

    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }

    public String getProtocol() {
        return protocol;
    }
}
