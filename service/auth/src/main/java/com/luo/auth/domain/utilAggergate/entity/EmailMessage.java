package com.luo.auth.domain.utilAggergate.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class EmailMessage implements Serializable {
    private String email;
    private String subject;
    private String content;

    public EmailMessage(String email) {
        this.email = email;
    }

    public void setMessage(String subject,String content) {
        this.subject = subject;
        this.content = content;
    }

}
