package com.whb.demozuul;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author labu
 * @Date 2018/12/5
 * @Description 自定义 zuul过滤器
 */
public class PreRequestLogFilter extends ZuulFilter {
    private static final Logger LOG = LoggerFactory.getLogger(PreRequestLogFilter.class);
    /**
     * Description : 返回 过滤器的类型 pre  route   post    error
     *
     * @param  : []
     * @return : java.lang.String
     * @author : labu
     * @Date   : 2018/12/5
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * Description : 指定过滤器的执行顺序 不同的过滤器允许返回相同的数字
     *
     * @param  : []
     * @return : int
     * @author : labu
     * @Date   : 2018/12/5
     */
    @Override
    public int filterOrder() {

        return 1;
    }

    /**
     * Description : 判断当前过滤器是否执行
     *
     * @param  :
     * @return :
     * @author : labu
     * @Date   : 2018/12/5
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * Description : 过滤器的具体逻辑
     *
     * @param  :
     * @return :
     * @author : labu
     * @Date   : 2018/12/5
     */
    @Override
    public Object run() {
        RequestContext cxt = RequestContext.getCurrentContext();
        HttpServletRequest request = cxt.getRequest();
        PreRequestLogFilter.LOG.info(String.format("send %s request to %s ",request.getMethod(),request.getRequestURL().toString()));
        return null;
    }
}
