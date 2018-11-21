/**
 *
 */
package com.yueny.rapid.lang.mask.decorator;

import com.yueny.rapid.lang.mask.convert.MapConvert;
import com.yueny.rapid.lang.mask.convert.StringConvert;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.yueny.rapid.lang.mask.builder.MaskToStringBuilder;
import com.yueny.superclub.api.pojo.IBo;

import java.util.Map;

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
	private Object bo;

	/**
	 * 脱敏表达式。
	 * <pre>
	 * # 原样显示1位
	 * * 脱敏显示一位
	 * ? 原样显示N位
	 * 譬如##**? 头两位原样显示，其后两位脱敏，再其后原样显示
	 * </pre>
	 */
	private String pattern;

	/**
	 * @return the bo
	 */
	public Object getBo() {
		return bo;
	}

	/**
	 * 对象脱敏，可以为自定义mojo, List, Set, Map, String等
	 *
	 * @param bo
	 */
	public MaskfulDecorator(Object bo) {
		this.bo = bo;
	}

	/**
	 * 普通字符串脱敏
	 *
	 * @param value 普通字符串
	 * @param pattern 脱敏表达式。
	 * <pre>
	 * # 原样显示1位
	 * * 脱敏显示一位
	 * ? 原样显示N位
	 * 譬如##**? 头两位原样显示，其后两位脱敏，再其后原样显示
	 * </pre>
	 */
	public MaskfulDecorator(String value, String pattern) {
		this.bo = value;
		this.pattern = pattern;
	}

	/*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
	@Override
	public String toString() {
		// 空对象的输出
		if (null == bo) {
			return "";
		}

		try {
			// 字符串的输出
			if (bo instanceof String) {
				return StringConvert.mask(getBo().toString(), pattern);
			}

			// 其他基本类型， 如 Integer 等，不做掩码处理
			//.

			// Map类型
			if (bo instanceof Map) {
				return MapConvert.mask((Map) getBo());
			}

			// 非字符串的 mask 输出: 自定义bean, List等复杂对象
			return MaskToStringBuilder.reflectionToString(getBo(), ToStringStyle.SHORT_PREFIX_STYLE);
		} catch (Exception e) {
			// 此处异常忽略，仅打印异常信息
			System.out.println("字段脱敏失败");
			e.printStackTrace();
		}

		return "-unknow-";
	}

}
