package com.luo.auth.infrastructure.acl;

import com.luo.auth.domain.userAggregate.entity.User;
import com.luo.auth.infrastructure.config.security.dto.UserSecurity;
import com.luo.auth.infrastructure.util.PrivateJwtUtil;
import com.luo.spring.infrastructure.util.AuthorizationUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TokenAcl {
    private final PrivateJwtUtil privateJwtUtil;

    public void initAuthentication(HttpServletRequest request,User user) {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            UserSecurity userSecurity = new UserSecurity(user);
            var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    userSecurity, null, userSecurity.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
    }

    public Jws<Claims> getTokenFromRequest(String requestTokenHeader) {
        return privateJwtUtil.tokenAnalysis(AuthorizationUtil.getTokenAuth(requestTokenHeader));
    }

    public String generateToken(User user) {
        return privateJwtUtil.generateToken(user);
    }

    public Jws<Claims> tokenAnalysis(String tokenHeader) {
        return privateJwtUtil.tokenAnalysis(tokenHeader);
    }
}
