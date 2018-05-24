package com.cooker.zoom.helper.utils.convert;

/**
 * Created by yu.kequn on 2018-01-24.
 */
public interface IEntityConvert<T, U> {
    T fill(U... entitys);
}
