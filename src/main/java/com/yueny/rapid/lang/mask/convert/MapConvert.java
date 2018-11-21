package com.yueny.rapid.lang.mask.convert;

import com.yueny.rapid.lang.mask.builder.MaskToStringBuilder;
import com.yueny.rapid.lang.mask.internals.WatchServiceConfigLoader;
import com.yueny.rapid.lang.mask.util.MaskInfoUtil;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Map;

/**
 *  Map 类转换
 */
public class MapConvert {
    // ===================================  复杂对象掩码输出 by yueny @20181016 START
    /**
     * 复杂对象掩码输出
     *
     * @param source
     *            欲掩码的值
     * @return 掩码后的值
     */
    public static String mask(final Map<Object, Object> source) {
        StringBuffer buffer = new StringBuffer(512);

        buffer.append("{");
        for (final Map.Entry<Object, Object> entry : source.entrySet()) {
            // 简单类型 String
            if (entry.getKey() instanceof String && entry.getValue() instanceof String) {
                if (WatchServiceConfigLoader.get().getMaskFields().contains(entry.getKey())
                        || WatchServiceConfigLoader.get().getEmailFields().contains(entry.getKey())) {
                    // 在配置项中，value 脱敏
                    buffer.append(entry.getKey()).append(MaskInfoUtil.EQUAL)
                            .append(MaskInfoUtil.mask((String) entry.getValue()))
                            // TODO 此处需要控制是否追加
                            .append(MaskInfoUtil.COMMA);
                }else{
                    // 不脱敏
                    buffer.append(entry.getKey()).append(MaskInfoUtil.EQUAL)
                            .append(entry.getValue()).append(MaskInfoUtil.COMMA);
                }
            }else{
                // 其他所有类型，均执行统一掩码脱敏规则
                buffer.append(entry.getKey()).append(MaskInfoUtil.EQUAL)
                        .append(MaskToStringBuilder.reflectionToString(entry.getValue(), ToStringStyle.SHORT_PREFIX_STYLE)).append(MaskInfoUtil.COMMA);
            }
        }
        buffer.append("}");

        return buffer.toString();
    }
    // ===================================  复杂对象掩码输出 by yueny @20181016 END

}
