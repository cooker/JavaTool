package com.cooker.zoom.helper.utils.base.prop;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

/**
 * Created by yu.kequn on 2018-06-28.
 */
public class AutoProperties extends BaseAutoProperties implements IProperties {
    private String fileName;
    static AutoWatcher autoWatcher = new AutoWatcher((fileName)->{
       if(checkLoadProp(fileName)){
           getProp(fileName).ifPresent(p->{
               System.out.println(p + ">>>>>>");
               try (FileInputStream fin = new FileInputStream(fileName)){
                   p.load(fin);
               }catch (IOException e){
               }
           });
       }
    });
    static Map<String, AutoProperties> cache = Maps.newConcurrentMap();
    protected AutoProperties(String fileName){
        this.fileName = fileName;
    }

    public static AutoProperties build(String fileName) throws IOException, InterruptedException {
        if(checkLoadProp(fileName)){
            return cache.get(fileName);
        }else{
            if(!autoWatcher.isWatching()){
                autoWatcher.init();
            }
            AutoProperties my = new AutoProperties(fileName);
            my.putProp(fileName);
            cache.put(fileName, my);
            return my;
        }
    }

    @Override
    public Optional<String> getString(String key, String dVal) {
        String val = this.getPString(key).get();
        if(val == null) val = dVal;
        return Optional.ofNullable(val);
    }

    @Override
    public long getLong(String key, long dVal) {
        String val = getString(key, "").get();
        return NumberUtils.toLong(val, dVal);
    }

    @Override
    public int getInt(String key, int dVal) {
        String val = getString(key, "").get();
        return NumberUtils.toInt(val, dVal);
    }

    @Override
    public boolean getBoolean(String key, boolean dVal) {
        String val = getString(key, dVal + "").get();
        return BooleanUtils.toBoolean(val);
    }

    @Override
    public float getFloat(String key, float dVal) {
        String val = getString(key, "").get();
        return NumberUtils.toFloat(val, dVal);
    }

    @Override
    public double getDouble(String key, double dVal) {
        String val = getString(key, "").get();
        return NumberUtils.toDouble(val, dVal);
    }

    private Optional<String> getPString(String key){
        Optional<Properties> prop = getProp(fileName);
        Properties pv = prop.get();
        String val = null;
        if(pv != null){
            val = pv.getProperty(key);
        }
        return Optional.ofNullable(val);
    }
}
