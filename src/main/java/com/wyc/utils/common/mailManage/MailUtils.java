package com.wyc.utils.common.mailManage;

import cn.hutool.core.lang.Validator;
import com.alibaba.fastjson.JSONObject;
import com.sun.mail.util.MailSSLSocketFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.security.GeneralSecurityException;
import java.util.Properties;
import java.util.regex.Pattern;

@Component
public class MailUtils {

    public Boolean testCheckMail(MailEntity mailEntity) throws GeneralSecurityException {
        Properties prop = new Properties();
        prop.setProperty("mail.host", mailEntity.getHost());
        prop.setProperty("mail.smtp.port", mailEntity.getPort());
        prop.setProperty("mail.transport.protocol", mailEntity.getProtocol());
        prop.setProperty("mail.smtp.auth", "true");
        //设置超时时间为20秒
        prop.setProperty("mail.smtp.timeout", "20000");
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.socketFactory", sf);

        boolean result;

        //1、创建session
        Session session = Session.getInstance(prop);
        //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
        session.setDebug(true);
        //2、通过session得到transport对象
        Transport ts;
        //3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
        try {
            ts = session.getTransport();
            ts.connect(mailEntity.getHost(),Integer.parseInt(mailEntity.getPort()),mailEntity.getUserName(),mailEntity.getPassWord());
            ts.close();
            result = true;
        } catch (MessagingException e) {
            result = false;
        }
        return result;
    }

    //动态创建JavaMailSender
    public Boolean sendAttachmentsMail(String to, String subject, String content, File file, JSONObject jsonObject){
        boolean result;
        try {
            //构建JavaMailSender
            JavaMailSender mailSender = configEmail(jsonObject);
            //构建message
            MimeMessage message = mailSender.createMimeMessage();
            //正文
            MimeMessageHelper helper = getMimeMessageHelper(jsonObject.getString("userName"),message,to,subject,content);
            //附件
            if (file!=null){
                FileSystemResource fileResource=  new FileSystemResource(file);
                helper.addAttachment(file.getName(),fileResource);
            }
            mailSender.send(message);
            result = true;
        } catch (MessagingException | MailSendException ignored) {
            result = false;
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        return result;
    }

    private MimeMessageHelper getMimeMessageHelper(String userName,MimeMessage message,String to, String subject, String content) throws MessagingException {
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(userName);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);
        return helper;
    }

    public JavaMailSender configEmail(JSONObject jsonObject) throws Exception {
        JavaMailSenderImpl javaMailProperties = new JavaMailSenderImpl();
        javaMailProperties.setDefaultEncoding("UTF-8");
        javaMailProperties.getSession().setDebug(true);
        javaMailProperties.setUsername(jsonObject.getString("userName"));
        javaMailProperties.setPassword(jsonObject.getString("password"));

        //companyType 10:腾讯 20:阿里
        Integer companyType = jsonObject.getInteger("companyType");

        boolean isEmail = Validator.isEmail(jsonObject.getString("userName"));
        if (!isEmail){
            throw new Exception("邮箱格式有问题！");
        }

        Pattern r = Pattern.compile("@");
        String[] split = r.split(jsonObject.getString("userName"));
        MailDefaultParameters tencentPerson = null;
        switch (split[1]){
            case "qq.com":
                tencentPerson = MailDefaultParameters.TENCENT_PERSON;
                break;
            case "163.com":
                tencentPerson = MailDefaultParameters.NETEASE_163;
                break;
            case "aliyun.com":
                tencentPerson = MailDefaultParameters.ALIBABA_PERSON;
                break;
            case "gmail.com":
                tencentPerson = MailDefaultParameters.GMAIL_PERSON;
                break;
            default:
                if (companyType.equals(10)){
                    tencentPerson = MailDefaultParameters.TENCENT_COMPANY;
                }else if (companyType.equals(20)){
                    tencentPerson = MailDefaultParameters.ALIBABA_COMPANY;
                }
        }
        if (tencentPerson == MailDefaultParameters.ALIBABA_COMPANY){
            javaMailProperties.setHost("smtp."+split[1]);
        }else {
            javaMailProperties.setHost(tencentPerson.getHost());
        }
        javaMailProperties.setPort(tencentPerson.getPort());
        javaMailProperties.setProtocol(tencentPerson.getProtocol());
        return javaMailProperties;
    }

}
