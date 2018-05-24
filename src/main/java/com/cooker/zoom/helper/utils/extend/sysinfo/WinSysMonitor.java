package com.cooker.zoom.helper.utils.extend.sysinfo;

import com.sun.management.OperatingSystemMXBean;
import org.apache.commons.lang3.SystemUtils;

import java.io.File;
import java.lang.management.ManagementFactory;

/**
 * Created by yu.kequn on 2017-12-18.
 */
public class WinSysMonitor extends BaseMonitor implements ISysMonitor {
    private final static OperatingSystemMXBean osmxb =
            ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

    @Override
    public Double getCpuUsage() {
        Double cpuUsage = osmxb.getSystemCpuLoad();
        cpuUsage = (cpuUsage == -1.0) ? 0.0:cpuUsage;
        return super.to2DotDouble(cpuUsage);
    }

    @Override
    public Double getDiskUsage() {
        String runPath = SystemUtils.USER_DIR.substring(0, 3);
        File fdisk = new File(runPath);
        long diskFreeSpace = fdisk.getUsableSpace();
        long diskTotalSpace = fdisk.getTotalSpace();
        long diskUsedSpace = diskTotalSpace - diskFreeSpace;
        Double diskUsage = (double)diskUsedSpace / (double)diskTotalSpace;
        return super.to2DotDouble(diskUsage);
    }

    @Override
    public Double getMemUsage() {
        long physicalMemoryFree = osmxb.getFreePhysicalMemorySize();
        long physicalMemoryTotal = osmxb.getTotalPhysicalMemorySize();
        long physicalMemoryUsed = physicalMemoryTotal - physicalMemoryFree;
        Double physicalMemoryUsage = (double)physicalMemoryUsed / (double)physicalMemoryTotal;
        return super.to2DotDouble(physicalMemoryUsage);
    }
}
