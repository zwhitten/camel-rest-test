package com.testing.config;


import com.testing.views.CustomDataFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("local")
public class LocalEnvironmentBean {
    @Value("${port}")
    Integer port;


    @Bean
    public CustomDataFormat hibernateJsonDataFormat(){
        return new CustomDataFormat();
    }
}
