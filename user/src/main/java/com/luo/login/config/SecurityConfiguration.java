package com.luo.login.config;

import com.luo.login.config.jwt.JwtAuthenticationSuccessHandler;
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

import javax.annotation.Resource;
import java.util.Collections;

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

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        // 配置用户认证逻辑
//        auth.authenticationProvider(authenticationProvider());
//    }
//
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return new ProviderManager(Collections.singletonList(authenticationProvider()));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain formLoginFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize ->
                        authorize
                                .antMatchers(
                                        "/",
                                        "/test/**",
                                        "/router/**",
                                        "/user/createUser","/resources/**",
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
                .logout().logoutUrl("/router/logout").logoutSuccessUrl("/")
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