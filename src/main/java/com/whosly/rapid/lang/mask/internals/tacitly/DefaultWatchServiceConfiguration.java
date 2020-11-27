/**
 * 
 */
package com.whosly.rapid.lang.mask.internals.tacitly;

import com.whosly.rapid.lang.mask.internals.WatchConfigureType;
import com.whosly.rapid.lang.mask.spi.IWatchServiceConfiguration;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


/**
 * 默认掩码规则
 *
 * @author yueny09 <yueny09@163.com>
 *
 * @DATE 2018年9月5日 上午11:15:23
 */
public class DefaultWatchServiceConfiguration implements IWatchServiceConfiguration {

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
	private static Set<String> defaultEmailFields = Collections.unmodifiableSet(new HashSet<>(Arrays.asList("email", "EMAIL", "mail", "emailAddr", "emailAddress",
			// 以下是针对_分隔的情况
			"email_addr", "email_address")));
	/**
	 * <p>
	 * 默认打马赛克的字段， 值不可被修改
	 * </p>
	 * 身份证号码</br>
	 */
	private static Set<String> defaultMaskFields = Collections.unmodifiableSet(
			new HashSet<>(Arrays.asList(
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
			"password")));
	
	/**
	 * <p>
	 * 默认打马赛克的email字段， 值不可被修改
	 * </p>
	 */
	private static Set<String> emailFields;
	/**
	 * <p>
	 * 默认打马赛克的字段， 值不可被修改
	 * </p>
	 */
	private static Set<String> maskFields;

	/* (non-Javadoc)
	 * @see com.whosly.rapid.lang.mask.spi.IWatchServiceConfiguration#getEmailFields()
	 */
	@Override
	public Set<String> getEmailFields(){
		return emailFields;
	}

	/* (non-Javadoc)
	 * @see com.whosly.rapid.lang.mask.spi.IWatchServiceConfiguration#getMaskFields()
	 */
	@Override
	public Set<String> getMaskFields(){
		return maskFields;
	}

	/* (non-Javadoc)
	 * @see com.whosly.rapid.lang.mask.spi.IWatchServiceConfiguration#isBizMonitor()
	 */
	@Override
	public boolean isBizMonitor(){
		return true;
	}

	/* (non-Javadoc)
	 * @see com.whosly.rapid.lang.mask.spi.IWatchServiceConfiguration#load()
	 */
	@Override
	public boolean load(){
		maskFields = defaultMaskFields;
		emailFields = defaultEmailFields;

		return true;
	}

	@Override
	public WatchConfigureType watchConfigureType() {
		return WatchConfigureType.DEFAULT;
	}
}
