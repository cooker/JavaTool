//package com.cooker.zoom.helper.utils.lower;
//
//import com.google.common.collect.Lists;
//import com.google.common.collect.Maps;
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.qpid.qmf2.console.Console;
//import org.apache.qpid.qmf2.console.QmfConsoleData;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.jms.Connection;
//import java.nio.charset.Charset;
//import java.util.Collections;
//import java.util.LinkedHashMap;
//import java.util.List;
//
///**
// * 版权：    上海云砺信息科技有限公司<br/>
// * 创建者:   zmh<br/>
// * 创建时间: 2017-05-15-11:31<br/>
// * 功能描述: Qpid查询工具类<br/>
// * 修改历史: <br/>
// */
//public class QpidQueryUtils
//{
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//    private QpidConnectionFactory factory;
//    public QpidQueryUtils(QpidConnectionFactory factory){
//        this.factory = factory;
//    }
//
//    public List<LinkedHashMap<String, String>> getAllQueues(){
//        List<LinkedHashMap<String, String>> _values = Collections.emptyList();
//        Connection con = null;
//        Console console = null;
//        List<QmfConsoleData> list = null;
//        try {
//            console = new Console();
//            con = factory.getQueryConnection();
//            console.disableEvents();//关闭事件
//            console.addConnection(con);
//            list = console.getObjects("queue",6000);
//            if(!CollectionUtils.isEmpty(list)){
//                _values = Lists.newArrayList();
//                int len = list.size();
//                QmfConsoleData data = null;
//                String val = null;
//                LinkedHashMap<String, String> map = null;
//                long msgDepth = 0;
//                for(int i=0;i<len;i++){
//                    data = list.get(i);
//                    map = Maps.newLinkedHashMap();
//                    val = StringUtils.toEncodedString((byte[])data.getValue("name"), Charset.forName("utf-8"));
//                    if(StringUtils.contains(val,"TempQueue")) continue;
//                    map.put("name",val);
//                    msgDepth = (Long)data.getValue("msgDepth");
//                    val = msgDepth +"";
//                    map.put("msgDepth", val);//队列大小
//                    _values.add(map);
//                }
//                _values.add(map);
//            }
//        } catch (Exception e) {
//            logger.error("Qpid队列信息获取异常", e);
//        } finally {
//            if (console != null)
//                console.destroy();
//        }
//        return _values;
//    }
//}
