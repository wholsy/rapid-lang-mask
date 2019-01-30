package com.yueny.rapid.lang.mask.pojo.instance;

import com.yueny.rapid.lang.mask.internals.WatchServiceConfigLoader;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.yueny.rapid.lang.mask.builder.MaskToStringBuilder;
import com.yueny.superclub.api.pojo.IBo;
import com.yueny.superclub.api.pojo.instance.AbstractBo;

/**
 * 抽象mask bo, 或者使用 MaskfulDecorator(IBo)实现mask<br>
 * 未实现 Serializable接口<br>
 * 也可以使用实现 Serializable的抽象bo
 * {@link com.yueny.superclub.api.pojo.instance.AbstractBo}
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2015年12月17日 下午1:37:09
 * @inc baidu
 */
public abstract class AbstractMaskBo extends AbstractBo implements IBo {
	/**
	 */
	private static final long serialVersionUID = 9010871182373542714L;

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		try {
			if(WatchServiceConfigLoader.get().isBizMonitor()){
				return MaskToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
			}
		}catch(Exception e){
			// 此处一场忽略, 直接进行不脱敏
			//.
		}

		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
