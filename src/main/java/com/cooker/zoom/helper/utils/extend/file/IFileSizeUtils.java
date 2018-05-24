package com.cooker.zoom.helper.utils.extend.file;

import com.cooker.zoom.helper.utils.base.quantity.Data;

/**
 * Created by yu.kequn on 2018-05-24.
 */
public interface IFileSizeUtils {
    static String sizeFormat(long size){
        if(size / Data.TB.multiplier() >= 1){//如果当前Byte的值大于等于1TB
            return String.format("%.2f", size/(float)Data.TB.multiplier()) + "TB";//将其转换成TB
        }else if (size / Data.GB.multiplier() >= 1)//如果当前Byte的值大于等于1GB
            return String.format("%.2f", size/(float)Data.GB.multiplier()) + "GB";//将其转换成GB
        else if (size / Data.MB.multiplier() >= 1)//如果当前Byte的值大于等于1MB
            return String.format("%.2f", size/(float)Data.MB.multiplier()) + "MB";//将其转换成MB
        else if (size / Data.KB.multiplier() >= 1)//如果当前Byte的值大于等于1KB
            return String.format("%.2f", size/(float)Data.KB.multiplier()) + "KB";//将其转换成KGB
        else
            return size + "B";//显示Byte值
    }
}
