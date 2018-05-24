package com.cooker.zoom.helper.utils.extend.weixin.sugar;

import com.cooker.zoom.helper.utils.extend.http.HttpUtils;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * Created by yu.kequn on 2017/9/1.
 */
public class PostMsgUtils {
    public static final String url = "http://sc.ftqq.com/";
    public static final String sub_url = "https://pushbear.ftqq.com/sub";
    public static final Logger logger = LoggerFactory.getLogger(PostMsgUtils.class);
    public static boolean doPost(PostMsg msg){
        boolean flag = false;
        String sjson = "";
        String furl = url + msg.getSckey() + ".send";
        Map<String, String> params = Maps.newHashMap();
        params.put("text", msg.getText());
        params.put("desp", msg.getDesp());
        try {
            sjson = HttpUtils.doPostForm(furl, params);
            sjson = StringEscapeUtils.unescapeJson(sjson);
            if (StringUtils.contains(sjson,"\"errno\":0")){
                flag = true;
            }
        } catch (IOException e) {
            logger.error("微信消息推送失败", e);
        }
        return flag;
    }

    public static boolean doPost(SubPostMsg msg){
        boolean flag = false;
        String sjson = "";
        try {
            sjson = HttpUtils.doPostJson(sub_url, msg);
            sjson = StringEscapeUtils.unescapeJson(sjson);
            if (StringUtils.contains(sjson,"\"code\": 0")){
                flag = true;
            }
        } catch (Exception e) {
            logger.error("微信消息Sub推送失败", e);
        }
        return flag;
    }
}
