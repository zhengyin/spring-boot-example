package com.izhengyin.springboot.example.transaction;

import java.util.concurrent.TimeUnit;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2020-12-25 16:17
 */
public class Sleep {
    public static void second(int n) {
        try {
            TimeUnit.SECONDS.sleep(n);
        }catch (InterruptedException e){

        }
    }
}
