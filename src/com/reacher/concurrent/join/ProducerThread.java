package com.reacher.concurrent.join;

import au.com.bytecode.opencsv.CSVReader;

import java.util.concurrent.BlockingDeque;

/**
 * Created by reacher on 17-7-26.
 */
public class ProducerThread extends ImportThread {

    private CSVReader reader;

    public ProducerThread(BlockingDeque<String[]> queue, CSVReader reader) {
        super(queue);
        this.reader = reader;
    }

    @Override
    public void run() {
        try {
            String[] datas = null;
            while ((datas = reader.readNext()) != null) {
                this.queue.put(datas);
            }
            this.queue.put(new String[] {DONE});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
