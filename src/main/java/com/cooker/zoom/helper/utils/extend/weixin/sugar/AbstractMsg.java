package com.cooker.zoom.helper.utils.extend.weixin.sugar;

/**
 * Created by yu.kequn on 2017/9/4.
 */
public abstract class AbstractMsg {
    protected String text;
    protected String desp;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }
}
