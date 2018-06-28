package com.cooker.zoom.helper.utils.base;

/**
 * Created by yu.kequn on 2018-06-28.
 */
public interface IProperties {
    default String getString(String key){
        return getString(key, null);
    }
    default int getInt(String key){
        return getInt(key, 0);
    }
    default long getLong(String key){
        return getLong(key, 0);
    }
    default boolean getBoolean(String key){
        return getBoolean(key, false);
    }

    String getString(String key, String dVal);
    long getLong(String key, long dVal);
    int getInt(String key, int dVal);
    boolean getBoolean(String key, boolean dVal);
    float getFloat();
    double getDouble();
}
