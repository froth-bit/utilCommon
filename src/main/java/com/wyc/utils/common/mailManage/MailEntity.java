package com.wyc.utils.common.mailManage;

public class MailEntity {

    private String uGuid;
    private String host;
    private String userName;
    private String passWord;
    private String protocol="smtps";
    private String port="465";

    public String getuGuid() {
        return uGuid;
    }

    public void setuGuid(String uGuid) {
        this.uGuid = uGuid;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
