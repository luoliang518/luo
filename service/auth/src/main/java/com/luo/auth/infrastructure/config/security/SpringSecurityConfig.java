package com.luo.auth.infrastructure.config.security;

import com.luo.auth.domain.filter.TenantFilter;
import com.luo.auth.domain.filter.UserFilter;
import com.luo.auth.domain.userAggregate.service.UserDetailsServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

/**
 * 开启controller层权限控制
 *
 * @author luoliang
 * @PreAuthorize 在方法调用之前进行验证，用于确定是否允许访问方法。它支持Spring表达式语言（SpEL），可以使用方法参数、返回值等来编写条件。
 * @PostAuthorize 在方法执行后再进行验证，用于过滤方法的返回结果。它可以确保只有满足特定条件的结果才会返回给调用方。
 * @PreFilter 允许在方法执行前过滤输入参数的集合。例如，可以根据用户权限过滤传入方法的列表。
 * @PostFilter 允许在方法执行后过滤返回结果的集合。例如，可以过滤返回给调用者的列表，确保只返回符合条件的元素。
 * hasRole('ROLE_ADMIN')：检查当前用户是否具有名为 ROLE_ADMIN 的角色。
 * hasAuthority('ROLE_ADMIN')：检查当前用户是否具有名为 ROLE_ADMIN 的权限。
 * hasAnyRole('ROLE_ADMIN', 'ROLE_USER')：检查当前用户是否具有多个角色中的任意一个。
 * hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')：检查当前用户是否具有多个权限中的任意一个。
 */
@Slf4j
@EnableWebSecurity
@AllArgsConstructor
@Configuration
@EnableMethodSecurity
public class SpringSecurityConfig {
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final UserFilter userFilter;
    private final TenantFilter tenantFilter;
    private final CorsConfigurationSource corsConfigurationSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 获取到认证管理器
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(userDetailsServiceImpl)
                .passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                // 基于 token，不需要 csrf
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auth -> auth
                                // 指定某些接口不需要通过验证即可访问。登录接口肯定是不需要认证的
                                .requestMatchers("/user/say").permitAll()
                                .requestMatchers("/user/sendCode").permitAll()
                                .requestMatchers("/user/userRegistration").permitAll()
                                .requestMatchers("/user/userLogin").permitAll()
                                // 静态资源，可匿名访问
//                                .requestMatchers( "/", "/*.html", "/*/*.html", "/*/*.css", "/*/*.js").permitAll()
//                                .requestMatchers("/swagger-ui.html", "/swagger-resources/**", "/webjars/**", "/*/api-docs", "/druid/**","/doc.html").permitAll()
                                // 这里意思是其它所有接口需要认证才能访问
                                .anyRequest().authenticated()
                )
                // 基于 token，不需要 session
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // cors security 解决方案
//                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .cors(AbstractHttpConfigurer::disable)
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint((request, response, authException) -> {
                            log.info(authException.getMessage(),authException);
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            log.info(accessDeniedException.getMessage(),accessDeniedException);
                            response.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage());
                        })
                )
                .addFilterBefore(userFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(tenantFilter, UserFilter.class)
                .build();
    }
}
