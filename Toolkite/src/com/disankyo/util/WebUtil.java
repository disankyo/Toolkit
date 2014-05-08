package com.disankyo.util;

import com.disankyo.log.SyncLogUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.LogFactory;

/**
 * web相关的工具方法
 * @version 1.00 2010-12-27 10:36:55
 * @since 1.6
 * @author Allean
 */
public class WebUtil {

    private static final SyncLogUtil LOG = new SyncLogUtil(LogFactory.getLog(WebUtil.class));

    private WebUtil(){
    }

    /**
     * 取实际用户的访问地址。
     * @param request 请求
     * @return 客户端Ip地址
     */
    public static String getIpAddress(HttpServletRequest request){
        String ips = request.getHeader("x-forwarded-for");
        if(ips == null || ips.length() == 0 || "unknown".equalsIgnoreCase(ips)){
            ips = request.getHeader("Proxy-Client-Ip");
        }
        if(ips == null || ips.length() == 0 || "unknown".equalsIgnoreCase(ips)){
            ips = request.getHeader("WL-Proxy-Client-Ip");
        }
        if(ips == null || ips.length() == 0 || "unknown".equalsIgnoreCase(ips)){
            ips = request.getRemoteAddr();
        }

        String[] ipArray = ips.split(",");
        String clientIp = null;

        for (String ip : ipArray) {
            if(!("unknown".equalsIgnoreCase(ip))){
                clientIp = ip;
            }
        }

        return clientIp;
    }

    /**
     * 添加一个cookie，使用默认域名
     * @param request 请求
     * @param response 响应
     * @param name Cookie名称
     * @param value Cookie值
     * @param maxAge 生命周期
     */
    public static void addCookie(
            HttpServletRequest requset,
            HttpServletResponse response,
            String name,
            String value,
            int maxAge){
        addCookie(requset, response, name, value, null, maxAge);
    }
    
    /**
     * 添加一个cookie，使用指定域名
     * @param request 请求
     * @param response 响应
     * @param name Cookie名称
     * @param value Cookie值
     * @param domain 指定的域名
     * @param maxAge 生命周期
     */
    public static void addCookie(
            HttpServletRequest request,
            HttpServletResponse response,
            String name,
            String value,
            String domain,
            int maxAge){
        addCookie(request, response, name, value, domain, null, maxAge);
    }

    /**
     * 添加一个Cookie,使用指定的contextPath
     * @param request 全请求
     * @param response 响应
     * @param name Cookie名称
     * @param value Cookie值
     * @param domain 指定域名
     * @param contextPath 域名存放路径
     * @param maxAge 生命周期
     */
    public static void addCookie(
            HttpServletRequest request,
            HttpServletResponse response,
            String name,
            String value,
            String domain,
            String contextPath,
            int maxAge){

        if(request != null && response != null){
            Cookie cookie = new Cookie(name,value);
            cookie.setSecure(request.isSecure());
            cookie.setMaxAge(maxAge);

            if(domain != null && !domain.isEmpty()){
                cookie.setDomain(domain);
            }

            if(contextPath == null || contextPath.isEmpty()){
                cookie.setPath("/");
            } else {
                cookie.setPath(contextPath);
            }

            response.addCookie(cookie);

            LOG.infoLog(
                    "The cookie update [name={0}, value={1}, domain={2}, contextPath={3}, maxAge={4}.]",
                    name, value, domain, contextPath, maxAge);
        }
    }

    /**
     * 根据指定的名称查找相应的cookie
     * @param request 请求
     * @param name 名称
     * @return 有相应的cookie，则返回相应的实例，否则返回空。
     */
    public static Cookie findCookieByName(HttpServletRequest request, String name){
        if(request != null){
            Cookie[] cookies = request.getCookies();
            if(cookies != null && cookies.length>0){
                for (Cookie cookie : cookies) {
                    if(name.equals(cookie.getName())){
                        return cookie;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 根据指定的名称查找相应的cookie值
     * @param request 请求
     * @param name 名称
     * @return cookie 名称
     */
    public static String findCookieValueByName(HttpServletRequest request, String name){
        Cookie cookie = findCookieByName(request, name);
        return cookie != null ? cookie.getValue() : null;
    }

    /**
     * 将指定cookie名称的cookie失效
     * @param request 请求
     * @param response 响应
     * @param name 名称
     */
    public static void failureCookie(
            HttpServletRequest request,
            HttpServletResponse response,
            String name){
        failureCookie(request, response, name, null);
    }

    /**
     * 将指定cookie名称的cookie失效
     * @param request 请求
     * @param response 响应
     * @param name 名称
     */
    public static void failureCookie(
            HttpServletRequest request,
            HttpServletResponse response,
            String name,
            String domain){
        String contextPath = request.getContextPath();
        if(contextPath == null || contextPath.isEmpty()){
            contextPath = "/";
        }
        failureCookie(request, response, name, domain, contextPath);
    }
    
    /**
     * 将指定域名指定cookie名称的cookie失效
     * @param request 请求
     * @param response 响应
     * @param name 名称
     * @param domain cookie域名
     */
    public static void failureCookie(
            HttpServletRequest request,
            HttpServletResponse response,
            String name,
            String domain,
            String contextPath){
        if(request != null && response != null){
            addCookie(request, response, name, null, domain, contextPath, 0);
        }
    }

    public static String getRequestFullAddress(HttpServletRequest request){
        if(request != null){
            StringBuilder buff = new StringBuilder(request.getRequestURI().toString());
            String queryString = request.getQueryString();
            if(queryString != null){
                buff.append("?").append(queryString);
            }
            
            return buff.toString();
        }

        return null;
    }

}
