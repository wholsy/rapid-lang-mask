package com.whosly.rapid.lang.mask.spi;

import com.whosly.rapid.lang.mask.internals.WatchConfigureType;

import java.util.Set;

/**
 * 配置中心实现
 */
public interface IWatchServiceConfiguration {

    /**
     * @return email 掩码字段
     */
    Set<String> getEmailFields();

    /**
     * @return 掩码字段
     */
    Set<String> getMaskFields();

    /**
     * @return 是否开启监控。 各自根据需要去判断和定义
     */
    boolean isBizMonitor();

    /**
     * @return 配置启动，初始化去读取配置中心
     */
    boolean load();

    /**
     * 配置类型
     *
     * @return 配置类型
     */
    WatchConfigureType watchConfigureType();

}
