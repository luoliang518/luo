package com.luo.auth.domain.messageAggergate.valueObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
