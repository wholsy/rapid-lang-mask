package com.whosly.rapid.lang.mask.internals.tacitly;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigChangeListener;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.whosly.rapid.lang.mask.internals.WatchConfigureType;
import com.whosly.rapid.lang.mask.spi.IWatchServiceConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * apollo 配置中心
 */
public class ApolloWatchServiceConfiguration implements IWatchServiceConfiguration, IWatchServiceSupport {

    private static final Logger logger = LoggerFactory.getLogger(ApolloWatchServiceConfiguration.class);

    /**
     * 顶级 namespace
     */
    private static final String FX_APP = "FX.app";
    /**
     * 自定义 namespace
     */
    private static final String NAMESPACE_LANG = "namespace.lang";

    private static final String ON = "on";

    /**
     * 是否启用配置中心配置。on 启用。off不启用。
     */
    private static final String MASK_TO_STRING_ENABLE_KEY = "mask.to.string.enable.key";

    private static Config nsConfig = null;

    private static volatile boolean bizMonitor = false;

    /**
     * <p>
     * 默认打马赛克的email字段， 值不可被修改
     * </p>
     */
    private static Set<String> emailFields;

    /**
     * <p>
     * 默认打马赛克的字段， 值不可被修改
     * </p>
     */
    private static Set<String> maskFields;

    /*
     *
     * @see com.aicai.fw.mask.spi.IWatchServiceConfiguration#getEmailFields()
     */
    public Set<String> getEmailFields() {
        return emailFields;
    }

    /*
     *
     * @see com.aicai.fw.mask.spi.IWatchServiceConfiguration#getMaskFields()
     */
    public Set<String> getMaskFields() {
        return maskFields;
    }

    public boolean isBizMonitor() {
        return bizMonitor;
    }

    private static void findStatus() {
        findMaskToStringEnable(nsConfig.getProperty(MASK_TO_STRING_ENABLE_KEY, ""));
        findMaskEmailFields(nsConfig.getProperty(MASK_EMAIL_FIELDS_KEY, ""));
        findMaskFields(nsConfig.getProperty(MASK_FIELDS_KEY, ""));
    }

    private static void holdStatus() {
        nsConfig.addChangeListener(new ConfigChangeListener() {
            @Override
            public void onChange(ConfigChangeEvent changeEvent) {
                for (String key : changeEvent.changedKeys()) {
                    ConfigChange change = changeEvent.getChange(key);
                    logger.info(
                            "ApolloWatchServiceConfiguration Found change - key: {}, oldValue: {}, newValue: {}, changeType: {}",
                            change.getPropertyName(), change.getOldValue(), change.getNewValue(),
                            change.getChangeType());
                    if (StringUtils.equals(change.getPropertyName(), MASK_TO_STRING_ENABLE_KEY)) {
                        findMaskToStringEnable(change.getNewValue());
                    }
                    if (StringUtils.equals(change.getPropertyName(), MASK_EMAIL_FIELDS_KEY)) {
                        findMaskEmailFields(change.getNewValue());
                    }
                    if (StringUtils.equals(change.getPropertyName(), MASK_FIELDS_KEY)) {
                        findMaskFields(change.getNewValue());
                    }
                }
            }
        });
    }

    private static void findMaskToStringEnable(String newValue) {
        String maskToStringEnable = newValue;
        logger.debug("maskToStringEnable: {}", maskToStringEnable);
        if ((StringUtils.isNotEmpty(maskToStringEnable)) && (StringUtils.equals(maskToStringEnable, ON))) {
            bizMonitor = true;
        } else {
            bizMonitor = false;
        }
    }

    private static void findMaskEmailFields(String newValue) {
        logger.debug("maskEmailFields: {}", newValue);

        Set<String> fields = IWatchServiceSupport.findFields(newValue);
        if(fields == null){
            fields = defaultEmailFields;
        }

        emailFields = fields;
    }

    private static void findMaskFields(String newValue) {
        logger.debug("maskFields: {}", newValue);

        Set<String> fields = IWatchServiceSupport.findFields(newValue);
        if(fields == null){
            fields = defaultMaskFields;
        }

        maskFields = fields;
    }

    @Override
    public boolean load() {
        try {
            long startTime = System.currentTimeMillis();
            Config config = ConfigService.getConfig(FX_APP);
            String ns = config.getProperty(NAMESPACE_LANG, "");
            if(StringUtils.isNotEmpty(ns)){
                config = ConfigService.getConfig(ns);
            }
            nsConfig = config;
            findStatus();
            long endTime = System.currentTimeMillis();
            logger.info("ApolloWatchServiceConfiguration 配置初始化完成。 耗时:{} ms。", (endTime - startTime));
        } catch (Exception e) {
            logger.error("ApolloWatchServiceConfiguration findStatus 出现异常！", e);
            maskFields = defaultMaskFields;
            emailFields = defaultEmailFields;
        }
        try {
            holdStatus();
            return true;
        } catch (Exception e) {
            logger.error("ApolloWatchServiceConfiguration holdStatus 出现异常！", e);
        }
        return false;
    }

    @Override
    public WatchConfigureType watchConfigureType() {
        return WatchConfigureType.APOLLO;
    }
}
