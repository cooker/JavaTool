package com.cooker.zoom.helper.utils.base.prop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by yu.kequn on 2018-06-29.
 */
public final class AutoWatcher {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    //配置检查路径
    static String WATCH_PATH = ".";
    private IWatch watch;
    String path = null;
    WatchService watchService = null;
    AtomicBoolean isRun = new AtomicBoolean(false);
    public AutoWatcher(IWatch watch) {
        this(WATCH_PATH, watch);
    }

    public AutoWatcher(String watchPath, IWatch watch) {
        this.path = watchPath;
        this.watch = watch;
    }

    public synchronized AutoWatcher init() throws IOException, InterruptedException {
        if(watchService == null){
            isRun.set(true);
            watchService = FileSystems.getDefault().newWatchService();
            Paths.get(path).register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
            watchService.poll(3L, TimeUnit.SECONDS);
            final WatchService tWatchService = watchService;
            Thread thread = new Thread(()->{
                try {
                    if(isRun.get()){
                        WatchKey key = watchService.take();
                        key.pollEvents().stream().forEach(e->{
                            watch.doWatch(e.context().toString());
                        });
                        key.reset();
                    }
                } catch (InterruptedException e) {
                    isRun.set(false);
                    logger.error("动态加载配置文件异常", e);
                }

            });
            thread.start();
        }
        return this;
    }

    public boolean isWatching(){
        return isRun.get();
    }


    public interface IWatch{
        void doWatch(String fileName);
    }

}
