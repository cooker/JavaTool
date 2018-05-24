//package com.cooker.zoom.helper.utils.lower;
//
//import com.xforceplus.apollo.config.EmailConfig;
//import com.xforceplus.apollo.logger.ApolloEmailFactory;
//import com.xforceplus.apollo.utils.ErrorUtil;
//import org.apache.qpid.client.PooledConnectionFactory;
//import org.apache.qpid.url.URLSyntaxException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.jms.Connection;
//
//import static com.xforceplus.apollo.config.InnerMQConfig.getConfig;
//
///**
// * 版权：    上海云砺信息科技有限公司<br/>
// * 创建者:   yu.kequn<br/>
// * 创建时间: 2017/6/19 16:50<br/>
// * 功能描述: QPid连接创建工厂<br/>
// * 修改历史:<br/>
// * 2017/6/19 16:50 yu.kequn 描述<br/>
// */
//public class QpidConnectionFactory {
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//    private final static PooledConnectionFactory pool;
//    private final static PooledConnectionFactory queryPool;
//
//    static {
//        pool = new PooledConnectionFactory();
//        queryPool = new PooledConnectionFactory();
//        String url = getConfig().getProperty("connectionfactory.qpidConnectionfactory");
//        long timeOut = getConfig().getIntProperty("connection.router.timeout", 3000);
//        try {
//            pool.setConnectionURLString(url);
//            pool.setMaxPoolSize(getConfig().getIntProperty("connection.router.maxpoolsize", 120));
//            pool.setConnectionTimeout(timeOut);
//            queryPool.setConnectionURLString(url);
//            queryPool.setMaxPoolSize(10);
//            queryPool.setConnectionTimeout(timeOut);
//        } catch (URLSyntaxException e) {
//            ApolloEmailFactory.getFactory().sendEmail("日志收集连接池初始化异常：" +  ErrorUtil.getStackMsg(e));
//        }
//    }
//
//    public Connection getQueryConnection(){
//        Connection con = null;
//        try {
//            con = queryPool.createConnection();
//            con.start();
//        } catch (Exception e) {
//            errConn(e);
//        }
//        return con;
//    }
//
//    public Connection getConnection(){
//        Connection connection = null;
//        try {
//            connection = pool.createConnection();//new XAMQConnection(getConfig().getProperty("connectionfactory.qpidConnectionfactory"));
//            connection.start();
//        } catch (Exception e) {
//            errConn(e);
//        }
//        return connection;
//    }
//
//    protected void errConn(Exception e){
//        logger.error("Qpid连接获取出现异常", e);
//        ApolloEmailFactory.getFactory().sendEmail(
//                EmailConfig.getConfig().getProperty("title"), String.format("%s 获取Qpid连接异常，请处理<br/>%s",
//                        "logcollect", ErrorUtil.getStackMsg(e)), EmailConfig.getConfig().getProperty("receivers"));
//    }
//
//    public void releaseConnection(Connection con){
//        if(con != null)
//            try {
//                con.close();
//            } catch (Exception e) {
//                logger.error("释放Qpid连接出现异常！");
//                ApolloEmailFactory.getFactory().sendEmail(
//                        EmailConfig.getConfig().getProperty("title"), String.format("%s 释放Qpid连接异常，请处理<br/>%s",
//                                "logcollect", ErrorUtil.getStackMsg(e)),EmailConfig.getConfig().getProperty("receivers"));
//            }
//    }
//
//}