package base;

import com.cooker.zoom.helper.utils.base.StartWatch;
import org.junit.Test;

/**
 * Created by yu.kequn on 2018-05-24.
 */
public class StartWatchTest {

    @Test
    public void runTimeLog() throws InterruptedException {
        System.out.println("Run");
        StartWatch timer = new StartWatch();
        timer.start();
        Thread.sleep(1000L);
        timer.stop();

        System.out.println("起始时间：" + timer.getStartTime());
        System.out.println("运行时间：" + timer.getTime());

    }
}
