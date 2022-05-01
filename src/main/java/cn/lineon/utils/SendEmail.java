package cn.lineon.utils;

import cn.lineon.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
/**
 * @author 刘国庆
 * @version 1.0.0
 * @ClassName SendMail.java
 * @Description TODO
 * @createTime 2022年04月27日 22:03:00
 */
@Component
public class SendEmail extends Thread{
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String form;
    @Value("${spring.mail.name}")
    private String name;

    private User user;
    private String subject;
    private String content;

    /**
     * 设置参数
     * @param user 用户
     * @param subject 主题
     * @param content 内容
     */
    public SendEmail setParameter(User user,String subject,String content){
        this.user=user;
        this.subject=subject;
        this.content=content;
        return this;
    }
    @Override
    public void run(){
        MimeMessage message = mailSender.createMimeMessage();
        String to = user.getEmail();
        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(form,name);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(message);
        } catch (Exception e) {
        }
    }
}