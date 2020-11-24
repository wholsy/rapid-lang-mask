package com.whosly.rapid.lang.mask.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 对敏感信息进行遮挡, 扩展了 MaskOcclusionUtil 的业务字段的脱敏范围
 * @Author yueny09 <deep_blue_yang@126.com>
 * @Date 2019-08-16 11:29
 */
public class MaskDesensitizedOcclusionUtil {
    /**
     * 【中文姓名】第二个字星号，比如：李*测
     *
     * @param fullName
     * @return 中文姓名掩码
     */
    public static String occlusionChineseName(String fullName) {
        if (StringUtils.isEmpty(fullName)) {
            return StringUtils.EMPTY;
        }
        if (fullName.length() <= 1) {
            return fullName;
        }
        final StringBuilder encryptName = new StringBuilder(fullName.length());
        encryptName.append(StringUtils.left(fullName, 1))
                .append(MaskOcclusionUtil.SINGLE_ENCRYPT_TOKEN);
        if (fullName.length() > 2) {
            encryptName.append(StringUtils.right(fullName, 1));
        }
        return encryptName.toString();
    }

    /**
     * 【地址】只显示到市，不显示详细地址，比如：北京市****
     *
     * @param address
     */
    public static String occlusionAddress(String address) {
        return occlusionAddress(address, true);
    }

    /**
     * 【地址】只显示到市，不显示详细地址，比如：北京市****
     *
     * @param address
     */
    public static String occlusionAddress(String address, boolean check) {
        if (StringUtils.isEmpty(address)) {
            return StringUtils.EMPTY;
        }
        if(!check){
            return MaskOcclusionUtil.CARDNO_ENCRYPT_TOKEN;
        }

        int sensitiveSize = address.indexOf("市");
        if (sensitiveSize < 0) {
            return MaskOcclusionUtil.CARDNO_ENCRYPT_TOKEN;
        }
        return new StringBuilder(StringUtils.left(address, sensitiveSize + 1))
                .append(MaskOcclusionUtil.SINGLE_ENCRYPT_TOKEN).toString();
    }

    /**
     * @category 对于用户的生日进行遮挡 input:19841202 output:****1202<br>
     *           不验证生日合法性
     *
     * @param birthday
     *            生日
     * @return occlusionBirthday
     */
    public static String occlusionBirthday(final String birthday) {
        if (StringUtils.isEmpty(birthday)) {
            return StringUtils.EMPTY;
        }
        if ( birthday.length() != 8) {
            return birthday;
        }
        return MaskInfoUtil.mask(birthday, 0, 4);
    }

    /**
     * @category 对于验证码进行遮挡 input: 876723 output: ******
     * @param validCode
     * @return
     */
    public static String occlusionValidCode(final String validCode){
        if (StringUtils.isBlank(validCode)) {
            return StringUtils.EMPTY;
        }
        if(!validCode.matches("^\\d{"+validCode.length()+"}$")){
            throw new IllegalArgumentException("不是合法的验证码!");
        }
        return RandomStringUtils.random(validCode.length(), MaskOcclusionUtil.SINGLE_ENCRYPT_TOKEN);
    }

    /**
     * Don't let anyone instantiate this class.
     */
    private MaskDesensitizedOcclusionUtil() {
        // .
    }
}
