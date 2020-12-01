/**
 * 
 */
package com.whosly.rapid.lang.mask.internals.tacitly;

import com.whosly.rapid.lang.mask.internals.WatchConfigureType;
import com.whosly.rapid.lang.mask.spi.IWatchServiceConfiguration;

import java.util.Set;


/**
 * 默认掩码规则
 *
 * @author yueny09 <yueny09@163.com>
 *
 * @DATE 2018年9月5日 上午11:15:23
 */
public class DefaultWatchServiceConfiguration implements IWatchServiceConfiguration, IWatchServiceSupport {
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

	/* (non-Javadoc)
	 * @see com.whosly.rapid.lang.mask.spi.IWatchServiceConfiguration#getEmailFields()
	 */
	@Override
	public Set<String> getEmailFields(){
		return emailFields;
	}

	/* (non-Javadoc)
	 * @see com.whosly.rapid.lang.mask.spi.IWatchServiceConfiguration#getMaskFields()
	 */
	@Override
	public Set<String> getMaskFields(){
		return maskFields;
	}

	/* (non-Javadoc)
	 * @see com.whosly.rapid.lang.mask.spi.IWatchServiceConfiguration#isBizMonitor()
	 */
	@Override
	public boolean isBizMonitor(){
		return true;
	}

	/* (non-Javadoc)
	 * @see com.whosly.rapid.lang.mask.spi.IWatchServiceConfiguration#load()
	 */
	@Override
	public boolean load(){
		maskFields = defaultMaskFields;
		emailFields = defaultEmailFields;

		return true;
	}

	@Override
	public WatchConfigureType watchConfigureType() {
		return WatchConfigureType.DEFAULT;
	}
}
