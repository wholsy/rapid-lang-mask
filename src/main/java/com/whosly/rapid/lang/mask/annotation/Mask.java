package com.whosly.rapid.lang.mask.annotation;

import java.lang.annotation.*;

/***
 * 标注需要打马赛克的字符串字段<br>
 *
 * 卡号<br>
 * #Mask<br>
 * private String cardNo;<br>
 *
 * 则toString时该字段会被InfoMaskUtil.mask
 *
 * @author 袁洋 2015年8月17日 下午2:09:55
 * @inc baidu
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Deprecated
public @interface Mask {
	/**
	 * 是否忽略该字段的掩码处理
	 */
	boolean ignore() default false;

	/**
	 * 左侧预留字符数目
	 *
	 * @return 左侧预留字符数目
	 */
	int left() default -1;

	/**
	 * 右侧预留字符数目数
	 *
	 * @return 右侧预留字符数目
	 */
	int right() default -1;

}
