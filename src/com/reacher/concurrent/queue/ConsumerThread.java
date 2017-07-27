package com.reacher.concurrent.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by reacher on 17-7-26.
 */
public class ConsumerThread extends Thread{

    private static final String DONE = "IMPORT-DONE";

    private BlockingQueue<String[]> queue;
    private CountDownLatch latch;

    public ConsumerThread(String name, BlockingQueue<String[]> queue, CountDownLatch latch) {
        super(name);
        this.queue = queue;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            while (true) {
                if(null != this.queue.peek() && this.queue.peek()[0].equals(DONE)) {
                    break;
                }
                System.out.println(Thread.currentThread().getName() + " : " + test(this.queue.take()));
                TimeUnit.SECONDS.sleep(1);
            }
            this.latch.countDown();
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
