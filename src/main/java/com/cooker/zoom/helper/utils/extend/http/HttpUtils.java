package com.cooker.zoom.helper.utils.extend.http;

import com.cooker.zoom.helper.utils.convert.JacksonUtils;
import com.google.common.collect.Lists;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by yu.kequn on 2017/8/10.
 */
public class HttpUtils {
    public static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);
    public static HttpHelper helper = new HttpHelper();
    protected HttpUtils(){}

    public static String doGet(String url) throws IOException {
        return doGet(url, null);
    }

    public static String doGet(String url, Map<String, String> params) throws IOException {
        return doGet(url, params, HTTP.UTF_8);
    }

    public static String doGet(String url, Map<String, String> params, String encoding)
            throws IOException {
        String result = HttpResponseMsg.FAIL.toString();

        try (CloseableHttpClient client = HttpClients.createDefault()){

            String nurl = StringUtils.trimToNull(url);
            String uparams = helper.newGetParams(params);
            StringBuilder sb = new StringBuilder(nurl);
            boolean cs = false, equ = false;
            cs = nurl.contains("?");
            equ = nurl.contains("=");
            if(cs && equ) sb.append("&");
                else if(!cs) sb.append("?");
            sb.append(uparams);

            HttpGet get = new HttpGet(sb.toString());
            CloseableHttpResponse httpResponse = client.execute(get);
            result = helper.getResponseContent(httpResponse, encoding);
        } catch (IOException e) {
            throw new IOException("HTTP doGet IO Exception", e);
        }
        return result;
    }

    public static String doPostFormData(String url, Map<String, ContentBody> params) throws IOException {
        return doPostFormData(url, params, HTTP.UTF_8);
    }

    public static String doPostFormData(String url, Map<String, ContentBody> params, String encoding) throws IOException {
        String result = HttpResponseMsg.FAIL.toString();
        String nurl = StringUtils.trimToNull(url);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        if(MapUtils.isNotEmpty(params))
            params.forEach((key, value)->{
                builder.addPart(key, value);
            });
        result = doPostX(nurl, builder.build(), encoding);
        return result;
    }

    public static String doPostForm(String url, Map<String, String> params) throws IOException {
        return doPostForm(url, params, HTTP.UTF_8);
    }

    public static String doPostForm(String url, Map<String, String> params, String encoding)
            throws IOException {
        String result = HttpResponseMsg.FAIL.toString();
        List<NameValuePair> nvs = Lists.newArrayList();
        String nurl = StringUtils.trimToNull(url);
        if(MapUtils.isNotEmpty(params))
            params.forEach((key, value)->{
                NameValuePair nv = new BasicNameValuePair(key, value);
                nvs.add(nv);
            });
        result = doPostX(nurl, new UrlEncodedFormEntity(nvs, encoding), encoding);
        return result;
    }

    public static String doPostJson(String url, Object object) throws Exception {
        return doPostJson(url, object, HTTP.UTF_8);
    }

    public static String doPostJson(String url, Object object, String encoding) throws Exception {
        String result = HttpResponseMsg.FAIL.toString();
        String nurl = StringUtils.trimToNull(url);
        String sjson = JacksonUtils.toJSON(object);
        StringEntity sentity = new StringEntity(sjson, HTTP.UTF_8);
        result = doPostX(nurl, sentity, encoding);
        return result;
    }

    public static String doPostX(String url, HttpEntity entity, String encoding) throws IOException {
        String result = HttpResponseMsg.FAIL.toString();
        try (CloseableHttpClient client = HttpClients.createDefault()){
            HttpPost post = new HttpPost(url);
            post.setEntity(entity);
            CloseableHttpResponse httpResponse = client.execute(post);
            result = helper.getResponseContent(httpResponse, encoding);
        } catch (IOException e) {
            throw new IOException("HTTP IO Exception", e);
        }
        return result;
    }

}
