package com.whosly.rapid.lang.mask;

import com.whosly.rapid.lang.mask.annotation.Maskble;
import com.whosly.rapid.lang.mask.instance.AbstractMaskBo;
import lombok.Getter;
import lombok.Setter;
import org.junit.Test;

import com.whosly.rapid.lang.mask.annotation.Mask;
import com.whosly.rapid.lang.mask.decorator.MaskfulDecorator;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author 袁洋 2015年8月17日 下午2:32:16
 *
 */
public class MaskBoTest {
	@Getter
	@Setter
	private class MaskDemoBo extends AbstractMaskBo {
		private final String email = "deep@163.com";
		private final String expectData = "expectDataexpectDataexpectDataexpectData";
		@Mask(left = 10, right = 3)
		private final String langChar = "实现思想很简单，Publisher调用redis的publish方法往特定的channel发送消息，Subscriber在初始化的时候要subscribe到该channel，一旦有消息就会立即接收。";
		private final boolean unboxBoolean = true;
		@Mask
		private final String unboxChar = "unboxChar";

		//	@Mask
		@Maskble
		private String orderNo;

		//	@Mask(ignore = true)
		//	@Maskble(ignore = true)
		private String mobile;
	}

	@Test
	public void test() {
		MaskDemoBo md = new MaskDemoBo();
		md.setMobile("18698565458");
		md.setOrderNo(UUID.randomUUID().toString());
		System.out.println(md);
		System.out.println(new MaskfulDecorator(md));

		// 当K, V 均为mojo时 或 当K, V 有一个为mojo时
		MaskDemoBo md1 = new MaskDemoBo();
		md1.setMobile("18698561234");
		md1.setOrderNo(UUID.randomUUID().toString());

		Map<String, MaskDemoBo> maps = new HashMap<>();
		maps.put("18698561234", md1);
		System.out.println(maps);
		System.out.println(new MaskfulDecorator(maps));

		// 当K, V中 Key 为敏感字段值时
		md1.setMobile("1234+564894948");
		Map<String, String> maps111 = new HashMap<>();
		maps111.put("mobile", md1.getMobile());
		maps111.put("email", "dhnqsihu@163.com");
		System.out.println(new MaskfulDecorator(maps111));

		System.out.println(new MaskfulDecorator("张三在吃饭"));
	}
}
