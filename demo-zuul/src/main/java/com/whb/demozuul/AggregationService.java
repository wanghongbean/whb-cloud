package com.whb.demozuul;

import com.alibaba.fastjson.JSON;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rx.Observable;


/**
 * @Author labu
 * @Date 2018/12/5
 * @Description 使用zuul 聚合微服务
 */
@Service
public class AggregationService {
    private static final Logger LOG = LoggerFactory.getLogger(AggregationService.class);
    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "fallback")
    public Observable<User> getUserById(Long id){
        LOG.info("$$-AggregationService.getUserById() var-id: {}", JSON.toJSONString(id));
        return Observable.create(observer->{
            User user = restTemplate.getForObject("http://localhost:8000/db/{id}", User.class, id);
//            User user = restTemplate.getForObject("http://demo-provider/db/{id}", User.class, id);
            observer.onNext(user);
            observer.onCompleted();
        });
    }

    public Observable<User> getConsuerUserByUserId(Long id){
        LOG.info("$$-AggregationService.getConsuerUserByUserId() var-id: {}", JSON.toJSONString(id));
        return Observable.create(observer->{
            User user = restTemplate.getForObject("http://demo-consumer/admin/{id}", User.class, id);
            observer.onNext(user);
            observer.onCompleted();
        });
    }

    /**
     * Description : 熔断 服务 快速返回方法
     *
     * @param  :
     * @return :
     * @author : labu
     * @Date   : 2018/12/5
     */
    public User fallback(Long id){
        User user = new User();
        user.setId(-1L);
        return user;
    }
}
