package com.cooker.zoom.helper.utils.base.prop;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

/**
 * Created by yu.kequn on 2018-06-29.
 */
public abstract class BaseAutoProperties {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    static volatile Map<String, Properties> props = Maps.newConcurrentMap();
    //检查配置是否加载
    protected static boolean checkLoadProp(String fileName){
        return props.get(fileName) != null;
    }
    protected static Optional<Properties> getProp(String fileName){
        return Optional.ofNullable(props.get(fileName));
    }
    protected boolean putProp(String fileName){
        Properties prop = new Properties();
        boolean isOk = true;
        try(FileInputStream fin = new FileInputStream(fileName)){
            prop.load(fin);
            props.put(fileName, prop);
        }catch (IOException e){
            logger.error("配置文件加载失败：{}", fileName, e);
            isOk = false;
        }
        return isOk;
    }
}
