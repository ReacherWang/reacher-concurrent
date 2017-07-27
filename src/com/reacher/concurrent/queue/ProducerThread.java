package com.reacher.concurrent.queue;

import au.com.bytecode.opencsv.CSVReader;

import java.util.concurrent.BlockingQueue;

/**
 * Created by reacher on 17-7-26.
 */
public class ProducerThread extends Thread {

    private BlockingQueue<String[]> queue;
    private CSVReader reader;

    public ProducerThread(BlockingQueue<String[]> queue, CSVReader reader) {
        this.queue = queue;
        this.reader = reader;
    }

    @Override
    public void run() {
        try {
            String[] datas = null;
            while ((datas = reader.readNext()) != null) {
                this.queue.put(datas);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
