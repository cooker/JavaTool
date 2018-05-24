package com.cooker.zoom.helper.utils.extend.weixin.sugar;

import java.util.StringJoiner;

/**
 * Created by yu.kequn on 2017/9/4.
 */
public class SubPostMsg extends AbstractMsg{
    private String sendkey;

    public SubPostMsg(String sendkey, String text, String desp) {
        this.sendkey = sendkey;
        this.text = text;
        this.desp = desp;
    }

    public String getSendkey() {
        return sendkey;
    }

    public void setSendkey(String sendkey) {
        this.sendkey = sendkey;
    }

    public String toJsonString(){
        StringJoiner sjson = new StringJoiner("","{","}");
        sjson.add("\"sendkey\":").
                add("\"").add(sendkey).add("\",");
        sjson.add("\"text\":").
                add("\"").add(text).add("\",");
        sjson.add("\"desp\":").
                add("\"").add(desp).add("\",");
        return sjson.toString();
    }
}
