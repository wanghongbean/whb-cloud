package com.whb.consumer;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author labu
 * @Date 2018/12/3
 * @Description
 */
//@FeignClient(name = "demo-provider",configuration = FeignLogConfiguration.class)
public interface UserFeignClient {
    @GetMapping("/db/{id}")
    public User findById(@PathVariable("id") Long id);
}
