package com.manage;

import org.springframework.scheduling.annotation.Async;

import java.io.IOException;

public class T3343 {

    public static void main(String[] args) throws InterruptedException, IOException {

        T3343.doLog2("12312");

    }


      static void doLog2(String r) {
        System.out.println(r + " gogo");
          T3343.doLog(r);
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(r + " gogogo");


    }
    @Async
    static void doLog(String r) {
        System.err.println(r.toString() + " rejected");
    }


}
