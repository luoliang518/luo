package com.luo.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.luo.common.enums.unifiedEnums.UnifiedServiceHandleEnum;
import com.luo.common.result.IntegrateException;
import com.luo.common.utils.httpUtils.HttpUtil;
import com.luo.common.utils.httpUtils.IpUtil;
import com.luo.common.utils.httpUtils.SsoUtil;
import com.luo.common.utils.springUtils.ApplicationContextUtils;
import com.luo.user.config.jwt.JwtConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author: luoliang
 * @DATE: 2023/1/18/018
 * @TIME: 16:34
 */
@Controller
@RequestMapping("/router")
public class RouterController {
    @Value("${okta.oauth2.baseUri}")
    private String baseUri;
    @Value("${okta.oauth2.getCodeUri}")
    private String getCodeUri;
    @Value("${okta.oauth2.getTokenUri}")
    private String getTokenUri;
    @Value("${okta.oauth2.getUserUri}")
    private String getUserUri;
    @Value("${okta.oauth2.client-id}")
    private String clientId;
    @Value("${okta.oauth2.client-secret}")
    private String clientSecret;
    @Autowired
    private JwtConfig jwtConfig;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping({"/","/index"})
    public String index() {
        return "index";
    }

    @RequestMapping("/toLogin")
    public String toLogin() {
        return "views/login";
    }
    @ResponseBody
    @GetMapping("/logout")
    public void logout(ServletRequest request, HttpServletResponse response) {
        try {
            response.sendRedirect("https://localhost:518/");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @ResponseBody
    @RequestMapping("/loginSuccess")
    public String loginSuccess() {
        return "success";
    }

    /**
     * OKTA 登录地址
     * @param request
     */
    @ResponseBody
    @GetMapping("/oktaLogin")
    public void oktaLogin(HttpServletRequest request, HttpServletResponse response) {
        // 添加state
        String state = UUID.randomUUID().toString();
        // 添加nonce
        long time = new Date().getTime();
        // 保存到redis
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(IpUtil.IP,IpUtil.getIPAddress(request));
        redisTemplate.opsForValue().set(state, jsonObject.toJSONString(), TimeUnit.MINUTES.toMillis(5));
        String tokenSsoUri = SsoUtil.getTokenSsoUri(
                baseUri + getCodeUri,
                clientId,
                "code",
                "query",
                "openid profile phone email",
                HttpUtil.HTTP_prefix+ApplicationContextUtils.getAddress() + "/router" + "/oktaCallback",
                state, String.valueOf(time));
        HttpUtil.silentRedirect(response,tokenSsoUri);
    }
    /**
     * OKTA 回调地址
     * @return
     */
    @ResponseBody
    @RequestMapping("/oktaCallback")
    public void oktaCallback(HttpServletRequest request, HttpServletResponse response) {
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        // 1. 判断redis中是否有key
        if (!Boolean.TRUE.equals(redisTemplate.hasKey(state))) {
            IntegrateException.buildExternalEx(UnifiedServiceHandleEnum.STATUS_REQUEST_TIMEOUT);
        }
        // 获取token
        // 设置请求头参数
        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(MediaType.parseMediaType(MediaType.APPLICATION_JSON_VALUE));
        httpHeaders.setAccept(new ArrayList<>(Collections.singleton(MediaType.parseMediaType(MediaType.APPLICATION_JSON_VALUE))));
        byte[] bytes = (clientId+":"+clientSecret).getBytes(StandardCharsets.UTF_8);
        String basic = Base64.getEncoder().encodeToString(bytes);
        httpHeaders.add("Authorization","Basic "+basic);
        // 设置请求体参数
        LinkedMultiValueMap<String, Object> bodyParameters = new LinkedMultiValueMap<>();
        bodyParameters.add("grant_type","authorization_code");
        bodyParameters.add("redirect_uri",HttpUtil.HTTP_prefix+ApplicationContextUtils.getAddress() + "/router" + "/oktaCallback");
        bodyParameters.add("code",code);
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(bodyParameters, httpHeaders);
        String responseBodyString = restTemplate.postForObject(baseUri + getTokenUri, httpEntity, String.class);
        JSONObject responseBody = JSONObject.parseObject(responseBodyString);
        // 获取令牌
        assert responseBody != null;
        String accessToken = responseBody.getString("access_token");
        // 获取用户信息
        httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(new ArrayList<>(Collections.singleton(MediaType.parseMediaType(MediaType.APPLICATION_JSON_VALUE))));
        httpHeaders.add("Authorization","Bearer "+accessToken);
        bodyParameters = new LinkedMultiValueMap<>();
        httpEntity = new HttpEntity<>(bodyParameters, httpHeaders);
        ResponseEntity<JSONObject> exchange = restTemplate.exchange(baseUri + getUserUri, HttpMethod.GET, httpEntity, JSONObject.class);
        JSONObject body = exchange.getBody();
        String name = body.getString("name");
        String token = jwtConfig.generateToken(name);
        // 重定向到'/'地址，并附带token作为URL参数
        try {
            response.sendRedirect("/?token=" + URLEncoder.encode(token, "UTF-8"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
