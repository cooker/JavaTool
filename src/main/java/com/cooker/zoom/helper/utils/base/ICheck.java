package com.cooker.zoom.helper.utils.base;

/**
 * Created by yu.kequn on 2018-06-27.
 */
public interface ICheck {
    boolean check();
    static boolean check(ICheck param) throws RuntimeException {
        if(param == null){
            throw new RuntimeException("参数对象为空！");
        }
        return param.check();
    }
}
