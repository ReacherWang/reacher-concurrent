package com.reacher.concurrent.fork;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

/**
 * 类说明
 *
 * @author wanggw
 * @date 2017/7/24
 */
public class ImportTask extends RecursiveTask<Map<Integer, String>> {

    private List<String[]> datas;
    private int start;
    private int end;

    public ImportTask(List<String[]> datas, int start, int end) {
        this.datas = datas;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Map<Integer, String> compute() {
        if(this.end - this.start < 50) {
            return handle(this.start, this.end);
        }

        int middle = (this.start + this.end) / 2;
        ImportTask task1 = new ImportTask(this.datas, this.start, middle);
        ImportTask task2 = new ImportTask(this.datas, middle, this.end);
        invokeAll(task1, task2);

        try {
            return this.groupResults(task1.get(), task2.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Map<Integer, String> groupResults(Map<Integer, String> ...results) {
        Map<Integer, String> temps = new HashMap<>();

        for(Map<Integer, String> result: results) {
            temps.putAll(result);
        }

        return temps;
    }

    private Map<Integer, String> handle(int start, int end) {
        Map<Integer, String> results = new HashMap<>();
        for (int i = start; i < end; ++i) {

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            results.put(i, this.toString(this.datas.get(i)));
        }

        return results;
    }

    private String toString(String[] datas) {
        StringBuffer buffer = new StringBuffer();

        for (String data: datas) {
            buffer.append(data + " ");
        }

        return buffer.toString();
    }
}
