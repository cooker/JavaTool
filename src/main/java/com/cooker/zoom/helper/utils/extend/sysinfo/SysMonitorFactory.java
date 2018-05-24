package com.cooker.zoom.helper.utils.extend.sysinfo;

import org.apache.commons.lang3.SystemUtils;

/**
 * Created by yu.kequn on 2017-12-19.
 */
public class SysMonitorFactory {
    private SysMonitorFactory(){}

    public static ISysMonitor getSysMonitor(){
        ISysMonitor monitor = null;
        if(SystemUtils.IS_OS_WINDOWS)
            monitor = new WinSysMonitor();
        if (SystemUtils.IS_OS_LINUX)
            monitor = new LinuxSysMonitor();
        return monitor;
    }
}
