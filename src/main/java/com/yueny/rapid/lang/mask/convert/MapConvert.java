package com.yueny.rapid.lang.mask.convert;

import com.yueny.rapid.lang.mask.builder.MaskToStringBuilder;
import com.yueny.rapid.lang.mask.internals.WatchServiceConfigLoader;
import com.yueny.rapid.lang.mask.util.MaskInfoUtil;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *  Map 类转换
 */
public class MapConvert {
    private static Set<String> getEmailFields(){
        return WatchServiceConfigLoader.get().getEmailFields();
    }

    private static Set<String> getMaskFields(){
        return WatchServiceConfigLoader.get().getMaskFields();
    }

    /**
     * 复杂对象掩码输出
     *
     * @param source
     *            欲掩码的值
     * @return 掩码后的值
     */
    public static Map<Object, Object> mask(final Map<Object, Object> source) {
        Map<Object, Object> target = new HashMap<>();

        for (final Map.Entry<Object, Object> entry : source.entrySet()) {
            // 简单类型 String
            if (entry.getKey() instanceof String && entry.getValue() instanceof String) {
                if (getMaskFields().contains(entry.getKey())
                        || getEmailFields().contains(entry.getKey())) {
                    // 在配置项中，value 脱敏
                    target.put(entry.getKey(), MaskInfoUtil.mask((String) entry.getValue()));
                }else{
                    // 不脱敏
                    target.put(entry.getKey(), entry.getValue());
                }
            }else{
                // 非字符串的 mask 输出: 自定义bean, List等复杂对象
                // TODO 暂未实现
                target.put(entry.getKey(), entry.getValue());
            }
        }

        return target;
    }

    // ===================================  复杂对象掩码输出 by yueny @20181016 START
    /**
     * 复杂对象掩码输出
     *
     * @param source
     *            欲掩码的值
     * @return 掩码后的值
     */
    public static String toString(final Map<Object, Object> source) {
        StringBuffer buffer = new StringBuffer(512);

        buffer.append("{");
        for (final Map.Entry<Object, Object> entry : source.entrySet()) {
            // 简单类型 String
            if (entry.getKey() instanceof String && entry.getValue() instanceof String) {
                if (getMaskFields().contains(entry.getKey())
                        || getEmailFields().contains(entry.getKey())) {
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
