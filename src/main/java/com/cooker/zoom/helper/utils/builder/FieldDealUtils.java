package com.cooker.zoom.helper.utils.builder;

import com.google.common.collect.Maps;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Created by yu.kequn on 2018-05-22.
 */
public class FieldDealUtils {
    private static Logger logger = LoggerFactory.getLogger(FieldDealUtils.class);

    public static void copyFields(final Object src,final Object target){
        try {
            BeanUtils.copyProperties(target, src);
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.error("{} > {} 属性copy失败", src.getClass(), target.getClass(), e);
        }
    }

    public static Map<String, String> beanMap(Object obj){
        Map<String, String> map = null;
        try {
            map = BeanUtils.describe(obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        if(map == null) map = Maps.newHashMap();
        return map;
    }
}
