package com.luo.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setReadTimeout(5000);
        simpleClientHttpRequestFactory.setConnectTimeout(10000);
        RestTemplate restTemplate = new RestTemplate(simpleClientHttpRequestFactory);
//        restTemplate.getMessageConverters().set(1,new StringHttpMessageConverter(StandardCharsets.UTF_8));
        // 设置请求头参数
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add(UnifiedServiceHandleEnumError.AUTHOR_NAME.getValue(),UnifiedServiceHandleEnumError.AUTHOR_PHONE.getValue());
//        httpHeaders.setContentType(MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE));
        return restTemplate;
    }
}
