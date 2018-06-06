package com.cooker.zoom.helper.utils.extend.args.constraints;

import com.cooker.zoom.helper.utils.extend.args.Verifier;
import com.cooker.zoom.helper.utils.extend.args.VerifierFor;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * Created by yu.kequn on 2018-05-29.
 */
@VerifierFor(StringLength.class)
public class StringLengthVerifier implements Verifier<String> {

    @Override
    public void verify(String fieldName, String value, Annotation annotation) throws IllegalArgumentException {
        StringLength tarAnn = (StringLength) annotation;
        int max = tarAnn.max();
        int min = tarAnn.min();
        int len = StringUtils.length(value);
        StringLength.CHECK_MODE mode = tarAnn.mode();
        String tstr = fieldName + "，";
        String errMsg = "";
        switch (mode){
            case CHECK_ALL:
                if(!(min <= len && len <= max)){
                    errMsg = tstr + "长度小于" + min + "或大于" + max;
                }
                break;
            case CHECK_MAX:
                if(len > max) errMsg = tstr + "长度大于" + max;
                break;
            case CHECK_MIN:
                if(len < min) errMsg = tstr + "长度小于" + min;
                break;
            default:
                break;
        }

        checkArgument(isEmpty(errMsg), errMsg);
    }

    @Override
    public String toString(Class<? extends String> argType, Annotation annotation) {
        return "check length";
    }
}
