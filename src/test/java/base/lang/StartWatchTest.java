package base.lang;

import com.cooker.zoom.helper.utils.base.StartWatch;
import com.cooker.zoom.helper.utils.builder.FieldDealUtils;
import com.cooker.zoom.helper.utils.convert.JacksonUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.ThreadUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.*;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by yu.kequn on 2018-05-24.
 */
public class StartWatchTest {
    private ThreadLocal<String> threadLocal = new ThreadLocal<>();

    @Test
    public void runTimeLog() throws InterruptedException {
        System.out.println("Run");
        StartWatch timer = new StartWatch();
        timer.start();
        Thread.sleep(1000L);
        timer.split();
        System.out.println(timer.toString());
        System.out.println(timer.getSplitTime());
        timer.split();
        System.out.println(timer.getSplitTime());
        Collection<Thread> lists = ThreadUtils.getAllThreads();
        System.out.println(lists);
    }

    @Test
    public void doLocal() throws InterruptedException {
        Thread th = new Thread(()->{
            threadLocal.set("22222");
        });

        th.start();
        Thread tth = th;
        Thread.sleep(1000L);
        th = new Thread(()->{
            System.out.println(threadLocal.get());
        });
        th.start();
        Thread.sleep(1000L);
    }

    @Test
    public void tommm(){
        String sjson = "{\"seqNo\":\"流水号\",\"amountWithTax\":\"原始业务单金额\",\"alreadyMakeAmount\":\"已开金额\",\"salesBillNo\":\"业务单号\",\"salesBillBatchNo\":\"业务批次号\",\"salesOrderNo\":\"销售订单号\",\"salesContractNo\":\"销售合同号\",\"purchaserType\":\"1：企业 2：个人\",\"salesBillType\":\"1：合同 2：合同从属单据\",\"ext1\":\"\",\"ext2\":\"\",\"ext3\":\"\",\"ext4\":\"\",\"ext5\":\"\",\"ext6\":\"\",\"ext7\":\"\",\"ext8\":\"\",\"ext9\":\"\",\"ext10\":\"\",\"ext11\":\"\",\"ext12\":\"\",\"ext13\":\"\",\"ext14\":\"\",\"ext15\":\"\",\"ext16\":\"\",\"ext45\":\"\"}";
        Map<String, String> map = JacksonUtils.json2Object(sjson, Map.class);
        map.forEach((k, v)->{
            System.out.println(k+"|varchar 40||"+v+"|");
        });
    }

    @Test
    public void sa(){
        A ac = new A();
        ac.setId("0");
        System.out.println(JacksonUtils.toJSON(ac));
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class A{
        String id;

        public String getId() {
            if(id == null || "0".equals(id))
                return null;
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public static class B{
        String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    @Test
    public void wathPath() throws IOException, InterruptedException {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        Path path = Paths.get("d:/");
        path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
        WatchKey watchKey = watchService.poll(3, TimeUnit.SECONDS);
        for(;;){
            watchKey = watchService.take();
            watchKey.pollEvents().stream().forEach(e->{
                System.out.println(e.context());
                System.out.println(e.kind().name());
            });
            watchKey.reset();
        }
    }
}

