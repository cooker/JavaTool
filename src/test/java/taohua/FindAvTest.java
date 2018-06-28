package taohua;

import com.cooker.zoom.helper.utils.convert.JacksonUtils;
import com.cooker.zoom.helper.utils.extend.http.HttpUtils;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FindAvTest {

    static ExecutorService cachedThreadPool = Executors.newFixedThreadPool(30);

    public static void main(String[] args) {
        String url ="http://taohuabt.cc/forum-181-";
        List<String> errList = Lists.newArrayList();
        int max = 351;
        String fe = ".html";
        for (int i=0;i<=max;i++){
            String uurl = url + i + fe;
            int a = i;
            cachedThreadPool.submit(()->{
                String resp;
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                }
                try {
                     resp = HttpUtils.doPostForm(uurl, null);
                    if(StringUtils.contains(resp, "北川瞳")){
                        System.out.println(uurl);
                    }
                } catch (Exception | Error e) {
                    errList.add(a +"");
                }
            });
        }
        cachedThreadPool.shutdown();//只是不能再提交新任务，等待执行的任务不受影响

        try {
            boolean loop = true;
            do {    //等待所有任务完成
                loop = !cachedThreadPool.awaitTermination(2, TimeUnit.SECONDS);  //阻塞，直到线程池里所有任务结束
            } while(loop);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("失败》》》" + errList);
    }



    @Test
    public void again(){
        String json = "[67, 1, 104, 102, 110, 119, 121, 128, 45, 36, 61, 158, 191, 194, 214, 233, 239, 247, 42, 8, 60, 97, 258, 270, 286, 293, 300, 301, 314, 248, 324, 328, 331, 346, 348, 140, 31, 49, 46, 62, 23, 40, 83, 59, 92, 99, 52, 50, 37, 73, 81, 11, 64, 74, 28, 71, 0, 10, 44, 65, 54, 13, 7, 84, 39, 98, 66, 94, 16, 4, 6, 70, 147, 146, 148, 149, 150]";
        List<Integer> list = JacksonUtils.json2Object(json, List.class);
        String url ="http://taohuabt.cc/forum-181-";
        String fe = ".html";

        for (Integer i : list){
            String uurl = url + i + fe;
            cachedThreadPool.submit(()->{
                String resp;
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                }
                try {
                    resp = HttpUtils.doPostForm(uurl, null);
                    if(StringUtils.contains(resp, "愛乃なみ")){
                        System.out.println(uurl);
                    }
                } catch (Exception | Error e) {
                }
            });
        }
        cachedThreadPool.shutdown();//只是不能再提交新任务，等待执行的任务不受影响

        try {
            boolean loop = true;
            do {    //等待所有任务完成
                loop = !cachedThreadPool.awaitTermination(2, TimeUnit.SECONDS);  //阻塞，直到线程池里所有任务结束
            } while(loop);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
