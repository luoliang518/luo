package com.luo.spring.infrastructure.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.AntPathMatcher;

import java.util.Set;

public class AuthorizationUtil {
    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    public static String getTokenAuth(String authorization) {
        return authorization.substring(7);
    }
    public static String getTokenHeader(HttpServletRequest request) {
        final String requestTokenHeader = request.getHeader("Authorization");
        if (requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer ")) {
            return null;
        }
        return requestTokenHeader;
    }
    public static boolean isPathAllowed(String requestPath, Set<String> ALLOWED_PATHS) {
        for (String allowedPath : ALLOWED_PATHS) {
            if (PATH_MATCHER.match(allowedPath, requestPath)) {
                return true;
            }
        }
        return false;
    }
}
