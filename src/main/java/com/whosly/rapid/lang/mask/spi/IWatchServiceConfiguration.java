package com.whosly.rapid.lang.mask.spi;

import java.util.Set;

/**
 * 配置中心实现
 */
public interface IWatchServiceConfiguration {

    /**
     * @return email 掩码字段
     */
    public Set<String> getEmailFields();

    /**
     * @return 掩码字段
     */
    public Set<String> getMaskFields();

    /**
     * @return 是否开启监控。 各自根据需要去判断和定义
     */
    public boolean isBizMonitor();

    /**
     * @return 配置启动，初始化去读取配置中心
     */
    public boolean load();

}
