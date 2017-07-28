package com.reacher.concurrent.thread;

import au.com.bytecode.opencsv.CSVReader;
import com.reacher.concurrent.CSVHelper;
import com.reacher.concurrent.TimeUtils;

import java.util.concurrent.*;

/**
 * 类说明
 *
 * @author wanggw
 * @date 2017/7/24
 */
public class ImportServiceImpl {

    private static final int CONSUMER_NUMBER = 8;

    private static final String NAME = "D:\\csv-test.csv";

    private static final String CODE = "GB18030";

    public void imports(String name, String code) throws Exception {

        long start = System.currentTimeMillis();

        ExecutorService executorService = Executors.newCachedThreadPool();

        CSVReader reader = CSVHelper.read(name, code);

        BlockingDeque<String[]> queue = new LinkedBlockingDeque<>(50);

        executorService.execute(new ProducerThread(queue, reader));

        CountDownLatch latch = new CountDownLatch(CONSUMER_NUMBER);
        for (int i = 0; i < CONSUMER_NUMBER; ++i) {
            ConsumerThread consumerThread = new ConsumerThread(queue, latch);
            executorService.execute(consumerThread);
        }

        latch.await();

        long end = System.currentTimeMillis();

        System.out.println("Used time: " + TimeUtils.print(end - start));
    }

    public static void main(String[] args) throws Exception {
        ImportServiceImpl importService = new ImportServiceImpl();

        importService.imports(NAME, CODE);

        //importService.test(NAME, CODE);

    }

}
