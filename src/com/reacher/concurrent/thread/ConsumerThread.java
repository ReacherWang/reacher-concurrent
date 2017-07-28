package com.reacher.concurrent.thread;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by reacher on 17-7-26.
 */
public class ConsumerThread extends ImportThread{

    private CountDownLatch latch;

    public ConsumerThread(BlockingDeque<String[]> queue, CountDownLatch latch) {
        super(queue);
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            while (null == this.queue.peek() || !this.queue.peek()[0].equals(DONE)) {
                System.out.println(this.queue.take());
                TimeUnit.SECONDS.sleep(1);
            }
            this.latch.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
