package com.luo.user.config.jwt;

import com.alibaba.fastjson.JSONArray;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.luo.user.service.UserService;
import com.luo.user.utils.ObjectToMapConverter;
import com.luo.model.jopoMapper.UserFiledMapper;
import com.luo.model.user.dto.UserDto;
import com.luo.model.user.entity.UserDo;
import com.luo.model.user.entity.UserRoleDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {
    @Autowired
    private UserService userService;

    public String extractTokenFromRequest(HttpServletRequest request) {
        String token;
        // 从请求头中提取 token
        String authorizationHeader = request.getHeader("Authorization");
        token = getToken(authorizationHeader);
        // 尝试从cookie中获取
        if (token == null&&request.getCookies()!=null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("Authorization")){
                    token = cookie.getValue();
                }
            }
        }
        // 如果请求头中没有 token，则尝试从请求参数中获取
        if (token == null) {
            authorizationHeader = request.getParameter("Authorization");
            token = getToken(authorizationHeader);
        }
        if (token == null) {
            token = request.getParameter("token");
        }
        return token;
    }

    private static String getToken(String authorizationHeader) {
        String token = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7); // 去除 "Bearer " 前缀
        }
        return token;
    }

    public String generateToken(String userAccount) {
        UserDo userByAccount = userService.getUserByAccount(userAccount);
        JSONArray jsonArray = JSONArray.parseArray(userByAccount.getUserRolesIds());
        UserDto userDto = UserFiledMapper.INSTANCE.userDo2Dto(userByAccount);
        userDto.setRolesIds(jsonArray.toJavaList(String.class));
        Map<String, Object> convert = ObjectToMapConverter.convert(userDto);
        // 生成 Token
        String token =JWT.create()
                .withSubject(userAccount)
                .withHeader(convert)
                .withClaim("userAccount",userAccount)
                .withClaim("password",userByAccount.getPassword())
                .withClaim("roles",userByAccount.getUserRoleDos().stream().map(UserRoleDo::getRoleName).collect(Collectors.toList()))
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600))
                .sign(Algorithm.HMAC512("luo".getBytes()));
        return token;
    }
}
