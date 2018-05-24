package com.cooker.zoom.helper.utils.extend.sysinfo;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.apache.commons.lang3.SystemUtils.USER_DIR;

/**
 * Created by yu.kequn on 2017-12-18.
 */
public class LinuxSysMonitor extends BaseMonitor implements ISysMonitor {
    String cpuCmd = "cat /proc/stat";
    String diskCmd = "df -h ";
    String memCmd = "cat /proc/meminfo";

    @Override
    public Double getCpuUsage() {
        Double cpuUsage = Double.NaN;
        Process pro1 = null,pro2 = null;
        BufferedReader in1 = null, in2 = null;
        Runtime r = Runtime.getRuntime();
        CpuAttr cpuOp01, cpuOp02;
        try {
            pro1 = r.exec(cpuCmd);
            in1 = new BufferedReader(new InputStreamReader(pro1.getInputStream()));
            cpuOp01 = operCpu(in1);
            IOUtils.closeQuietly();
            pro1.destroy();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) { ; }
            pro2 = r.exec(cpuCmd);
            in2 = new BufferedReader(new InputStreamReader(pro2.getInputStream()));
            cpuOp02 = operCpu(in2);
            IOUtils.closeQuietly(in2);
            pro2.destroy();
            if(cpuOp01.getIdleCpuTime() != 0 && cpuOp01.getTotalCpuTime() !=0
                    && cpuOp02.getIdleCpuTime() != 0 && cpuOp02.getTotalCpuTime() !=0){
                cpuUsage = 1 - (double)(cpuOp02.getIdleCpuTime() - cpuOp01.getIdleCpuTime())
                        / (double)(cpuOp02.getTotalCpuTime() - cpuOp01.getTotalCpuTime());
            }
        } catch (IOException e) {
            logger.error("CPU % usage get error", e);
        }
        return super.to2DotDouble(cpuUsage);
    }

    @Override
    public Double getDiskUsage() {
        Double diskUsage = Double.NaN;
        Process pro = null;
        BufferedReader in = null;
        String line = null;
        Runtime r = Runtime.getRuntime();
        try {
            pro = r.exec(diskCmd, new String[]{USER_DIR});
            in = new BufferedReader(new InputStreamReader(pro.getInputStream()));
            int i = 0;
            while((line=in.readLine()) != null){
                if(i++ == 0) continue;
                String[] diskInfo = line.split("\\s+");
                String str = StringUtils.remove(diskInfo[4], "%");
                diskUsage = NumberUtils.toDouble(str, 0.0d) / 100.0d;
                break;
            }
            IOUtils.closeQuietly(in);
            pro.destroy();
        }catch (IOException e){
            logger.error("DISK % usage get error", e);
        }
        return super.to2DotDouble(diskUsage);
    }

    @Override
    public Double getMemUsage() {
        Double memUsage = Double.NaN;
        String line = null;
        Process pro = null;
        Runtime r = Runtime.getRuntime();
        try {
            pro = r.exec(memCmd);
            BufferedReader in = new BufferedReader(new InputStreamReader(pro.getInputStream()));
            int count = 0;
            long totalMem = 0, freeMem = 0;
            while((line=in.readLine()) != null){
                String[] memInfo = line.split("\\s+");
                if(memInfo[0].startsWith("MemTotal")){
                    totalMem = Long.parseLong(memInfo[1]);
                }
                if(memInfo[0].startsWith("MemFree")){
                    freeMem = Long.parseLong(memInfo[1]);
                }
                memUsage = 1 - (double)freeMem/(double)totalMem;
                if(++count == 2){
                    break;
                }
            }
            IOUtils.closeQuietly(in);
            pro.destroy();
        } catch (IOException e) {
            logger.error("MEM % usage get error", e);
        }
        return super.to2DotDouble(memUsage);
    }

    /**
     * 单核cpu
     */
    protected CpuAttr operCpu(BufferedReader in) throws IOException {
        //分别为系统启动后空闲的CPU时间和总的CPU时间
        String line = null;
        long idleCpuTime = 0, totalCpuTime = 0;
        while((line=in.readLine()) != null){
            if(line.startsWith("cpu")){
                line = line.trim();
                String[] temp = line.split("\\s+");
                idleCpuTime = Long.parseLong(temp[4]);
                for(String s : temp){
                    if(!s.equals("cpu")){
                        totalCpuTime += Long.parseLong(s);
                    }
                }
                break;
            }
        }
        CpuAttr attr = new CpuAttr(idleCpuTime, totalCpuTime);
        return attr;
    }

    /**
     *******Other Entity*****
     */
    class CpuAttr{
        private long idleCpuTime;
        private long totalCpuTime;
        public CpuAttr(long idleCpuTime, long totalCpuTime){
            this.idleCpuTime = idleCpuTime;
            this.totalCpuTime = totalCpuTime;
        }

        public long getIdleCpuTime() {
            return idleCpuTime;
        }

        public void setIdleCpuTime(long idleCpuTime) {
            this.idleCpuTime = idleCpuTime;
        }

        public long getTotalCpuTime() {
            return totalCpuTime;
        }

        public void setTotalCpuTime(long totalCpuTime) {
            this.totalCpuTime = totalCpuTime;
        }
    }

}
