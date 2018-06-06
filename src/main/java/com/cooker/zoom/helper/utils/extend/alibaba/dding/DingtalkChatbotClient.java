package com.cooker.zoom.helper.utils.extend.alibaba.dding;

import com.cooker.zoom.helper.utils.convert.JacksonUtils;
import com.cooker.zoom.helper.utils.extend.alibaba.dding.message.Message;
import com.cooker.zoom.helper.utils.extend.http.HttpUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by dustin on 2017/3/17.
 */
public class DingtalkChatbotClient {

    public static SendResult send(String webhook, Message message) throws Exception {
        String result = HttpUtils.doPostJson(webhook, message.toJsonString());
        SendResult sendResult = new SendResult();
        sendResult.setErrorCode(0);
        sendResult.setErrorMsg("Api调用失败");
        if (StringUtils.isNotEmpty(result)) {
            sendResult = JacksonUtils.json2Object(result, SendResult.class);
        }
        return sendResult;
    }

}


