/**
 *
 */
package com.yueny.rapid.lang.mask;

import org.junit.Assert;
import org.junit.Test;

import com.yueny.rapid.lang.mask.MaskOcclusionUtil;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年7月6日 下午1:31:36
 *
 */
public class MaskOcclusionUtilTest {
	@Test
	public void testOcclusionCardNo() {
		final String encryptCardNo = MaskOcclusionUtil.occlusionCardNo("6225882133271010");
		Assert.assertEquals("****1010", encryptCardNo);
	}

	@Test
	public void testOcclusionCertNo() {
		final String encrypt = MaskOcclusionUtil.occlusionCertNo("662973199010088176");
		Assert.assertEquals("662******8176", encrypt);
	}

	@Test
	public void testOcclusionEmail() {
		final String encrypt = MaskOcclusionUtil.occlusionEmail("ab@126.com");
		Assert.assertEquals("a***@126.com", encrypt);

		final String encrypt1 = MaskOcclusionUtil.occlusionEmail("abc@126.com");
		Assert.assertEquals("a***@126.com", encrypt1);

		final String encrypt2 = MaskOcclusionUtil.occlusionEmail("deep_blue_yang@126.com");
		Assert.assertEquals("de***g@126.com", encrypt2);
	}

	@Test
	public void testOcclusionMobile() {
		final String encrypt = MaskOcclusionUtil.occlusionMobile("18000000000");
		Assert.assertEquals("180****0000", encrypt);

		final String encrypt1 = MaskOcclusionUtil.occlusionMobile("23124325");
		Assert.assertEquals("", encrypt1);
	}
}
