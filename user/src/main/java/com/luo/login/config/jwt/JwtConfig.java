package com.luo.login.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.luo.login.service.UserService;
import com.luo.login.utils.ObjectToMapConverter;
import com.luo.model.jopoMapper.UserFiledMapper;
import com.luo.model.user.dto.UserDto;
import com.luo.model.user.entity.UserDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {
    public String header;

    //token前缀
    public String tokenPrefix;

    //有效期
    public long expireTime;

    @Autowired
    private UserService userService;

    public String extractTokenFromRequest(HttpServletRequest request) {
        String token = null;
        // 从请求头中提取 token
        String authorizationHeader = request.getHeader(header);
        if (authorizationHeader != null && authorizationHeader.startsWith(tokenPrefix)) {
            token = authorizationHeader.substring(7); // 去除 "Bearer " 前缀
        }
        // 如果请求头中没有 token，则尝试从请求参数中获取
        if (token == null) {
            token = request.getParameter("token");
        }
        return token;
    }

    public String generateToken(String userAccount) {
        UserDo userByAccount = userService.getUserByAccount(userAccount);
        UserDto userDto = UserFiledMapper.INSTANCE.userDo2Dto(userByAccount);
        Map<String, Object> convert = ObjectToMapConverter.convert(userDto);
        // 生成 Token
        String token =tokenPrefix + JWT.create()
                .withSubject(userAccount)
                .withHeader(convert)
                .withClaim("userAccount",userAccount)
                .withExpiresAt(new Date(System.currentTimeMillis() + expireTime))
                .sign(Algorithm.HMAC512("luo".getBytes()));

        return token;
    }
}
