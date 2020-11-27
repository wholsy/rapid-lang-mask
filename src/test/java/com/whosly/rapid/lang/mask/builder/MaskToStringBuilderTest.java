package com.whosly.rapid.lang.mask.builder;

import java.util.HashMap;

import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;

import com.whosly.rapid.lang.mask.annotation.Mask;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

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
		assertTrue(tester != null);

		String toStr = MaskToStringBuilder.toString(tester, new String[] { "email", "expectData", "langChar" });
		System.out.println(toStr);
		assertEquals(toStr, "com.whosly.rapid.lang.mask.builder.MaskToStringBuilderTest$Tester@6321e813[email=dee******com,expectData=expect***********Data,langChar=实现思想很简单，Pu******************接收。,unboxBoolean=true,unboxChar=un*****ar]");

		toStr = MaskToStringBuilder.toString(tester, ToStringStyle.DEFAULT_STYLE);
		System.out.println(toStr);
		assertEquals(toStr, "com.whosly.rapid.lang.mask.builder.MaskToStringBuilderTest$Tester@6321e813[email=d**p@16***om,expectData=expectDataexpectDataexpectDataexpectData,langChar=实现思想很简单，Pu******************接收。,unboxBoolean=true,unboxChar=un*****ar]");
	}
}
