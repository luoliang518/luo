package com.luo.common.utils.httpUtils;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class HttpUtil {
    public static final String HTTP_prefix = "http://";
    /**
     * 静默重定向
     *
     * @param response response
     * @param url      重定向url
     */
    public static void silentRedirect(HttpServletResponse response, String url) {
        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            //此处不应有异常
        }
    }
}
