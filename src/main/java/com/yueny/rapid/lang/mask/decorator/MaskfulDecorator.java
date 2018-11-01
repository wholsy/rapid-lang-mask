/**
 *
 */
package com.yueny.rapid.lang.mask.decorator;

import org.apache.commons.lang3.builder.ToStringStyle;

import com.yueny.rapid.lang.mask.builder.MaskToStringBuilder;
import com.yueny.superclub.api.pojo.IBo;

/**
 * IBo 的mask装饰器
 *
 * @author yueny09 <yueny09@163.com>
 *
 * @DATE 2018年6月6日 下午5:02:05
 */
public class MaskfulDecorator implements IBo {
	/**
	 * 持有一个IBo类型的引用，可以包装具体构件或装饰者（只要实现了IBo接口）
	 */
	private final IBo bo;

	public MaskfulDecorator(final IBo bo) {
		this.bo = bo;
	}

	/**
	 * @return the bo
	 */
	public IBo getBo() {
		return bo;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// mask 输出
		return MaskToStringBuilder.reflectionToString(getBo(), ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
