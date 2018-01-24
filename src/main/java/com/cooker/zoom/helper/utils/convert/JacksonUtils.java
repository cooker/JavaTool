package com.cooker.zoom.helper.utils.convert;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 创建者:   ck<br/>
 * 创建时间: 2017/7/31 15:56<br/>
 * 功能描述:<br/>
 * 修改历史:<br/>
 * 2017/7/31 15:56 ck 描述<br/>
 */
public class JacksonUtils {
    private final static Logger logger = LoggerFactory.getLogger(JacksonUtils.class);
    protected static ObjectMapper mapper = new ObjectMapper();
    static {
        //关闭未知的反序列化 属性
//        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        //允许单引号，非标准格式
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        //强制非ASCII转义
        mapper.configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
    }

    private JacksonUtils(){}

    public static String toJSON(Object obj){
        String json = "";
        try {
            json = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error("Object转化为JSON异常：", e);
        }
        return json;
    }

    public static<T> T json2Object(String json, Class<T> cl){
        T obj = null;
        try {
            obj = mapper.readValue(json, cl);
        } catch (IOException e) {
            logger.error("JSON转化为Object异常：", e);
        }
        return obj;
    }

    public static<T> T json2Collection(String json, TypeReference<T> type){
        T obj = null;
        try {
            obj = mapper.readValue(json, type);
        } catch (IOException e) {
            logger.error("JSON转化为集合异常：", e);
        }
        return obj;
    }
}
