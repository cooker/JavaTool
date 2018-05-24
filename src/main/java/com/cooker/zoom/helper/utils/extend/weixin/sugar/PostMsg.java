package com.cooker.zoom.helper.utils.extend.weixin.sugar;

/**
 * Created by yu.kequn on 2017/9/1.
 */
public class PostMsg extends AbstractMsg{
    //Key
    private String sckey;

    public PostMsg(){}

    public PostMsg(String sckey, String text, String desp) {
        this.sckey = sckey;
        this.text = text;
        this.desp = desp;
    }

    public String getSckey() {
        return sckey;
    }

    public void setSckey(String sckey) {
        this.sckey = sckey;
    }

}
