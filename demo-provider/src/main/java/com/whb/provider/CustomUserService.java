package com.whb.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @Author labu
 * @Date 2018/12/3
 * @Description
 */
@Component
public class CustomUserService implements UserDetailsService {
    private static final Logger LOG = LoggerFactory.getLogger(CustomUserService.class);
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ("admin".equals(username)){
            LOG.info("$$-$$$$$$$$$$$.() var-: {}");
            return new SecurityUser("admin","admin","admin");
        }else if ("user".equals(username)){
            LOG.info("$$-%%%%%%%%%%%%.() var-: {}");
            return new SecurityUser("user","user","user");
        }else {
            LOG.info("$$-(((((((((((((.() var-: {}");
            return null;
        }
    }
}
