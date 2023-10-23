//package com.luo.user.config.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
///**
// * @author luoliang
// * @description 拦截器配置
// * @date 2023/10/23 10:42
// */
//@Configuration
//public class SecurityInterceptorConfig implements WebMvcConfigurer {
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        // 添加权限拦截器，并排除登录接口 这里直接放权给shiro进行接口拦截
//        registry.addInterceptor(authInterceptor())
////                .excludePathPatterns("")
//        ;
//    }
//
//    @Bean
//    public SecurityInterceptor authInterceptor() {
//        return new SecurityInterceptor();
//    }
//
//}
