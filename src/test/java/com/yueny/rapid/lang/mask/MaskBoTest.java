package com.yueny.rapid.lang.mask;

import org.junit.Test;

import com.yueny.rapid.lang.mask.annotation.Mask;
import com.yueny.rapid.lang.mask.builder.MaskToStringBuilder;
import com.yueny.rapid.lang.mask.decorator.MaskfulDecorator;
import com.yueny.superclub.api.pojo.IBo;

/**
 * @author 袁洋 2015年8月17日 下午2:32:16
 *
 */
public class MaskBoTest {
	private class Tester implements IBo {
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
	public void test() {
		final Tester tester = new Tester();
		System.out.println(tester);
		System.out.println(new MaskfulDecorator(tester));
	}
}
