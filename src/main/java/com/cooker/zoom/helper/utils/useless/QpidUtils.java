//package com.cooker.zoom.helper.utils.lower;
//
//import org.apache.qpid.client.AMQAnyDestination;
//import org.apache.qpid.client.AMQConnection;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.jms.*;
//import java.net.URISyntaxException;
//
///**
// * Created by yu.kequn on 2017/6/19.
// * Qpid操作工具类
// */
//public class QpidUtils {
//    private QpidConnectionFactory factory;
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
////    private ThreadLocal<Connection> localConnection = null;
//    private volatile boolean isFrist = true;
//    private volatile MessageConsumer consumer = null;
//    private volatile Connection con;
//    public QpidUtils(QpidConnectionFactory factory){
//        this.factory = factory;
////        localConnection = new ThreadLocal<Connection>(){
////            @Override
////            protected Connection initialValue() {
////                return QpidUtils.this.factory.getConnection();
////            }
////        };
//    }
//
//    public Message receiveQueue(String destination){
//        Message message = null;
//        try {
//            MessageConsumer consumer = getVaildConsumer(destination);
//            if(consumer == null){
//                logger.warn("{} 无效的Qpid消费者...", destination);
//            }else{
//                message = consumer.receive(3000);
//            }
//        } catch (Exception e) {
//            release(con);
//            logger.error("【{}】队列消息获取出现异常", destination, e);
//        }
//
//        return message;
//    }
//
//    protected MessageConsumer getVaildConsumer(String destination) throws JMSException, URISyntaxException {
////        Connection con = localConnection.get();
////        if(!checkConn(con)) {
////            //释放原链接
////            release(con);
////            localConnection.set(factory.getConnection());
////            return null;
////        }
//        if(con == null){
//            con = factory.getConnection();
//        }
//        MessageConsumer funConsumer = null;
//        if(isFrist){
//            isFrist = false;
//            Session session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
//            funConsumer = session.createConsumer(new AMQAnyDestination("ADDR:"+destination+"; {create: always}"));
//            this.consumer = funConsumer;
//        }else{
//            funConsumer = this.consumer;
//        }
//
//        return funConsumer;
//    }
//
//
//    /**
//     * qpid连接池创建连接无法判断连接状态
//     * @param con
//     * @return
//     */
//    protected boolean checkConn(Connection con){
//        boolean isCon = false;
//        if(con != null){
//            AMQConnection conn = (AMQConnection) con;
//            if(conn.isConnected()){
//                isCon = true;
//            }
//        }
//        return isCon;
//    }
//
//    protected void release(Connection conn){
//        isFrist = true;
//        consumer = null;
//        this.con = null;
//        factory.releaseConnection(conn);
//    }
//
//    public void release(){
//        release(con);
//    }
//
//}