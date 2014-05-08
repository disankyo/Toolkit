package com.disankyo.servlet.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.disankyo.log.SyncLogUtil;
import com.disankyo.log.LoggerFactory;

/**
 * 过滤器的超类。
 * @version 1.00 2010-12-31 17:09:06
 * @since 1.6
 * @author disankyo
 */
public abstract class BaseFilter implements Filter {

    protected FilterConfig filterConfig;
    protected SyncLogUtil logger;

    public BaseFilter() {
        logger = LoggerFactory.getLog(BaseFilter.class);
    }

    /**
     * 初始化方法，获取过滤器配置信息。
     * @param filterConfig 過濾器配置信息
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException{
        this.filterConfig = filterConfig;

        logger.debugLog("{0} filter initialzition.", getClass().getName());
    }

    /**
     * 过滤方法。
     * 将ServletRequest和ServletResponse分别转成
     * HttpServletRequest和HttpServletResponse，再调用抽象的doFilter方法。
     * @param request 请求。
     * @param response 相应。
     * @param chain 过滤器链。
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        doFilter((HttpServletRequest) request, (HttpServletResponse) response,
                chain);
    }

    /**
     * 方便子类实现的过滤方法。
     * @param request Http请求。
     * @param response Http响应。
     * @param chain 过滤器链。
     * @throws IOException
     * @throws ServletException
     */
    protected abstract void doFilter(HttpServletRequest request,
            HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException;

    /**
     * 销毁方法
     */
    @Override
    public void destroy() {
        logger.infoLog("{0} filter destory.", getClass().getName());
    }
}
