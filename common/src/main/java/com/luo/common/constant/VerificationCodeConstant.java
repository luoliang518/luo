package com.luo.common.constant;

import java.util.regex.Pattern;

/**
 * @Description
 * @Author luoliang
 * @Date 2024/6/17
 */
public class VerificationCodeConstant {
    public static final String CHARACTERS = "0123456789";
    public static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"
    );
    public static final int CODE_LENGTH = 6;
    public final static Long VERIFICATION_CODE_SURVIVAL_TIME = 60L;
}
