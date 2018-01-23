package com.cooker.zoom.helper.utils.extend;

import org.apache.commons.io.IOUtils;

public class EIOUtils extends IOUtils {
    public static void destroy(final Process process) {
        if (process != null) {
            try {
                process.destroy();
            }
            catch (Exception e) {
                // ignore
            }
        }
    }
}
