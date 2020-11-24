/**
 *
 */
package com.whosly.rapid.lang.mask.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年7月6日 上午10:11:23
 *
 */
public class MaskInfoUtilTest {
	@Test
	public void testMask() {
		// 1. length >=18，保留前6后4 <br>
		final String maskName20 = MaskInfoUtil.mask("我爱我家我爱我家我爱我家我爱我家我爱我家");
		Assert.assertEquals("我爱我家我爱**********我爱我家", maskName20);

		// 1. 14 <= length < 18，保留前4后4 <br>
		final String maskName16 = MaskInfoUtil.mask("我爱我家我爱我家我爱我家我爱我家");
		Assert.assertEquals("我爱我家********我爱我家", maskName16);

		// 2. 10 <= length < 14，保留前3后3 <br>
		final String maskName12 = MaskInfoUtil.mask("我爱我家我爱我家我爱我家");
		Assert.assertEquals("我爱我******爱我家", maskName12);

		// 3. 7 <= length < 10，保留前2后2 <br>
		final String maskName8 = MaskInfoUtil.mask("我爱我家我爱我家");
		Assert.assertEquals("我爱****我家", maskName8);

		// 4. 4 < length < 7，保留前2后1 <br>
		final String maskName5 = MaskInfoUtil.mask("我爱我家啊");
		Assert.assertEquals("我爱**啊", maskName5);

		// 5. 3 <= length <= 4，保留前1后1 <br>
		final String maskName4 = MaskInfoUtil.mask("我爱我家");
		Assert.assertEquals("我**家", maskName4);
		final String maskName3 = MaskInfoUtil.mask("我爱你");
		Assert.assertEquals("我*你", maskName3);

		// 6. length == 2，保留前1 <br>
		final String maskName2 = MaskInfoUtil.mask("爱你");
		Assert.assertEquals("爱*", maskName2);

		// 7. 5. length == 1,不加掩码,直接输出 <br>
		final String maskName1 = MaskInfoUtil.mask("爱");
		Assert.assertEquals("爱", maskName1);
	}

	@Test
	public void testMaskEmail() {
		final String maskEmail1 = MaskInfoUtil.maskEmail("deep_blue_yang");
		Assert.assertEquals("deep_blue_yang", maskEmail1);

		final String maskEmail2 = MaskInfoUtil.maskEmail("deep");
		Assert.assertEquals("deep", maskEmail2);

		final String maskEmail3 = MaskInfoUtil.maskEmail("deep_blue_yang@163.com");
		Assert.assertEquals("deep******yang@16***om", maskEmail3);

		final String maskEmail4 = MaskInfoUtil.maskEmail("deep@163.com");
		Assert.assertEquals("d**p@16***om", maskEmail4);

		final String maskEmail5 = MaskInfoUtil.maskEmail("deep$163.com");
		Assert.assertEquals("deep$163.com", maskEmail5);
	}
}
