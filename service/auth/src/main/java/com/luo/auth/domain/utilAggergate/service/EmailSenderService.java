package com.luo.auth.domain.utilAggergate.service;

import com.luo.auth.domain.utilAggergate.entity.VerificationCode;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        message.setFrom("17357170942@163.com");
        mailSender.send(message);
    }
    public String sendVerificationCode(String email) {
        String code = new VerificationCode().getCode();
        // 存储验证码到数据库或缓存（这里省略）

        String subject = "Your Verification Code";
        String content = "Your verification code is: " + code;
        sendEmail(email, subject, content);
        return code;
    }
}
