package com.reacher.concurrent.join;

import au.com.bytecode.opencsv.CSVReader;
import com.reacher.concurrent.CSVHelper;
import com.reacher.concurrent.TimeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 类说明
 *
 * @author wanggw
 * @date 2017/7/24
 */
public class ImportServiceImpl {

    private static final int CONSUMER_NUMBER = 20;

    private static final String NAME = "D:\\csv-test.csv";

    private static final String CODE = "GB18030";

    public void imports(String name, String code) throws Exception {

        long start = System.currentTimeMillis();

        CSVReader reader = CSVHelper.read(name, code);

        BlockingDeque<String[]> queue = new LinkedBlockingDeque<>(50);

        new ProducerThread(queue, reader).start();

        List<ImportThread> consumers = new ArrayList<>();
        for (int i = 0; i < CONSUMER_NUMBER; ++i) {
            ConsumerThread consumer = new ConsumerThread(queue);
            consumer.start();
            consumers.add(consumer);
        }
        for (ImportThread consumer: consumers) {
            consumer.join();
        }

        long end = System.currentTimeMillis();

        System.out.println(String.format("Used time: {%s}, status: {%s}", TimeUtils.print(end - start), queue.take()[0]));
    }

    public static void main(String[] args) throws Exception {
        ImportServiceImpl importService = new ImportServiceImpl();

        importService.imports(NAME, CODE);

    }

}
