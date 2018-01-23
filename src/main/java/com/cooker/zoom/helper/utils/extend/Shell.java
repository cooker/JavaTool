package com.cooker.zoom.helper.utils.extend;

import org.apache.commons.logging.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static com.cooker.zoom.helper.utils.extend.EIOUtils.destroy;
import static org.apache.commons.io.IOUtils.closeQuietly;
import static org.apache.commons.io.IOUtils.toBufferedReader;

public class Shell {
    Shell(){
        super();
    }

    public static void exec(final String[] cmd, Log log){
        Process process = null;
        BufferedReader read = null;
        BufferedReader errorRead = null;
        try {
            process = Runtime.getRuntime().exec(cmd);
            String line = null;
            read = toBufferedReader(new InputStreamReader(process.getInputStream()));
            while ((line = read.readLine()) != null) {
                log.info(line);
            }
            errorRead = toBufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((line = errorRead.readLine()) != null) {
                log.error(line);
            }
        }
        catch (Exception e) {
            log.error("命令[" + cmd + "]执行失败,错误信息:", e);
        }
        finally {
            closeQuietly(read, errorRead);
            destroy(process);
        }
    }

}
