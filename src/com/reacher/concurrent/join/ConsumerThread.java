package com.reacher.concurrent.join;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * Created by reacher on 17-7-26.
 */
public class ConsumerThread extends ImportThread {


    public ConsumerThread(BlockingDeque<String[]> queue) {
        super(queue);
    }

    @Override
    public void run() {
        try {
            while (null == this.queue.peek() || !this.queue.peek()[0].equals(DONE)) {
                System.out.println(this.queue.take()[0]);
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
