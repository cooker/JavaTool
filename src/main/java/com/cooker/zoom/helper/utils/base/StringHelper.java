package com.cooker.zoom.helper.utils.base;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringHelper {

    /***
     * 截取字符串中的全部汉字
     * @param str
     * @return
     */
    public static String getCharater(String str) {
        String result = "";
        Pattern p = Pattern.compile("([\u4e00-\u9fa5]+)");
        Matcher m = p.matcher(str);
        while (m.find()) {
            result += m.group();
        }
        return result;
    }

    /**
     * 检验是否是中文
     *
     * @param value
     * @return
     */
    public static boolean checkChineseValue(String value) {
        Pattern p = Pattern.compile("^[\u4e00-\u9fa5]+$");
        Matcher m = p.matcher(value);
        return m.matches();
    }

    /**
     * @param str
     * @return
     */
    public static int getLastLetterValue(String str) {
        int result = -1;
        char[] ch = str.toCharArray();
        char[] chReverse = new char[ch.length];
        for (int i = 0; i < ch.length; i++) {
            chReverse[i] = ch[ch.length - 1 - i];
        }
        for (int i = 0; i < chReverse.length; i++) {
            String chStr = chReverse[i] + "";
            try {
                Integer.parseInt(chStr);
            } catch (Exception e) {
                result = i;
                return chReverse.length - 1 - result;
            }
        }
        return result;
    }

    // 含中文字符串的真实长度
    public static int realStringLengthGBK(String a) {
        a = a + "";
        int num = 0;
        String b = "";
        try {
            b = new String(a.getBytes("GBK"), "ISO8859_1");
            num = b.length();
        } catch (Exception ex) {
            num = a.length();
        }
        return num;
    }


    // 含中文字符串的真实长度
    public static int realStringLengthUTF8(String a) {
        a = a + "";
        int num = 0;
        String b = "";
        try {
            b = new String(a.getBytes("UTF-8"), "ISO8859_1");
            num = b.length();
        } catch (Exception ex) {
            num = a.length();
        }
        return num;
    }


    /**
     * 是否包含中文
     *
     * @param companyNameKey
     * @return
     */
    public static boolean hasChinese(String companyNameKey) {
        boolean flag = false;
        String regEx = "[\u4e00-\u9fa5]";
        Pattern pat = Pattern.compile(regEx);
        Matcher matcher = pat.matcher(companyNameKey);
        if (matcher.find()) {
            flag = true;
        }
        return flag;
    }

    /**
     * 去除字符串中的空格、回车、换行符、制表符
     *
     * @param str
     * @return
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    /**
     * 去除字符串中的回车、换行符、制表符
     *
     * @param str
     * @return
     */
    public static String replaceTab(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    /*
     * 各种字符的unicode编码的范围：
     * 汉字：[0x4e00,0x9fa5]（或十进制[19968,40869]）
     * 数字：[0x30,0x39]（或十进制[48, 57]）
     * 小写字母：[0x61,0x7a]（或十进制[97, 122]）
     * 大写字母：[0x41,0x5a]（或十进制[65, 90]）
     * 判断只能是字母或数字
     */
    public static boolean isLetterDigit(String str) {
        String regex = "^[a-z0-9A-Z0x30-0x39]+$";
        return str.matches(regex);
    }

    public static boolean isAllDigit(String str) {
        String regex = "^[0-90x30-0x39]+$";
        return str.matches(regex);
    }

    /**
     * 判断字符串中是否包含特殊字符	包含返回true
     *
     * @param s
     * @return
     */
    public static boolean containSpecial(String s) {
        boolean flag = false;
        if (!(s.matches("[A-Z0-9]*"))) {
            flag = true;
        }
//		if(!(s.replaceAll("[a-z]*[A-Z]*\\d*-*_*\\s*", "").length()==0)){
//			flag=true;
//		}
        return flag;
    }


    /**
     * 将半角括号转成圆角括号
     *
     * @param str
     * @return
     */
    public static String converToHslf(String str) {
        String strq = (str.trim()).replaceAll("\\(", "（").replaceAll("\\)", "）");
        return strq;
    }


    /***
     * 校验是否时手机号码
     * @param str
     * @return
     */
    public static boolean telPhoneNum(String str) {
        boolean result = false;
        Pattern p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
        Matcher m = p.matcher(str);
        while (m.find()) {
            result = true;
        }
        return result;
    }


    private static final String UNIT = "万仟佰拾亿仟佰拾万仟佰拾圆角分";
    private static final String DIGIT = "零壹贰叁肆伍陆柒捌玖";
    private static final double MAX_VALUE = 9999999999999.99D;

    /**
     * 小写数字转换为大写  （例如 输入111   返回值为壹佰壹拾壹圆整）
     *
     * @param v
     * @return 大写金额
     */
    public static String change(double v) {

        if (v < 0) {
            v = -v;
        }

        if (v < 0 || v > MAX_VALUE)
            return "参数非法!";
        long l = Math.round(v * 100);
        if (l == 0)
            return "零圆整";
        String strValue = l + "";
        // i用来控制数
        int i = 0;
        // j用来控制单位
        int j = UNIT.length() - strValue.length();
        String rs = "";
        boolean isZero = false;
        for (; i < strValue.length(); i++, j++) {
            char ch = strValue.charAt(i);
            if (ch == '0') {
                isZero = true;
                if (UNIT.charAt(j) == '亿' || UNIT.charAt(j) == '万' || UNIT.charAt(j) == '圆') {
                    rs = rs + UNIT.charAt(j);
                    isZero = false;
                }
            } else {
                if (isZero) {
                    rs = rs + "零";
                    isZero = false;
                }
                rs = rs + DIGIT.charAt(ch - '0') + UNIT.charAt(j);
            }
        }
        if (!rs.endsWith("分")) {
            rs = rs + "整";
        }
        if (rs.indexOf("整") < 0 && rs.indexOf("零") < 0) {
            rs = rs.replaceAll("拾圆", "拾圆零");
            rs = rs.replaceAll("仟圆", "仟圆零");
            rs = rs.replaceAll("万圆", "万圆零");
        }
        rs = rs.replaceAll("亿万", "亿");
        return rs;
    }


    public static boolean isGBK(String str) {
        char[] chars = str.toCharArray();
        boolean isGBK = false;
        for (int i = 0; i < chars.length; i++) {
            byte[] bytes = ("" + chars[i]).getBytes();
            if (bytes.length == 2) {
                int[] ints = new int[2];
                ints[0] = bytes[0] & 0xff;
                ints[1] = bytes[1] & 0xff;
                if (ints[0] >= 0x81 && ints[0] <= 0xFE && ints[1] >= 0x40
                        && ints[1] <= 0xFE) {
                    isGBK = true;
                    break;
                }
            }
        }
        return isGBK;
    }

    // 根据Unicode编码完美的判断中文汉字和符号
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }

    //中文标点
    public static boolean isChineseSymbols(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (isChinese(c) || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_FORMS
                || ub == Character.UnicodeBlock.VERTICAL_FORMS) {
            return true;
        }
        Character.UnicodeScript us = Character.UnicodeScript.of(c);

        return us == Character.UnicodeScript.HAN;
    }

    private static boolean isEnglishWord(String charaString) {
        return charaString.matches("^[a-zA-Z]*");
    }

    private static boolean isEnglishSymbol(String charaString) {
        return charaString.matches("^[%&',;=?$_\\-\\(\\)\\[\\]\\*]*");
    }

}
