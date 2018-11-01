package com.yueny.rapid.lang.mask;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import com.yueny.superclub.api.constant.Constants;

/**
 * 信息掩码处理工具，主要用于过滤卡号、身份证号等内容
 *
 * @author <a href="mailto:yueny09@126.com"> 袁洋 2015年1月15日 下午5:52:11
 * @inc baidu
 * @category tag
 */
public final class MaskInfoUtil {
	/**
	 * 默认掩码<br>
	 * Constants.STAR
	 */
	public static final char DEFAULT_MASK_CHAR = '*';

	/**
	 * 计算边界
	 *
	 * @param source
	 *            元数据
	 * @return 左边界/右边界
	 */
	private static Entry<Integer, Integer> calculateBoundary(final String source) {
		final int length = StringUtils.length(source);

		if (length >= 18) {
			return new SimpleEntry<Integer, Integer>(6, 4);
		} else if (length >= 14 && length < 18) {
			return new SimpleEntry<Integer, Integer>(4, 4);
		} else if (length >= 10 && length < 14) {
			return new SimpleEntry<Integer, Integer>(3, 3);
		} else if (length >= 7 && length < 10) {
			return new SimpleEntry<Integer, Integer>(2, 2);
		} else if (length > 4 && length < 7) {
			return new SimpleEntry<Integer, Integer>(2, 1);
		} else if (length >= 3 && length <= 4) {
			return new SimpleEntry<Integer, Integer>(1, 1);
		} else if (length == 2) {
			return new SimpleEntry<Integer, Integer>(1, 0);
		}
		return null;
	}

	/**
	 * 用默认掩码过滤，规则如下：
	 * <p/>
	 * 1. length >=18，保留前6后4 <br>
	 * 2. 14 <= length < 18，保留前4后4 <br>
	 * 3. 10 <= length < 14，保留前3后3 <br>
	 * 4. 7 <= length < 10，保留前2后2 <br>
	 * 5. 4 < length < 7，保留前2后1 <br>
	 * 6. 3 <= length <= 4，保留前1后1 <br>
	 * 7. length == 2，保留前1 <br>
	 * 8. 5. length == 1,不加掩码,直接输出 <br>
	 *
	 * @param source
	 *            欲掩码的值
	 * @return 掩码后的值
	 */
	public static String mask(final String source) {
		if (StringUtils.isEmpty(source)) {
			return StringUtils.EMPTY;
		}

		final String result = source;

		final Entry<Integer, Integer> boundary = calculateBoundary(source);
		if (boundary == null) {
			return result;
		}

		return mask(result, boundary.getKey(), boundary.getValue());
	}

	/**
	 * 过滤字符串中间位置的内容<br>
	 * 过滤字符为 *
	 *
	 * @param source
	 *            原始字符串
	 * @param leftCount
	 *            左边保留的位数
	 * @param rightCount
	 *            右边保留的位数
	 *
	 * @return 过滤后的字符串
	 */
	public static String mask(final String source, final int leftCount, final int rightCount) {
		return mask(source, leftCount, rightCount, DEFAULT_MASK_CHAR);
	}

	/**
	 * 过滤字符串中间位置的内容
	 *
	 * @param source
	 *            原始字符串
	 * @param leftCount
	 *            左边保留的位数
	 * @param rightCount
	 *            右边保留的位数
	 * @param maskChar
	 *            过滤字符
	 * @return 过滤后的字符串
	 */
	public static String mask(final String source, final int leftCount, final int rightCount, final char maskChar) {
		if (StringUtils.isEmpty(source)) {
			return StringUtils.EMPTY;
		}

		final int length = StringUtils.length(source);
		if (leftCount > length) {
			return source;
		}

		final String left = StringUtils.left(source, leftCount);
		final String right = StringUtils.right(source, rightCount);

		int maskCount = StringUtils.length(source) - leftCount - rightCount;
		if (maskCount < 0) {
			maskCount = 0;
		}
		final String maskString = StringUtils.repeat(maskChar, maskCountRole(maskCount));

		return StringUtils.join(left, maskString, right);
	}

	private static int maskCountRole(final int maskCount) {
		int showMaskCount = maskCount;

		if (maskCount > 50) {
			showMaskCount = (maskCount / 5) + 1;
		} else if (maskCount > 20) {
			showMaskCount = (maskCount / 3) + 1;
		} else if (maskCount > 10) {
			showMaskCount = (maskCount / 2) + 1;
		}

		return showMaskCount;
	}

	/**
	 * 过滤邮箱，如果没有找到@或者截不成前后两端，则直接返回原文 前后内容都用mask()方法过滤
	 *
	 * @param source
	 *            欲掩码的值
	 * @return 掩码后的值
	 */
	public static String maskEmail(final String source) {
		if (StringUtils.isEmpty(source)) {
			return StringUtils.EMPTY;
		}

		final String[] splitted = StringUtils.split(source, Constants.AT);
		if (splitted == null || splitted.length != 2) {
			return source;
		}

		final String pre = mask(splitted[0]);
		final String post = mask(splitted[1]);
		return StringUtils.join(pre, Constants.AT, post);
	}

	/**
	 * 过滤字符串中间位置的内容<br>
	 * 过滤字符为 *
	 *
	 * @param source
	 *            原始字符串
	 * @param leftCount
	 *            左边保留的位数,-1则从新计算
	 * @param rightCount
	 *            右边保留的位数,-1则从新计算
	 *
	 * @return 过滤后的字符串
	 */
	public static String maskSpecial(final String source, int leftCount, int rightCount) {
		if (StringUtils.isEmpty(source)) {
			return StringUtils.EMPTY;
		}

		if (leftCount != -1 && rightCount != -1) {
			return mask(source, leftCount, rightCount);
		}

		final String result = source;
		final Entry<Integer, Integer> boundary = calculateBoundary(source);
		if (boundary == null) {
			return result;
		}

		if (leftCount == -1) {
			leftCount = boundary.getKey();
		}
		if (rightCount == -1) {
			rightCount = boundary.getValue();
		}
		return mask(result, leftCount, rightCount);
	}

}
