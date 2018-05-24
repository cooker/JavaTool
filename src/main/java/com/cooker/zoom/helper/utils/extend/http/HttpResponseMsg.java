package com.cooker.zoom.helper.utils.extend.http;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by yu.kequn on 2017/8/10.
 */
public class HttpResponseMsg {
    public static final HttpResponseMsg SUCCESS = new HttpResponseMsg(1, "success");
    public static final HttpResponseMsg FAIL = new HttpResponseMsg(-1, "fail");
    public static final HttpResponseMsg IO_ERROR = new HttpResponseMsg(-500, "Network IO Exception");
    public static final HttpResponseMsg TIME_OUT = new HttpResponseMsg(-1000, "time out");

    int state;
    String msg;

    private HttpResponseMsg(int state, String msg){
        this.state = state;
        this.msg = msg;
    }

    public int getState() {
        return state;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return String.format("{\"state\": \"%s\", \"msg\":\"%s\"}", state, msg);
    }

    public static boolean isStrictEqual(HttpResponseMsg a, HttpResponseMsg b){
        boolean flag = false;
        flag = isEqual(a, b);
        if(flag == true){
            if(StringUtils.equals(a.getMsg(), b.getMsg()))
                flag = true;
            else
                flag = false;
        }
        return false;
    }

    public static boolean isEqual(HttpResponseMsg a, HttpResponseMsg b){
        boolean flag = false;
        if(ObjectUtils.allNotNull(a, b)){
            if (a.getState() == b.getState())
                flag = true;
        }
        return flag;
    }
}
