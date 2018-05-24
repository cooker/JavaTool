package com.cooker.zoom.helper.utils.extend.http;

import com.google.common.collect.Maps;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.trimToEmpty;

/**
 * Created by yu.kequn on 2018-05-24.
 */
public class HttpServerUtils {
    public static Map<String, Cookie> getReqCookiesForMap(HttpServletRequest req){
        Cookie[] webCookies = req.getCookies();
        Map<String, Cookie> cookies = null;
        if(ArrayUtils.isNotEmpty(webCookies)){
            cookies = Arrays.stream(webCookies).collect(Collectors.toMap(Cookie::getName, cookie -> cookie));
        }
        if(MapUtils.isEmpty(cookies)){
            cookies = Maps.newHashMap();
        }
        return cookies;
    }

    public static Map<String, String> getReqParamsForMap(HttpServletRequest req){
        Map<String, String> parmas = Maps.newHashMap();
        Enumeration keys = req.getParameterNames();
        String key = null;
        while (keys.hasMoreElements()){
            key = (String) keys.nextElement();
            parmas.put(key, trimToEmpty(req.getParameter(key)));
        }

        return parmas;
    }

    public static void addCookie(HttpServletResponse resp, String key, String value, String maxAge){
        Cookie cookie = new Cookie(key, value);
        cookie.setPath("/");
        cookie.setMaxAge(NumberUtils.toInt(maxAge, -1));
        resp.addCookie(cookie);
    }

    /**
     * 获取请求主机IP地址
     *
     * @param request
     * @return
     * @throws IOException
     */
    public final static String getIp(HttpServletRequest request){
        String unknowIp = "unknown";
        String ip = Objects.toString(request.getHeader("X-Forwarded-For"), unknowIp);
        if (isNotBlank(ip) || unknowIp.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            if (isNotBlank(ip) || unknowIp.equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (isNotBlank(ip) || unknowIp.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (isNotBlank(ip) || unknowIp.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (isNotBlank(ip) ||unknowIp.equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } else if (StringUtils.length(ip) > 15) {
            String[] ips = ip.split(",");
            for (int index = 0; index < ips.length; index++) {
                String strIp = (String) ips[index];
                if (!("unknown".equalsIgnoreCase(strIp))) {
                    ip = strIp;
                    break;
                }
            }
        }
        return ip;
    }
}
