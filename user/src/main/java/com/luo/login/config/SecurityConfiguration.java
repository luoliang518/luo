package com.luo.login.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author: luoliang
 * @DATE: 2023/1/18/018
 * @TIME: 16:50
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .oauth2Client()
//                .clientRegistrationRepository(new InMemoryClientRegistrationRepository())
//                .authorizedClientRepository(new AuthenticatedPrincipalOAuth2AuthorizedClientRepository(new JdbcOAuth2AuthorizedClientService()))
//                .authorizedClientService(new JdbcOAuth2AuthorizedClientService())
//                .authorizationCodeGrant()
//                .authorizationRequestRepository(this.authorizationRequestRepository())
//                .authorizationRequestResolver(this.authorizationRequestResolver())
//                .accessTokenResponseClient(this.accessTokenResponseClient());
//    }
//
//    @Bean
//    SecurityFilterChain oauth2SecurityFilterChain(HttpSecurity http) throws Exception {
//        // 所有请求需要认证
//        http.authorizeRequests((requests) -> requests.anyRequest().authenticated());
//        // 开启 oauth2login
//        http.oauth2Login(Customizer.withDefaults());
//        // 声明为 oauth2 client
//        http.oauth2Client();
//        return http.build();
//    }

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
                .defaultSuccessUrl("/",true)
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