package com.cooker.zoom.helper.utils.extend.sysinfo;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;

/**
 * Created by yu.kequn on 2017-12-18.
 */
public abstract class BaseMonitor {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    protected Double to2DotDouble(Double val){
        if(Double.isNaN(val))
            return 0.0d;
        else{
            DecimalFormat df = new DecimalFormat("#.00");
            return NumberUtils.toDouble(df.format(val), 0);
        }
    }
}
