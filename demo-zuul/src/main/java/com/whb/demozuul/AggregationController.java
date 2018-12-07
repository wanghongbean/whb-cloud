package com.whb.demozuul;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import rx.Observable;
import rx.Observer;

import java.util.HashMap;

/**
 * @Author labu
 * @Date 2018/12/5
 * @Description zuul 聚合多个请求
 */
@RestController
public class AggregationController {
    private static final Logger LOG = LoggerFactory.getLogger(AggregationController.class);
    @Autowired
    private AggregationService aggregationService;

    @GetMapping("/aggregate/{id}")
    public DeferredResult<HashMap<String, User>> aggregate(@PathVariable Long id) {
        Observable<HashMap<String, User>> observable = this.aggregateObservable(id);
        return this.toDeferredResult(observable);
    }

    public Observable<HashMap<String, User>> aggregateObservable(Long id) {
        //合并 多个 observables 发射出的数据项
        return Observable.zip(
                this.aggregationService.getUserById(id),
                this.aggregationService.getConsuerUserByUserId(id),
                (user, consumerUser) -> {
                    HashMap<String, User> map = Maps.newHashMap();
                    map.put("user", user);
                    map.put("consumerUser", consumerUser);
                    return map;
                }
        );
    }

    public DeferredResult<HashMap<String, User>> toDeferredResult(Observable<HashMap<String, User>> details) {
        DeferredResult<HashMap<String, User>> result = new DeferredResult<>();
        details.subscribe(new Observer<HashMap<String, User>>() {
            @Override
            public void onCompleted() {
                LOG.info("$$-完成。。。。");
            }

            @Override
            public void onError(Throwable throwable) {
                LOG.error(throwable.getMessage(), throwable);
            }

            @Override
            public void onNext(HashMap<String, User> stringUserHashMap) {
                result.setResult(stringUserHashMap);
            }
        });
        return result;
    }
}
