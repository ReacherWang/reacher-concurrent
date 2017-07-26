package com.reacher.concurrent.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by reacher on 17-7-26.
 */
public class ConsumerThread extends Thread{

    private BlockingQueue<String[]> queue;

    public ConsumerThread(String name, BlockingQueue<String[]> queue) {
        super(name);
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (!this.queue.isEmpty()) {
                System.out.println(test(this.queue.take()));
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String test(String[] datas) {
        StringBuffer buffer = new StringBuffer();
        for (String data: datas) {
            buffer.append(data).append(" ");
        }
        return buffer.toString();

    }
}
