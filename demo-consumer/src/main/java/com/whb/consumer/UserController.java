package com.whb.consumer;

import com.alibaba.fastjson.JSON;
import feign.Client;
import feign.Contract;
import feign.Feign;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.feign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @Author labu
 * @Date 2018/11/29
 * @Description
 */
@Import(FeignClientsConfiguration.class)
@RestController
public class UserController {
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    private UserFeignClient userUserfeignClient;
    private UserFeignClient adminUserfeignClient;

    @Autowired
    private RestTemplate restTemplate;


    @Autowired
    private DiscoveryClient client;

    @Autowired
    private LoadBalancerClient loadBalancerClient;


    @Autowired
    public UserController(Decoder decoder, Encoder encoder, Client client, Contract contract) {
        LOG.info("$$-UserController.UserController() var-consumer 构造器: {}");
        this.userUserfeignClient= Feign.builder()
                .client(client).encoder(encoder).decoder(decoder)
                .contract(contract).requestInterceptor(new BasicAuthRequestInterceptor("user","user"))
                .target(UserFeignClient.class,"http://demo-provider/");
        this.adminUserfeignClient= Feign.builder()
                .client(client).encoder(encoder).decoder(decoder)
                .contract(contract).requestInterceptor(new BasicAuthRequestInterceptor("admin","admin"))
                .target(UserFeignClient.class,"http://demo-provider/");
    }

    @GetMapping("/user/{id}")
    public Object getUser(@PathVariable Long id) {
        LOG.info("$$-UserController.getUser() var-: {}");
        return userUserfeignClient.findById(id);
    }

    @GetMapping("/admin/{id}")
    public Object getAdmin(@PathVariable Long id) {
        LOG.info("$$-UserController.getAdmin() var-id: {}", JSON.toJSONString(id));
        return adminUserfeignClient.findById(id);
    }

//    @Autowired
//    private UserFeignClient userFeignClient;

//    @GetMapping("/feign/{id}")
//    public Object getByFeign(@PathVariable  Long id){
//        return userFeignClient.findById(id);
//    }

    @RequestMapping("/u/{id}")
    public Object getById(@PathVariable Long id) {
        User forObject = restTemplate.getForObject("http://localhost:8000/" + id, User.class);
        return forObject;
    }

    @RequestMapping("/r/{id}")
    public Object getByRId(@PathVariable Long id) {
        User forObject = restTemplate.getForObject("http://demo-provider/db/" + id, User.class);
        return forObject;
    }

//    @RequestMapping("/c/{id}")
//    public Object getBydis(@PathVariable Long id) {
//        Application application = discoveryClient.getApplication("demo-provider");
//
//        return application;
//    }

    @RequestMapping("/c/{id}")
    public Object getBydissss(@PathVariable Long id) {
        List<ServiceInstance> instances = client.getInstances("demo-provider");
        return JSON.toJSONString(instances);
    }


    @GetMapping("/log-instance")
    public Object serviceInstance() {
        ServiceInstance choose = loadBalancerClient.choose("demo-provider");
        return choose;
    }
}
