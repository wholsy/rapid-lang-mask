package com.whosly.rapid.lang.mask.builder;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;

import com.whosly.rapid.lang.mask.annotation.Mask;

/**
 * @author 袁洋 2015年8月17日 下午2:32:16
 *
 */
public class MaskToStringBuilderTest {
	private class Tester {
		private final String email = "deep@163.com";
		private final String expectData = "expectDataexpectDataexpectDataexpectData";
		@Mask(left = 10, right = 3)
		private final String langChar = "实现思想很简单，Publisher调用redis的publish方法往特定的channel发送消息，Subscriber在初始化的时候要subscribe到该channel，一旦有消息就会立即接收。";
		private final boolean unboxBoolean = true;
		@Mask
		private final String unboxChar = "unboxChar";

		/**
		 * @return the email
		 */
		public String getEmail() {
			return email;
		}

		/**
		 * @return the expectData
		 */
		public String getExpectData() {
			return expectData;
		}

		/**
		 * @return the langChar
		 */
		public String getLangChar() {
			return langChar;
		}

		/**
		 * @return the unboxChar
		 */
		public String getUnboxChar() {
			return unboxChar;
		}

		/**
		 * @return the unboxBoolean
		 */
		public boolean isUnboxBoolean() {
			return unboxBoolean;
		}

		@Override
		public String toString() {
			return MaskToStringBuilder.toString(this);
		}
	}

	@Test
	public void testMap() throws Exception {
		final HashMap<String, String> map = new HashMap<String, String>();
		map.put("tel", "1234567890");
		map.put("name", "Test");
		String toStr = MaskToStringBuilder.toString(map);
		System.out.println(toStr);
		assertFalse(toStr.indexOf("1234567890") != -1);
		assertTrue(toStr.indexOf("Test") != -1);

		map.put(null, "123456");
		toStr = MaskToStringBuilder.toString(map);
		System.out.println(toStr);
		assertTrue(toStr.indexOf("null") != -1);
	}

	/**
	 * Test method for {@link MaskToStringBuilder#toString(java.lang.Class)} .
	 */
	@Test
	public void testToString() {
		final Tester tester = new Tester();

		System.out.println(tester);

		String toStr = MaskToStringBuilder.toString(tester, new String[] { "email", "expectData", "langChar" });
		System.out.println(tester);

		toStr = MaskToStringBuilder.toString(tester, ToStringStyle.DEFAULT_STYLE);
		System.out.println(toStr);
	}
}
