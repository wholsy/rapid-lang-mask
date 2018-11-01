package com.yueny.rapid.lang.mask;

import org.apache.commons.lang3.StringUtils;

/**
 * 对敏感信息进行遮挡
 *
 * @author <a href="mailto:yueny09@126.com"> 袁洋 2014年8月9日
 * @inc baidu
 * @category tag
 */
public final class MaskOcclusionUtil {
	/** 银行卡号遮挡填充key */
	private static final String CARDNO_ENCRYPT_TOKEN = "****";
	/** 邮箱遮挡填充key */
	private static final String EMAIL_ENCRYPT_TOKEN = "***";
	/** 身份证号最少的位数 */
	private static final int MIN_ID_CERT_DIGITS = 15;
	// /** 手机遮挡填充KEY */
	// private static final String MOBILE_ENCRYPT_TOKEN = "****";
	/** 邮件分隔符 */
	private static final char SEPARATOR = '@';

	/**
	 * @category 对于用户的银行卡号码进行遮挡 input:6225882133271010 output:****1010
	 *
	 * @param cardNo
	 *            银行卡号
	 * @return 8位银行卡号掩码
	 */
	public static String occlusionCardNo(final String cardNo) {
		if (StringUtils.isEmpty(cardNo) || cardNo.length() < 6) {
			return StringUtils.EMPTY;
		}

		// ************1010
		// InfoMaskUtil.mask(cardNo, 0, 4);

		final StringBuilder encryptCardNo = new StringBuilder(8);
		encryptCardNo.append(CARDNO_ENCRYPT_TOKEN).append(StringUtils.right(cardNo, 4));
		return encryptCardNo.toString();
	}

	/**
	 * @category 对身份证进行掩码处理，除前3位和后4位外其余数字×号显示<br>
	 *           input:662973199010088176 output:662******8176<br>
	 *           不验证身份证号码的合法性
	 *
	 * @param certNo
	 *            身份证号码
	 * @return 13位身份证掩码
	 */
	public static String occlusionCertNo(final String certNo) {
		if (StringUtils.isEmpty(certNo) || certNo.length() < MIN_ID_CERT_DIGITS) {
			return StringUtils.EMPTY;
		}

		// 662***********8176
		// return InfoMaskUtil.mask(certNo, 3, 4);

		// 对于用户的身份证号码进行遮挡 input:370881199010088176 output:370******8176
		final StringBuilder encryptCardId = new StringBuilder(13);
		encryptCardId.append(StringUtils.left(certNo, 3)).append("******").append(StringUtils.right(certNo, 4));
		return encryptCardId.toString();
	}

	/**
	 * @category 对于用户邮件信息进行遮挡 input:abc@126.com output:a***@126.com
	 *           input:abcd@126.com output:ab***d@126.com
	 * @param email
	 *            邮箱
	 * @return 掩码邮箱, 位数<=加密前邮箱长度
	 */
	public static String occlusionEmail(final String email) {
		if (StringUtils.isEmpty(email)) {
			return StringUtils.EMPTY;
		}

		// 根据@符拆分
		final String[] splitted = StringUtils.split(email, SEPARATOR);
		if (splitted == null || splitted.length != 2) {
			return StringUtils.EMPTY;
		}
		final String emailPrefix = splitted[0];
		final String post = splitted[1];

		final StringBuilder encryptEmail = new StringBuilder();

		// @符前大于3位
		if (emailPrefix.length() > 3) {
			encryptEmail.append(StringUtils.left(emailPrefix, 2)).append(EMAIL_ENCRYPT_TOKEN)
					.append(StringUtils.right(emailPrefix, 1));
		} else {
			encryptEmail.append(StringUtils.left(emailPrefix, 1)).append(EMAIL_ENCRYPT_TOKEN);
		}
		encryptEmail.append(SEPARATOR).append(post);

		return encryptEmail.toString();
	}

	/**
	 * @category 对于用户的手机号码进行遮挡 input:18000000000 output:180****0000<br>
	 *           不验证手机号码的合法性
	 *
	 * @param mobile
	 *            手机号码
	 * @return occlusionMobile
	 */
	public static String occlusionMobile(final String mobile) {
		if (StringUtils.isEmpty(mobile) || mobile.length() != 11) {
			return StringUtils.EMPTY;
		}

		return MaskInfoUtil.mask(mobile, 3, 4);

		// final StringBuilder encryptMobile = new StringBuilder(11);
		// encryptMobile.append(StringUtils.left(mobile,
		// 3)).append(MOBILE_ENCRYPT_TOKEN)
		// .append(StringUtils.right(mobile, 4));
		// return encryptMobile.toString();
	}

	/**
	 * Don't let anyone instantiate this class.
	 */
	private MaskOcclusionUtil() {
		// .
	}

}
