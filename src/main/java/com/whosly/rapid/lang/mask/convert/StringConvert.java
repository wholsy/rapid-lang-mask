package com.whosly.rapid.lang.mask.convert;

import com.whosly.rapid.lang.mask.util.MaskInfoUtil;
import org.apache.commons.lang3.StringUtils;

/**
 *  String 类转换
 */
public class StringConvert {
    // ===================================  字符串掩码输出 by wanglilin @20181016 START
    /**
     * 复杂对象掩码输出
     *
     * @param bo
     *            欲掩码的值
     * @param pattern
     *            掩码pattern表达式
     * @return 掩码后的值
     */
    public static String mask(String bo, String pattern) {
        if(StringUtils.isNotEmpty(pattern)){
            return MaskInfoUtil.mask(bo.toString(), pattern);
        }

        return MaskInfoUtil.mask(bo.toString());
    }
    // ===================================  字符串掩码输出 by wanglilin @20181016 START

}