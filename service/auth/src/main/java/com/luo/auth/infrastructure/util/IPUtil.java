package com.luo.auth.infrastructure.util;

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
    public static String getIPAddress(HttpServletRequest request) {
        String ip = null;

        //X-Forwarded-For：Squid 服务代理
        String ipAddresses = request.getHeader("X-Forwarded-For");

        if (ipAddresses == null || ipAddresses.isEmpty() || "unknown".equalsIgnoreCase(ipAddresses)) {
            //Proxy-Client-IP：apache 服务代理
            ipAddresses = request.getHeader("Proxy-Client-IP");
        }

        if (ipAddresses == null || ipAddresses.isEmpty() || "unknown".equalsIgnoreCase(ipAddresses)) {
            //WL-Proxy-Client-IP：weblogic 服务代理
            ipAddresses = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ipAddresses == null || ipAddresses.isEmpty() || "unknown".equalsIgnoreCase(ipAddresses)) {
            //HTTP_CLIENT_IP：有些代理服务器
            ipAddresses = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ipAddresses == null || ipAddresses.isEmpty() || "unknown".equalsIgnoreCase(ipAddresses)) {
            //X-Real-IP：nginx服务代理
            ipAddresses = request.getHeader("X-Real-IP");
        }

        //有些网络通过多层代理，那么获取到的ip就会有多个，一般都是通过逗号（,）分割开来，并且第一个ip为客户端的真实IP
        if (ipAddresses != null && !ipAddresses.isEmpty()) {
            ip = ipAddresses.split(",")[0];
        }

        //还是不能获取到，最后再通过request.getRemoteAddr();获取
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ipAddresses)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static String getIPAddress(ServerHttpRequest serverHttpRequest) {
        String ip = null;

        // 获取 X-Forwarded-For 头信息
        String ipAddresses = serverHttpRequest.getHeaders().getFirst("X-Forwarded-For");

        if (ipAddresses == null || ipAddresses.isEmpty() || "unknown".equalsIgnoreCase(ipAddresses)) {
            // 获取 Proxy-Client-IP 头信息
            ipAddresses = serverHttpRequest.getHeaders().getFirst("Proxy-Client-IP");
        }

        if (ipAddresses == null || ipAddresses.isEmpty() || "unknown".equalsIgnoreCase(ipAddresses)) {
            // 获取 WL-Proxy-Client-IP 头信息
            ipAddresses = serverHttpRequest.getHeaders().getFirst("WL-Proxy-Client-IP");
        }

        if (ipAddresses == null || ipAddresses.isEmpty() || "unknown".equalsIgnoreCase(ipAddresses)) {
            // 获取 HTTP_CLIENT_IP 头信息
            ipAddresses = serverHttpRequest.getHeaders().getFirst("HTTP_CLIENT_IP");
        }

        if (ipAddresses == null || ipAddresses.isEmpty() || "unknown".equalsIgnoreCase(ipAddresses)) {
            // 获取 X-Real-IP 头信息
            ipAddresses = serverHttpRequest.getHeaders().getFirst("X-Real-IP");
        }

        // 有些网络通过多层代理，那么获取到的ip就会有多个，一般都是通过逗号（,）分割开来，并且第一个ip为客户端的真实IP
        if (ipAddresses != null && !ipAddresses.isEmpty()) {
            ip = ipAddresses.split(",")[0].trim();
        }

        // 如果无法获取，最后再通过getRemoteAddress()获取
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ipAddresses)) {
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
