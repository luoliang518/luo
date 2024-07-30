package com.luo.spring.infrastructure.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author luoliang
 * @description ip工具类
 * @date 2023/8/9 9:57
 */
public class IPUtil {
    public static String IP = "ip";
    public static String getip(HttpServletRequest request) {
        String ip = null;

        //X-Forwarded-For：Squid 服务代理
        String ipes = request.getHeader("X-Forwarded-For");

        if (ipes == null || ipes.isEmpty() || "unknown".equalsIgnoreCase(ipes)) {
            //Proxy-Client-IP：apache 服务代理
            ipes = request.getHeader("Proxy-Client-IP");
        }

        if (ipes == null || ipes.isEmpty() || "unknown".equalsIgnoreCase(ipes)) {
            //WL-Proxy-Client-IP：weblogic 服务代理
            ipes = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ipes == null || ipes.isEmpty() || "unknown".equalsIgnoreCase(ipes)) {
            //HTTP_CLIENT_IP：有些代理服务器
            ipes = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ipes == null || ipes.isEmpty() || "unknown".equalsIgnoreCase(ipes)) {
            //X-Real-IP：nginx服务代理
            ipes = request.getHeader("X-Real-IP");
        }

        //有些网络通过多层代理，那么获取到的ip就会有多个，一般都是通过逗号（,）分割开来，并且第一个ip为客户端的真实IP
        if (ipes != null && !ipes.isEmpty()) {
            ip = ipes.split(",")[0];
        }

        //还是不能获取到，最后再通过request.getRemoteAddr();获取
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ipes)) {
            ip = request.getRemoteAddr();
        }
        ip = ip.replace(":", ".");
        return ip;
    }

    public static String getip(ServerHttpRequest serverHttpRequest) {
        String ip = null;

        // 获取 X-Forwarded-For 头信息
        String ipes = serverHttpRequest.getHeaders().getFirst("X-Forwarded-For");

        if (ipes == null || ipes.isEmpty() || "unknown".equalsIgnoreCase(ipes)) {
            // 获取 Proxy-Client-IP 头信息
            ipes = serverHttpRequest.getHeaders().getFirst("Proxy-Client-IP");
        }

        if (ipes == null || ipes.isEmpty() || "unknown".equalsIgnoreCase(ipes)) {
            // 获取 WL-Proxy-Client-IP 头信息
            ipes = serverHttpRequest.getHeaders().getFirst("WL-Proxy-Client-IP");
        }

        if (ipes == null || ipes.isEmpty() || "unknown".equalsIgnoreCase(ipes)) {
            // 获取 HTTP_CLIENT_IP 头信息
            ipes = serverHttpRequest.getHeaders().getFirst("HTTP_CLIENT_IP");
        }

        if (ipes == null || ipes.isEmpty() || "unknown".equalsIgnoreCase(ipes)) {
            // 获取 X-Real-IP 头信息
            ipes = serverHttpRequest.getHeaders().getFirst("X-Real-IP");
        }

        // 有些网络通过多层代理，那么获取到的ip就会有多个，一般都是通过逗号（,）分割开来，并且第一个ip为客户端的真实IP
        if (ipes != null && !ipes.isEmpty()) {
            ip = ipes.split(",")[0].trim();
        }

        // 如果无法获取，最后再通过getRemoteAddress()获取
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ipes)) {
            ip = serverHttpRequest.getRemoteAddress().getAddress().getHostAddress();
        }

        return ip;
    }
    public static String getIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException("无法获取本机ip");
        }
    }
}
