package com.hd.hdaisdk;

import com.hd.hdaisdk.client.HdAiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @auther hd
 * @Description
 */
//配置类
@Configuration
//读取application.yml配置并放置到属性中
@ConfigurationProperties("hdapi.client")
@Data
//自动扫描组件，注册相应bean
@ComponentScan
public class HdAiClientConfig {
    private String accessKey;

    private String secretKey;

    @Bean
    public HdAiClient yuApiClient() {
        return new HdAiClient(accessKey, secretKey);
    }

}
