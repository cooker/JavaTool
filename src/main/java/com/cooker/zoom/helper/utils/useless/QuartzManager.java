//package com.cooker.zoom.helper.utils.lower;
//
//import org.quartz.*;
//import org.quartz.impl.StdSchedulerFactory;
//
//import java.text.ParseException;
//
//import static org.quartz.CronScheduleBuilder.cronSchedule;
//
///**
// * Created by yu.kequn on 2018-01-10.
// */
//public class QuartzManager {
//    private static SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();
//    private static String JOB_GROUP_NAME = "EXTJWEB_JOBGROUP_NAME";
//    private static String TRIGGER_GROUP_NAME = "EXTJWEB_TRIGGERGROUP_NAME";
//
//    /**
//     * 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
//     *
//     * @param jobName
//     *            任务名
//     * @param jobClass
//     *            任务
//     * @param time
//     *            时间设置，参考quartz说明文档
//     * @throws ParseException
//     */
//    public static void addJob(String jobName, Class jobClass, String time) {
//        try {
//            Scheduler sched = gSchedulerFactory.getScheduler();
//            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, JOB_GROUP_NAME).build();// 任务名，任务组，任务执行类
//            // 触发器
//            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, TRIGGER_GROUP_NAME)
//                    .withSchedule(cronSchedule(time)).forJob(jobDetail).build();// 触发器名,触发器组
//            sched.scheduleJob(jobDetail, trigger);
//            // 启动
//            if (!sched.isShutdown()){
//                sched.start();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }
//
//    /**
//     * 关闭所有定时任务
//     */
//    public static void shutdownJobs() {
//        try {
//            Scheduler sched = gSchedulerFactory.getScheduler();
//            if(!sched.isShutdown()) {
//                sched.shutdown();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }
//}
//
//
