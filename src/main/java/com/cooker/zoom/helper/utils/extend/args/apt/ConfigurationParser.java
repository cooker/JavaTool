package com.cooker.zoom.helper.utils.extend.args.apt;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.io.LineProcessor;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * Created by yu.kequn on 2018-05-29.
 */
public class ConfigurationParser implements LineProcessor<Configuration> {

    private int lineNumber = 0;
    private final ImmutableList.Builder<Configuration.VerifierInfo> verifierInfoBuilder = ImmutableList.builder();
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    Configuration configuration = null;
    public ConfigurationParser(Configuration configuration){
        this.configuration = configuration;
    }

    @Override
    public boolean processLine(String line) throws IOException {
        ++lineNumber;
        String trimmed = StringUtils.trimToEmpty(line);
        if (!trimmed.isEmpty() && !trimmed.startsWith("#")) {
            List<String> parts = Lists.newArrayList(trimmed.split(" "));
            if (CollectionUtils.isEmpty(parts)) {
                throw new Configuration.ConfigurationException("Invalid line: %s @%d", trimmed, lineNumber);
            }

            int size = CollectionUtils.size(parts);

            String type = parts.remove(0);

            if ("verifier".equals(type)) {
                if (size != 3) {
                    throw new Configuration.ConfigurationException("Invalid verifier line: %s @%d", trimmed, lineNumber);
                }
                verifierInfoBuilder.add(new Configuration.VerifierInfo(type, parts.get(0), parts.get(1)));
            } else {
                logger.warn("Did not recognize entry type {} for line: {} @{}",
                        type, trimmed, lineNumber);
            }
        }
        return true;
    }

    @Override
    public Configuration getResult() {
        configuration.verifierInfos = ImmutableSet.copyOf(verifierInfoBuilder.build());
        return configuration;
    }
}
