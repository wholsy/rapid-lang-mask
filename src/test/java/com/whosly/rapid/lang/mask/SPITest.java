package com.whosly.rapid.lang.mask;

import java.util.concurrent.TimeUnit;

import com.whosly.rapid.lang.mask.internals.WatchServiceConfigLoader;
import org.junit.Test;

/**
 * Created by yueny on 2018/10/23 0023.
 */
public class SPITest {
    @Test
    public void test(){
        System.out.println(WatchServiceConfigLoader.get().getClass().getCanonicalName());
        System.out.println("EmailFields: " + WatchServiceConfigLoader.get().getEmailFields());

        try {
            TimeUnit.SECONDS.sleep(5L);
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("OVER！");
    }

}
