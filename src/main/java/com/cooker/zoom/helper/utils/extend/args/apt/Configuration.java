package com.cooker.zoom.helper.utils.extend.args.apt;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.common.io.Resources;
import com.google.common.reflect.ClassPath;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by yu.kequn on 2018-05-29.
 */
public final class Configuration {
    public static class ConfigurationException extends RuntimeException {
        public ConfigurationException(String message, Object... args) {
            super(String.format(message, args));
        }
        public ConfigurationException(Throwable cause) {
            super(cause);
        }
    }
    static final String DEFAULT_RESOURCE_PACKAGE = "config";
    private static final String DEFAULT_RESOURCE_PREFIX;
    static {
        DEFAULT_RESOURCE_PREFIX = DEFAULT_RESOURCE_PACKAGE.replace('.', '/') + "/";
    };
    static final String DEFAULT_RESOURCE_SUFFIX = ".vfer";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private volatile boolean isLoad = false;
    protected ImmutableSet<VerifierInfo> verifierInfos = ImmutableSet.of();
//    protected ImmutableSet<UnionVerifierInfo> unionVerifierInfos = ImmutableSet.of();

    String resPrefix = "";

    public Configuration(){
        this("");
    }

    public Configuration(String configPkg){
        resPrefix = StringUtils.isEmpty(configPkg) ? DEFAULT_RESOURCE_PREFIX :
                configPkg.replace('.', '/') + "/";
    }

    public Configuration(Iterable<VerifierInfo> verifierInfos){
        this.verifierInfos = ImmutableSet.copyOf(verifierInfos);
//        this.unionVerifierInfos = ImmutableSet.copyOf(unionVerifierInfos);
        this.isLoad = true;
    }

    public String getResPrefix() {
        return resPrefix;
    }

    public void load() throws IOException{
        if(isLoad) return;
        isLoad = true;
        logger.info("<<<读取参数处理器配置>>>Jar!= {}", resPrefix);
        Map<String, URL> resources = getLiveResources(resPrefix);
        resources.keySet().stream().forEach(key->{
            logger.info("<<<参数处理器 {} 加载>>>", key);
        });
        Set<String> verifiers = Sets.newHashSet();
        resources.values().stream().forEach(url->{
            List<String> lists = null;
            try {
                lists = Resources.readLines(url, Charset.forName("UTF-8"));
            } catch (IOException e) {
                ;
            }
            if(CollectionUtils.isNotEmpty(lists)){
                verifiers.addAll(lists);
            }
        });
        //加载**器
        loadArgBox(verifiers);
    }

    private void loadArgBox(Set<String> verifiers){
        ConfigurationParser parser = new ConfigurationParser(this);
        verifiers.stream().forEach(line -> {
            try {
                parser.processLine(line);
            } catch (IOException e) {
                logger.error("<<<参数处理器>>> 加载异常", e);
            }
        });
        parser.getResult();
    }

    public Iterable<VerifierInfo> verifierInfo() {
        return verifierInfos;
    }

    private static String checkValidIdentifier(String identifier, boolean compound) {
        Preconditions.checkNotNull(identifier);
        String className = StringUtils.trimToEmpty(identifier);
        if(compound == true){
            if(!classExists(className)){
                className = "";
            }
        }
        return className;
    }

    public static ClassLoader getClassLoader(){
        return Configuration.class.getClassLoader();
    }

    private static Map<String, URL> getLiveResources(String resPrefix) throws IOException {
        ClassLoader classLoader = getClassLoader();
        ClassPath classPath = ClassPath.from(classLoader);
        Map<String, URL> resources = new HashMap<>();
        for (ClassPath.ResourceInfo resourceInfo : classPath.getResources()) {
            String name = resourceInfo.getResourceName();
            // Find relevant resource files.
            if (name.startsWith(resPrefix) && name.endsWith(DEFAULT_RESOURCE_SUFFIX)) {
                String confName =
                        name.substring(
                                resPrefix.length(),
                                name.length() - DEFAULT_RESOURCE_SUFFIX.length());
                // Include only those resources for live classes.
                resources.put(confName, resourceInfo.url());
            }
        }
        return Collections.synchronizedMap(resources);
    }

    private static boolean classExists(ClassLoader cl, String className) {
        try {
            cl.loadClass(className);
            return true;
        } catch (ClassNotFoundException e) {
            // Expected for classes removed during incremental compilation.
            return false;
        }
    }

    private static boolean classExists(String className){
        return classExists(getClassLoader(), className);
    }

//    public static final class UnionVerifierInfo extends VerifierInfo{
//        public UnionVerifierInfo(String verifiedType, String verifyingAnnotation, String verifierClass) {
//            super(verifiedType, verifyingAnnotation, verifierClass);
//        }
//    }

    public static class VerifierInfo {
        public final String verifiedType;
        public final String verifyingAnnotation;
        public final String verifierClass;

        public VerifierInfo(String verifiedType, String verifyingAnnotation, String verifierClass) {
            this.verifiedType = checkValidIdentifier(verifiedType, false);
            this.verifyingAnnotation = checkValidIdentifier(verifyingAnnotation, true);
            this.verifierClass = checkValidIdentifier(verifierClass, true);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }

            if (!(obj instanceof VerifierInfo)) {
                return false;
            }

            VerifierInfo other = (VerifierInfo) obj;

            return new EqualsBuilder()
                    .append(verifiedType, other.verifiedType)
                    .append(verifyingAnnotation, other.verifyingAnnotation)
                    .append(verifierClass, other.verifierClass)
                    .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder()
                    .append(verifiedType)
                    .append(verifyingAnnotation)
                    .append(verifierClass)
                    .toHashCode();
        }

        @Override public String toString() {
            return new ToStringBuilder(this)
                    .append("verifiedType", verifiedType)
                    .append("verifyingAnnotation", verifyingAnnotation)
                    .append("verifierClass", verifierClass)
                    .toString();
        }
    }

}
