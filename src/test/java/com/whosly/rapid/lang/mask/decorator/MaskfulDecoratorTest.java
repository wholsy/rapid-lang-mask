package com.whosly.rapid.lang.mask.decorator;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author yueny09 <deep_blue_yang@126.com>
 * @Date 2019-08-16 12:12
 */
public class MaskfulDecoratorTest {
    @Test
    public void test() {
        Map<String, Object> map = new HashMap<>();
        map.put("password", "dwdwd");
        map.put("a", 66666);

        final MaskfulDecorator decorator = new MaskfulDecorator(map);
        Map<String, Object> map1 = (Map<String, Object>) decorator.mask();
        System.out.println(map1);
//        Assert.assertEquals("662****8176", encrypt);
    }
}
