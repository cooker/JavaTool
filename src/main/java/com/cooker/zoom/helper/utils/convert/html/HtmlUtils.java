package com.cooker.zoom.helper.utils.convert.html;

import java.util.Map;

/**
 * Created by yu.kequn on 2017/8/21.
 */
public class HtmlUtils {
    /**
     * Converts all HTML entities to applicable characters.
     *
     * @param encodedHtml
     *            The encoded HTML
     * @param encodedType
     *            0:html code, 1:decimal code ; default:1
     * @return The decoded HTML
     */
    public static String htmlDecode(final String encodedHtml, int encodedType) {
        String str = encodedHtml;
        for (Map.Entry<String, HtmlChar> entry : HtmlCharCode.DECODED_ENTITIES
                .entrySet()) {
            switch (encodedType) {
                case 0:
                    str = str.replaceAll(entry.getKey(), entry.getValue().getName());
                    break;
                case 1:
                    str = str.replaceAll(entry.getKey(), entry.getValue()
                            .getDecimalCode());
                    break;
                default:
                    str = str.replaceAll(entry.getKey(), entry.getValue()
                            .getDecimalCode());
                    break;
            }
        }
        return str;
    }

    /**
     * Convert all applicable characters to HTML entities.
     *
     * @param html
     *            The HTML to encode
     * @param encodedType
     *            0:html code, 1:decimal code ; default:1
     * @return The encoded data
     */
    public static String htmlEncode(final String html, int encodedType) {
        String str = html;
        for (Map.Entry<String, HtmlChar> entry : HtmlCharCode.DECODED_ENTITIES
                .entrySet()) {
            switch (encodedType) {
                case 0:
                    str = str.replaceAll(entry.getValue().getName(), entry.getKey());
                    break;
                case 1:
                    str = str.replaceAll(entry.getValue().getDecimalCode(),
                            entry.getKey());
                    break;
                default:
                    str = str.replaceAll(entry.getValue().getDecimalCode(),
                            entry.getKey());
                    break;
            }
        }
        return str;
    }

    public static String txt2Html(String txt){
        StringBuilder html = new StringBuilder("<!DOCTYPE html>\r\n");
        html.append("<html>");
        html.append("<head>");
        html.append("<meta charset=\"UTF-8\">");
        html.append("</head>");
        html.append("<body>");
        html.append("<pre>").append(txt).append("</pre>");
        html.append("</body>");
        html.append("</html>");
        return html.toString();
    }

}
