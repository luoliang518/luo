package com.luo.auth.infrastructure.acl;

import com.luo.auth.domain.userAggregate.entity.User;
import com.luo.auth.infrastructure.config.security.dto.UserSecurity;
import com.luo.auth.infrastructure.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TokenAcl {
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public void initAuthentication(HttpServletRequest request,User user) {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            UserSecurity userSecurity = new UserSecurity(user);
//            UserDetails userDetails = this.userDetailsService.loadUserByUsername(account);
            var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    userSecurity, null, userSecurity.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
    }

    public Jws<Claims> getTokenFromRequest(String requestTokenHeader) {
        return jwtUtil.tokenAnalysis(requestTokenHeader.substring(7));
    }
}
