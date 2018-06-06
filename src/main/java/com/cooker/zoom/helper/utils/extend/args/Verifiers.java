package com.cooker.zoom.helper.utils.extend.args;

import com.cooker.zoom.helper.utils.extend.args.apt.Configuration;
import com.cooker.zoom.helper.utils.extend.args.constraints.ValueUnion;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

/**
 * Created by yu.kequn on 2018-05-25.
 */
public class Verifiers {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private Configuration conf;
    private Map<String, Verifier> verifierMap = Maps.newLinkedHashMap();
    public static Verifiers buildDefaultVerifiers() throws IOException {
        return buildVerifiers("");
    }

    public static Verifiers buildVerifiers(String configPkg) throws IOException {
        Verifiers verifiers = new Verifiers();
        verifiers.conf = new Configuration(configPkg);
        return load(verifiers);
    }

    public static Verifiers buildVerifiers(Iterable<Configuration.VerifierInfo> verifierInfos) throws IOException {
        Verifiers verifiers = new Verifiers();
        verifiers.conf = new Configuration(verifierInfos);
        return load(verifiers);
    }
    private static Verifiers load(Verifiers verifiers) throws IOException {
        verifiers.conf.load();
        verifiers.loadVerifiers();
        return verifiers;
    }

    private void loadVerifiers(){
        Iterable<Configuration.VerifierInfo> iterable = this.verifierInfo();
        if(iterable == null){
            logger.warn("Jar!={} 加载参数处理器失败", conf.getResPrefix());
        }else{
            Iterator<Configuration.VerifierInfo> iterator = iterable.iterator();
            while (iterator.hasNext()){
                Configuration.VerifierInfo verifierInfo = iterator.next();
                String className = verifierInfo.verifierClass;
                try {
                    Verifier verifier = (Verifier) Class.forName(className).newInstance();
                    verifierMap.put(verifierInfo.verifyingAnnotation, verifier);
                } catch (Exception e) {
                    logger.warn("参数处理器{} 初始化异常", className, e);
                }
            }
        }
    }

    public Iterable<Configuration.VerifierInfo> verifierInfo() {
        return conf.verifierInfo();
    }

    public void processVerify(Object entity) throws IllegalArgumentException{
        checkNotNull(entity, "检验实体为空");
        List<Field> fields = FieldUtils.getAllFieldsList(entity.getClass());
        if(CollectionUtils.isEmpty(fields)) return;
        final Map<String, List<String>> unVals = Maps.newHashMap();
        fields.stream().forEach(field -> {
            Annotation[] anns = field.getAnnotations();
            if(ArrayUtils.isEmpty(anns)) return;
            Arrays.stream(anns).forEach(ann -> {
                if(ann instanceof ValueUnion){
                    ValueUnion uAnn = ((ValueUnion) ann);
                    if(uAnn.isUnion()){
                        String[] strs = uAnn.value();
                        field.setAccessible(true);
                        String val = "";
                        try {
                            val = Objects.toString(field.get(entity));
                        } catch (IllegalAccessException e) {
                            ;
                        }
                        boolean canUnion = false;
                        if(ArrayUtils.contains(strs, val)){
                            canUnion = true;
                        }
                        if(canUnion){
                            if(unVals.get(uAnn.union()) == null){
                                unVals.put(uAnn.union(), Lists.newArrayList(val));
                            }else{
                                unVals.get(uAnn.union()).add(val);
                            }
                        }
                    }
                }
            });
        });
        fields.stream().forEach(field -> {
            Annotation[] anns = field.getAnnotations();
            field.setAccessible(true);
            try {
                Object oval = field.get(entity);
                processVerify(field.getName(), oval, unVals, anns);
            } catch (IllegalAccessException e) {
                ;
            }
        });
    }

    protected void processVerify(String fieldName, Object value, Map<String,
            List<String>> unVals, Annotation... annotation) throws IllegalArgumentException{
        int len = ArrayUtils.getLength(annotation);
        if(len == 0) return;
        doVerify(fieldName, value, unVals, annotation);
    }

    protected void doVerify(String fieldName, Object value, Map<String,
            List<String>> unVals, Annotation... annotation) throws IllegalArgumentException{
        boolean isUnion = false;
        ValueUnion ann = null;
        List<Annotation> anns = Lists.newArrayList();

        for (Annotation an : annotation){
            if(an instanceof ValueUnion) {
                ann = (ValueUnion) an;
                isUnion = ann.isUnion();
//                String[] strs = unVals.get(ann.union());
//                if(ArrayUtils.getLength(strs) == 0){
//                    unVals.put(ann.union(), ann.value());
//                }
            }else{
                anns.add(an);
            }
        }
        boolean doVerify = false;
        if(isUnion){
            //为校验单元，可检验本身属性
            if(isNotEmpty(anns)){
                for (Annotation an : anns){
                    Verifier verifier = verifierMap.get(an.annotationType().getName());
                    if(verifier != null) {
                        verifier.verify(fieldName, value, an);
                    }
                }
//                this.doVerify(fieldName, value, unVals, (Annotation[]) anns.toArray());
            }
        }else if(ann != null && !isUnion){
           //为子单元，需关联检验单元，才能校验
            List<String> strs = unVals.get(ann.union());
            String[] zstrs = ann.value();
            //不校验
            if(CollectionUtils.isEmpty(strs) || ArrayUtils.isEmpty(zstrs)) return;
            for(String zstr : zstrs){
                if(strs.contains(zstr)){
                    doVerify = true;
                }
            }
        }

        if(doVerify || ann == null){
            for (Annotation an : anns){
                Verifier verifier = verifierMap.get(an.annotationType().getName());
                if(verifier != null) {
                    verifier.verify(fieldName, value, an);
                }
            }
        }

    }
}
