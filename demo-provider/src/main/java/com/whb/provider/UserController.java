package com.whb.provider;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.plugin.liveconnect.SecurityContextHelper;

import java.util.Collection;
import java.util.List;

/**
 * @Author labu
 * @Date 2018/11/29
 * @Description
 */
@RestController
public class UserController {
    @Autowired
    private UserRep userRep;
    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping("/db/{id}")
    public Object getById(@PathVariable Long id) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails){
            UserDetails userDetails=(UserDetails)principal;
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
            authorities.forEach(a-> System.out.println("当前用户 ： "+userDetails.getUsername()+"  角色 ： "+a.getAuthority()));
        }

        User one = userRep.getOne(id);
        return one;
    }

    @RequestMapping("/ss/{id}")
    public Object getDis(@PathVariable Long id) {
        List<String> services = discoveryClient.getServices();
        String s = JSON.toJSONString(services);
        return s;
    }


}
