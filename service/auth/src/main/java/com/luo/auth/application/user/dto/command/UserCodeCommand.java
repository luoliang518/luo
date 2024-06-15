package com.luo.auth.application.user.dto.command;

import lombok.Data;

@Data
public class UserCodeCommand {
    private String phoneNumber;
    private String email;
    private String graphicCheck;
}
