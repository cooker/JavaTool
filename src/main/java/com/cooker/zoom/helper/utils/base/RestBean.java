package com.cooker.zoom.helper.utils.base;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.cooker.zoom.helper.utils.convert.JacksonUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import java.lang.reflect.Field;

import static org.apache.commons.lang3.StringUtils.trimToEmpty;

/**
 * Created by yu.kequn on 2018-06-27.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class RestBean {
    /**
     * a=2&12=2& 格式参数
     */
    public String toUrlStr() {
        StringJoiner str = new StringJoiner("&");
        RestBean son = this;
        Field[] fields = FieldUtils.getAllFields(son.getClass());
        int len = ArrayUtils.getLength(fields);
        try{
            for(int i=0; i < len; i++){
                Field field = fields[i];
                field.setAccessible(true);
                str.add(field.getName() + "=" + getValue(field.get(son)));

            }
        }catch (IllegalAccessException e){
            throw new RuntimeException(e);
        }

        return str.toString();
    }

    public Map<String, String> toFormMap() {
        Map<String, String> parmas = Maps.newHashMap();
        RestBean son = this;
        Field[] fields = FieldUtils.getAllFields(son.getClass());
        int len = ArrayUtils.getLength(fields);
        try{
            for(int i=0; i < len; i++){
                Field field = fields[i];
                field.setAccessible(true);
                parmas.put(field.getName(), getValue(field.get(son)));

            }
        }catch (IllegalAccessException e){
            throw new RuntimeException(e);
        }
        return parmas;
    }

    public String toJsonStr(){
        return JacksonUtils.toJSON(this);
    }

    private String getValue(Object value){
        String rVal = Objects.toString(value, "");
        rVal = trimToEmpty(rVal);
        return rVal;
    }
}
