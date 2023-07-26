package com.luo.user.config;

import com.luo.user.config.jwt.JwtAuthenticationFilter;
import com.luo.user.config.jwt.JwtAuthenticationProvider;
import com.luo.user.config.jwt.JwtAuthenticationSuccessHandler;
import com.luo.user.config.jwt.JwtConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @author: luoliang
 * @DATE: 2023/1/18/018
 * @TIME: 16:50
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler;
    @Resource
    private JwtAuthenticationProvider jwtAuthenticationProvider;
    @Resource
    private JwtConfig jwtConfig;
    @Resource
    private LogoutSuccessHandler logoutSuccessHandler;
//    @Resource
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        // 配置用户认证逻辑
//        auth.authenticationProvider(jwtAuthenticationProvider);
//    }
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        return new JwtAuthenticationFilter(jwtConfig,authenticationManager());
    }
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return new ProviderManager(Arrays.asList(jwtAuthenticationProvider, authenticationProvider()));
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());
        return authProvider;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8080"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    @Bean
    public SecurityFilterChain formLoginFilterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authorize ->
                        authorize
                                .antMatchers(
                                        "/",
                                        "/test/**",
                                        "/router/**",
                                        "/user/createUser",
                                        "/user/getToken",
                                        "/resources/**",
                                        "/static/**").permitAll()
                                // 角色
//                                .antMatchers("/user/admin/**").hasRole("admin")
//                                .antMatchers("/user/**").hasRole("user")
                                // 权限
//                                .antMatchers("").authenticated()
                                .anyRequest().authenticated()
                )
                .formLogin()
                .loginPage("/router/toLogin")
                .usernameParameter("username").passwordParameter("password")
                .loginProcessingUrl("/login")
//                .defaultSuccessUrl("/router/",true) //配置了successHandler就不需要这个了
                .successHandler(jwtAuthenticationSuccessHandler)
                .and()
                .csrf().disable()
                .logout()
                .logoutSuccessHandler(logoutSuccessHandler)
                .logoutUrl("/router/logout")
//                .logoutSuccessUrl("/")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .and()
                .rememberMe().rememberMeParameter("remember")
//                .and()
//                .httpBasic()
        ;
        return http.build();
    }
}