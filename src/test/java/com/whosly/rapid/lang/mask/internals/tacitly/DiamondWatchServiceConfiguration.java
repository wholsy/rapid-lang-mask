/**
 * 
 */
package com.whosly.rapid.lang.mask.internals.tacitly;

import java.util.*;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

import com.google.common.base.Splitter;
import com.taobao.diamond.annotation.DiamondListener;
import com.taobao.diamond.client.DiamondClients;
import com.taobao.diamond.listener.DiamondDataCallback;
import com.taobao.diamond.manager.DiamondManager;
import com.taobao.diamond.manager.ManagerListener;
import com.whosly.rapid.lang.mask.internals.WatchConfigureType;
import com.whosly.rapid.lang.mask.spi.IWatchServiceConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * diamond 配置中心
 *
 * @author yueny09 <yueny09@163.com>
 *
 * @DATE 2018年9月5日 上午11:15:23
 */
@DiamondListener(dataId = "diamond-mask", groupId = "FX.app")
public class DiamondWatchServiceConfiguration implements IWatchServiceConfiguration, IWatchServiceSupport,
		InitializingBean, DiamondDataCallback {
	private static final Logger logger = LoggerFactory.getLogger(DiamondWatchServiceConfiguration.class);

	/**
	 * <p>
	 * 默认打马赛克的email字段， 值不可被修改
	 * </p>
	 */
	private static Set<String> emailFields = defaultEmailFields;
	/**
	 * <p>
	 * 默认打马赛克的字段， 值不可被修改
	 * </p>
	 */
	private static Set<String> maskFields = defaultMaskFields;

	/* (non-Javadoc)
	 * @see com.aicai.fw.mask.spi.IWatchServiceConfiguration#getEmailFields()
	 */
	public Set<String> getEmailFields(){
		return emailFields;
	}

	/* (non-Javadoc)
	 * @see com.aicai.fw.mask.spi.IWatchServiceConfiguration#getMaskFields()
	 */
	public Set<String> getMaskFields(){
		return maskFields;
	}

	/* (non-Javadoc)
	 * @see com.aicai.fw.mask.spi.IWatchServiceConfiguration#isBizMonitor()
	 */
	@Override
	public boolean isBizMonitor() {
		return true;
	}

	/* (non-Javadoc)
	 * @see com.aicai.fw.mask.spi.IWatchServiceConfiguration#load()
	 */
	@Override
	public boolean load(){
		String groupId = "FX.app";
		final String dataId = "diamond-mask";

		try {
			loadConfig(groupId, dataId);
		} catch (Exception e) {
			logger.error("警告性的加载配置信息异常！groupId=" + groupId + ", dataId = " + dataId, e);

			maskFields = defaultMaskFields;
			emailFields = defaultEmailFields;
		}

		return true;
	}

	private void loadConfig(String groupId, final String dataId) {
		ManagerListener managerListener = new ManagerListener() {
			public void receiveConfigInfo(String configInfo) {
				// 配置变更异步通知
				try {
					loadConfigByDataId(dataId, configInfo);
				} catch (Throwable ignore) {
				}
			}

			public Executor getExecutor() {
				return null;
			}
		};

		DiamondManager diamondManager = DiamondClients.createSafeDiamondManager(groupId, dataId, managerListener);
		// 加载一次数据
		String allConfig = diamondManager.getAvailableConfigureInfomation();
		loadConfigByDataId(dataId, allConfig);
	}

	private void loadConfigByDataId(String dataId, String config) {
		if(logger.isInfoEnabled()){
			StringBuilder sb= new StringBuilder();
			sb.append("配置项变更 ");
			sb.append(dataId);
			sb.append(" 的配置信息:\r\n");
			sb.append("######## START \r\n");
			sb.append(config);
			sb.append("\r\n######## END \r\n");

			logger.info(sb.toString());
		}

		received(config);
	}

	@Override
	public void received(String data) {
		if(StringUtils.isEmpty(data)){
			throw new RuntimeException("配置项值读取为null!");
		}

		List<String> iterable = Splitter.on("\r\n")
				// 去空， 忽略空格
				.trimResults()
				.omitEmptyStrings()
				.splitToList(data);

		// key, value 拆分为 map
		Map<String, String> map = iterable.parallelStream().map(line -> {
			List<String> list = Splitter.on("=").trimResults().splitToList(line);

			return new AbstractMap.SimpleEntry<String, String>(list.get(0), list.get(1));
		}).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

		logger.debug("map:{}.", map);

		if(map.containsKey(MASK_EMAIL_FIELDS_KEY)){
			findMaskEmailFields(map.get(MASK_EMAIL_FIELDS_KEY));
		}
		if(map.containsKey(MASK_FIELDS_KEY)){
			findMaskFields(map.get(MASK_FIELDS_KEY));
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
	public WatchConfigureType watchConfigureType() {
		return WatchConfigureType.DIAMOND;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.debug("finish.");
	}
}
