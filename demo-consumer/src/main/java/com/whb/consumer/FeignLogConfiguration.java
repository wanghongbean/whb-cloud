package com.whb.consumer;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author labu
 * @Date 2018/12/4
 * @Description
 */
@Configuration
public class FeignLogConfiguration {
    @Bean
    Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL;
    }
}
