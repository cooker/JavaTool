package com.cooker.zoom.helper.utils.extend.http;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Created by yu.kequn on 2017/8/30.
 */
public class HttpHelper {
    /***私有区**/
    public String getResponseContent(CloseableHttpResponse httpResponse, String encoding) throws IOException {
        HttpEntity entity = httpResponse.getEntity();
        String result = HttpResponseMsg.FAIL.toString();
        if(Objects.nonNull(entity) && HttpStatus.SC_OK == httpResponse.getStatusLine().getStatusCode()){
            result = IOUtils.toString(entity.getContent(), encoding);
        }
        HttpClientUtils.closeQuietly(httpResponse);
        return result;
    }

    public String newGetParams(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder(32);
        if(!MapUtils.isEmpty(params)){
            Set<Map.Entry<String, String>> keys = params.entrySet();
            Iterator<Map.Entry<String, String>> it = keys.iterator();
            boolean isfirst = true;
            Map.Entry entry;
            String key, value;
            while (it.hasNext()){
                entry = it.next();
                key = (String)entry.getKey();
                value = (String)entry.getValue();
                if(isfirst){
                    isfirst = false;
                }else {
                    sb.append("&");
                }
                sb.append(key).append("=").append(URLEncoder.encode(value, HTTP.UTF_8));
            }
        }
        return sb.toString();
    }
}
