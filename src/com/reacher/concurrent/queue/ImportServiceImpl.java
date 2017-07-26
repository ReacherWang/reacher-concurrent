package com.reacher.concurrent.queue;

import au.com.bytecode.opencsv.CSVReader;
import com.reacher.concurrent.CSVHelper;
import com.reacher.concurrent.TimeUtils;
import com.reacher.concurrent.forkjoin.ImportTask;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * 类说明
 *
 * @author wanggw
 * @date 2017/7/24
 */
public class ImportServiceImpl {

    private static final String NAME = "/home/reacher/Desktop/csv-test.csv";

    private static final String CODE = "GB18030";

    public void imports(String name, String code) throws Exception {

        long start = System.currentTimeMillis();

        List<Thread> consumers = new ArrayList<>();

        CSVReader reader = CSVHelper.read(name, code);

        BlockingQueue<String[]> queue = new LinkedBlockingDeque<>(50);
        ProducerThread producerThread = new ProducerThread(queue, reader);
        producerThread.start();
        for (int i = 0; i < 4; ++i) {
            ConsumerThread consumerThread = new ConsumerThread("Thread" + (i + 1), queue);
            consumerThread.start();
            consumers.add(consumerThread);
        }

        long end = System.currentTimeMillis();

        System.out.println("Used time: " + TimeUtils.print(end - start));
    }

    public void test(String name, String code) throws Exception {
        long start = System.currentTimeMillis();

        CSVReader reader = CSVHelper.read(name, code);

        List<String[]> rows = reader.readAll();

        for (int i = 0; i < rows.size(); ++i) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long end = System.currentTimeMillis();

        System.out.println("Test Used time: " + TimeUtils.print(end - start));
    }

    public static void main(String[] args) throws Exception {
        ImportServiceImpl importService = new ImportServiceImpl();

        importService.imports(NAME, CODE);

        importService.test(NAME, CODE);

    }

}
