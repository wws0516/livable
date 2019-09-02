package com.chuangshu.livable.social.QQ;

import com.chuangshu.livable.social.QQ.QQConnectionFactory;
import com.chuangshu.livable.social.SocialConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.security.SpringSocialConfigurer;
import org.springframework.stereotype.Component;

/**
 * @Author: wws
 * @Date: 2019-07-20 15:34
 */
@Configuration
public class QQAutoConfig extends SocialConfigurerAdapter {

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {
        super.addConnectionFactories(connectionFactoryConfigurer, environment);
        connectionFactoryConfigurer.addConnectionFactory(
                new QQConnectionFactory("QQ", "wxd99431bbff8305a0", "60f78681d063590a469f1b297feff3c4"));
    }

    @Bean
    public SpringSocialConfigurer configurer(){
        return new SpringSocialConfigurer();
    }

    @Override
    public UserIdSource getUserIdSource() {
        return new UserIdSource() {
            public String getUserId() {
                return "anonymous";
            }
        };
    }
}
