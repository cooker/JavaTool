package com.cooker.zoom.helper.utils.extend.args.constraints;//package com.xforceplus.apollo.core.utils.args.constraints;
//
//import com.google.common.base.Preconditions;
//import com.xforceplus.apollo.core.utils.args.BaseUnionVerifier;
//import com.xforceplus.apollo.core.utils.args.Verifier;
//import com.xforceplus.apollo.core.utils.args.VerifierFor;
//import com.xforceplus.apollo.core.utils.args.apt.Configuration;
//import org.apache.commons.collections.MapUtils;
//import org.apache.commons.lang3.StringUtils;
//
//import java.lang.annotation.Annotation;
//import java.util.Map;
//import java.util.Set;
//
///**
// * Created by yu.kequn on 2018-05-29.
// */
//@VerifierFor(ValueUnion.class)
//public class ValueUnionVerifier extends BaseUnionVerifier implements Verifier<String> {
//
//    @Override
//    public void verify(String fieldName, String value, Annotation annotation) throws IllegalArgumentException {
//        ValueUnion ann = (ValueUnion) annotation;
//        boolean isUnion = ann.isUnion();
//        String union = ann.union();
//        String aVal = ann.value();
//        Class<? extends Annotation> annClass = ann.verifierFor();
//        if(!isUnion){
//            Map<String, Set<String>> unVals = getUnVals();
//            if(MapUtils.isEmpty(unVals) || StringUtils.isEmpty(aVal)) return;
//            Set<String> sets = unVals.get(union);
//            if(sets.contains(aVal)){
//                Map<String, Verifier> verifiers = super.getVerifiers();
//                if(MapUtils.isEmpty(verifiers)) return;
//                verifiers
//            }
//        }
//    }
//
//    @Override
//    public String toString(Class<? extends String> argType, Annotation annotation) {
//        return "字段联合校验";
//    }
//}
