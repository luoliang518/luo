package com.luo.auth.infrastructure.util;

import jakarta.servlet.http.HttpServletRequest;

public class RequestUtil {
    public static String getTokenHeader(HttpServletRequest request) {
        final String requestTokenHeader = request.getHeader("Authorization");
        if (requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer ")) {
            return null;
        }
        return requestTokenHeader;
    }
}
