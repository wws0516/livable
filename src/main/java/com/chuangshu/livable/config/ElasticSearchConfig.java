package com.chuangshu.livable.config;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Author: wws
 * @Date: 2019-09-23 08:45
 */

@Configuration
public class ElasticSearchConfig {

    @PostConstruct
    void init() {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }
    @Bean
    public TransportClient esClient() throws UnknownHostException{
        Settings settings = Settings.builder()
                .put("cluster.name", "livable")
                .put("client.transport.sniff", true)
                .build();

        TransportAddress master = new TransportAddress(
                InetAddress.getByName("127.0.0.1"), 9300);

        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(master);
        return client;

    }
}
