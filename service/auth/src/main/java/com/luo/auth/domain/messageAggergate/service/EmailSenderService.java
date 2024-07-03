package com.luo.auth.domain.messageAggergate.service;

import com.luo.auth.domain.messageAggergate.entity.VerificationCode;
import com.luo.auth.infrastructure.acl.CacheAcl;
import com.luo.auth.infrastructure.config.code.EmailConfig;
import com.luo.common.exception.ServiceException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
@Slf4j
@Service
@AllArgsConstructor
public class EmailSenderService {
    private EmailConfig emailConfig;
    private final JavaMailSender mailSender;
    private final CacheAcl cacheAcl;
    private final TemplateEngine templateEngine;

    private void sendEmail(String to, String subject, String content) throws MessagingException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        message.setFrom(emailConfig.getFrom());
        mailSender.send(message);
    }

    public VerificationCode sendVerificationCode(VerificationCode verificationCode) {
        // 判断是否已发送过验证码
        long keepLive = cacheAcl.getEmailKeepLive(verificationCode);
        if (keepLive>0){
            return cacheAcl.getEmailCode(verificationCode).setExpiration(keepLive/1000);
        }
        verificationCode.setVerificationCode();
        // 记录缓存
        cacheAcl.saveEmailCode(verificationCode);
        String subject = "LuoAuth Your verification code";
        String content = verificationCode.getCode();
        verificationCode.getEmailMessage().setMessage(subject,content);
        log.info(verificationCode.toString());
        // 发送短信
//        sendEmailCode(verificationCode);
        return verificationCode;
    }

    private void sendEmailCode(VerificationCode verificationCode) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(verificationCode.getEmailMessage().getEmail());
            helper.setSubject(verificationCode.getEmailMessage().getSubject());
            helper.setFrom(emailConfig.getFrom());
            Context context = new Context();
            context.setVariable("subject", verificationCode.getEmailMessage().getSubject());
            context.setVariable("username", verificationCode.getUsername());
            context.setVariable("verificationCode", verificationCode.getEmailMessage().getContent());
            String html = templateEngine.process("emailTemplate", context);
            helper.setText(html, true);
        } catch (MessagingException e) {
            throw new ServiceException("消息发送功能出现异常，请联系管理员 17357170942@163.com",e);
        }
        mailSender.send(mimeMessage);
    }

    public boolean verifyVerificationCode(VerificationCode verificationCode) {
        VerificationCode verificationCodeCache = cacheAcl.getEmailCode(verificationCode).setExpiration(verificationCode.getExpiration());
        return verificationCodeCache.getCode().equals(verificationCode.getCode());
    }
}
