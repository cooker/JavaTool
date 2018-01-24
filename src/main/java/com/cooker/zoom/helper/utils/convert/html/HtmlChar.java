package com.cooker.zoom.helper.utils.convert.html;

/**
 * HTML special character
 * Created by yu.kequn on 2017/8/21.
 */
public final class HtmlChar {

    private String html;
    private String name;
    private String decimalCode;

    public HtmlChar() {
        super();
    }

    public HtmlChar(String html, String name, String decimalCode) {
        super();
        this.html = html;
        this.name = name;
        this.decimalCode = decimalCode;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDecimalCode() {
        return decimalCode;
    }

    public void setDecimalCode(String decimalCode) {
        this.decimalCode = decimalCode;
    }
}
