package com.cooker.zoom.helper.utils.extend.file;

import java.io.File;

/**
 * Created by yu.kequn on 2018-05-24.
 * 顶级文件工具
 */
public class TopFileUtils implements IFileSizeUtils{
    public static boolean forceDeletePath(File path) {
        if (path == null) {
            return false;
        }
        if (path.exists() && path.isDirectory()) {
            File[] files = path.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    forceDeletePath(file);
                } else {
                    file.delete();
                }
            }
        }
        return path.delete();
    }
}
