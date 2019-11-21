package com.chuangshu.livable.config;

import com.chuangshu.livable.security.validate.ValidateCodeFilter;
import com.chuangshu.livable.security.validate.email.EmailCodeAuthenticationSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * @Author: wws
 * @Date: 2019-07-06 15:30
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;
    @Autowired
    EmailCodeAuthenticationSecurityConfig emailCodeAuthenticationSecurityConfig;
    @Autowired
    private AuthenticationSuccessHandler livableAuthenticationSuccessHandle;
    @Autowired
    private AuthenticationFailureHandler livableAuthenticationFailureHandle;
    @Qualifier("myUserDetailService")
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private SpringSocialConfigurer springSocialConfigurer;

    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
//        tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        validateCodeFilter.setAuthenticationFailureHandler(livableAuthenticationFailureHandle);
        http
//                .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
//                .formLogin()
//                .loginPage("/requireAuthentication")
//                .loginProcessingUrl("/login")
//                .successHandler(livableAuthenticationSuccessHandle)
//                .failureHandler(livableAuthenticationFailureHandle)
//                .and()
//                .rememberMe()
//                .tokenRepository(persistentTokenRepository())
//                .tokenValiditySeconds(3600)
//                .userDetailsService(userDetailsService)
//                .and()
//                .authorizeRequests()
//                .antMatchers("/requireAuthentication", "/login1.html", "/login.html", "/imageCode", "/emailCode", "/swagger-ui.html", "/**").permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
                .csrf().disable()
//                .apply(emailCodeAuthenticationSecurityConfig)
//                .and()
//                .apply(springSocialConfigurer)

                //关闭验证
//                .and()
                .authorizeRequests()
                .anyRequest().permitAll()
                .and()
                .logout().permitAll()
        .and()
        .csrf().disable();

    }

}