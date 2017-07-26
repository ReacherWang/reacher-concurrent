package com.reacher.concurrent.forkjoin;

import au.com.bytecode.opencsv.CSVReader;
import com.reacher.concurrent.CSVHelper;
import com.reacher.concurrent.TimeUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
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

        CSVReader reader = CSVHelper.read(name, code);

        List<String[]> rows = reader.readAll();

        ForkJoinPool pool = new ForkJoinPool();

        ImportTask task = new ImportTask(rows, 1, rows.size());

        pool.execute(task);

        Map<Integer, String> results = task.get();

        for (Map.Entry<Integer, String> entry : results.entrySet()) {
            //System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
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
