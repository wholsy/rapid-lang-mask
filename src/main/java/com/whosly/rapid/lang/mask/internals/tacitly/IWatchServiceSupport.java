package com.whosly.rapid.lang.mask.internals.tacitly;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 支持类
 */
public interface IWatchServiceSupport {
    /**
     * 默认打马赛克的字段
     */
    String MASK_FIELDS_KEY = "mask.fields.key";

    /**
     *  默认打马赛克的email字段
     */
    String MASK_EMAIL_FIELDS_KEY = "mask.email.fields.key";

    /**
     * <p>
     * 默认打马赛克的email字段， 值不可被修改
     * </p>
     * email</br>
     * EMAIL</br>
     * mail</br>
     * emailAddr</br>
     * emailAddress</br>
     * email_addr</br>
     * email_address</br>
     */
    Set<String> defaultEmailFields = Collections.unmodifiableSet(new HashSet<>(Arrays.asList("email", "EMAIL", "mail", "emailAddr", "emailAddress",
            // 以下是针对_分隔的情况
            "email_addr", "email_address")));

    /**
     * <p>
     * 默认打马赛克的字段， 值不可被修改
     * </p>
     * 身份证号码</br>
     */
    Set<String> defaultMaskFields = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            "cardNo", "bankCard", "bankCardNo", "certificateNo", "certNo",
            //"mobile", "mobileNo", "tel", "telNo", "telphone", "telphoneNo", "phone", "phoneNo",
            "address", "addr", "postAddr", "postAddress",
            "mailAddr", "mailAddress",
            // 以下是针对遗留数据库增加f_前缀的情况
            "fCardNo", "fBankCard", "fBankCardNo", "fAccountNo", "fCertificateNo", "fCertNo", "fMobile", "fMobileNo",
            "fTel", "fTelNo", "fAddress", "fAddr", "fPostAddr", "fPostAddress", "fMailAddr", "fMailAddress",
            "fTelphone", "fTelphoneNo", "fPhone", "fPhoneNo",
            // 以下是针对_分隔的情况
            "card_no", "bank_card", "bank_card_no", "certificate_no", "cert_no",
            // "mobile_no", "tel_no", "telphone_no", "phone_no", "account_no"
            "post_addr", "post_address", "mail_addr", "mail_address",
            // 密码类
            "password"
    )));

    /**
     * 配置数据解析为字段列表
     *
     * support by jdk 8
     *
     * @param newValue
     * @return
     */
    static Set<String> findFields(String newValue) {
        Set<String> fields;

        String maskFieldsVal = newValue;
        if (StringUtils.isNotEmpty(maskFieldsVal)) {
            try {
                Set<String> list = new HashSet<>();
                String[] maskFieldsVals = maskFieldsVal.split(",");
                if (maskFieldsVals != null) {
                    for (String e : maskFieldsVals) {
                        if (StringUtils.isNotEmpty(e)) {
                            list.add(e.trim());
                        }
                    }
                }
                fields = Collections.unmodifiableSet(list);
            } catch (Exception e) {
                fields = null;
            }
        } else {
            fields = null;
        }

        return fields;
    }

}
