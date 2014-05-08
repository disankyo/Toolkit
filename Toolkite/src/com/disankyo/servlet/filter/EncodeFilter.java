package com.disankyo.servlet.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @version 1.00 2010-12-31 17:28:20
 * @since 1.6
 * @author disankyo
 */
public class EncodeFilter extends BaseFilter {

    private static final String PARAM_ENCODE = "encode";

    /**
     * 过滤方法。
     * 如果配置了初始化参数encoding，那么将过滤的请求和相应均设置为指定的编码方式，
     * 否则，直接放行，不做任何处理。
     * @param request 请求。
     * @param response 相应。
     * @param chain 过滤器链。
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void doFilter(HttpServletRequest request,
            HttpServletResponse response, FilterChain chain) throws IOException,
            ServletException {

        String encoding = filterConfig.getInitParameter(PARAM_ENCODE);

        if (encoding != null && !encoding.isEmpty()) {
            request.setCharacterEncoding(encoding);
        }

        chain.doFilter(request, response);

        if (encoding != null && !encoding.isEmpty()) {
            response.setCharacterEncoding(encoding);
        }
    }
}
