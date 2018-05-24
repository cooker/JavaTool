package com.cooker.zoom.helper.utils.builder;

import com.cooker.zoom.helper.utils.convert.JacksonUtils;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * Created by yu.kequn on 2018-05-22.
 */
public class FieldDealUtils {
    private static Logger logger = LoggerFactory.getLogger(FieldDealUtils.class);

    public static void copyFields(final Object src,final Object target){
        Preconditions.checkArgument(ObjectUtils.allNotNull(src, target), "属性赋值 (源-目标) 未指定!");

        Field[] srcAttrs = FieldUtils.getAllFields(src.getClass());
        Field[] tarAttrs = FieldUtils.getAllFields(target.getClass());
        int srcSize = ArrayUtils.getLength(srcAttrs);
        int tarSize = ArrayUtils.getLength(tarAttrs);
        if(srcSize != 0 && tarSize != 0){
            final Map<String, Field> srcMattrs = Arrays.stream(srcAttrs).collect(Collectors.toMap(Field::getName, field -> field));
            final Map<String, Field> tarMattrs = Arrays.stream(tarAttrs).collect(Collectors.toMap(Field::getName, field -> field));
            final StringJoiner sj = new StringJoiner(",");
            srcMattrs.forEach((k,v)->{
                Field field = tarMattrs.get(k);
                //类型相同 复制属性
                if(field != null && field.getType().equals(v.getType())){
                    field.setAccessible(true);
                    v.setAccessible(true);
                    try {
                        field.set(target, v.get(src));
                    } catch (IllegalAccessException e) {
                        sj.add(k);
                    }
                }
            });
            String str = sj.toString();
            if (StringUtils.isNotEmpty(str)){
                logger.warn("{} > {} 属性copy失败：{}", src.getClass(), target.getClass(), str);
            }
        }

    }

    public static Map<String, String> beanMap(Object obj){
        Map<String, String> map;
        String sjon = JacksonUtils.toJSON(obj);
        map = JacksonUtils.json2Object(sjon, Map.class);
        return map == null ? Maps.newHashMap() : map;
    }
}
