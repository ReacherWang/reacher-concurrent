package com.reacher.concurrent.thread;

import java.util.concurrent.BlockingDeque;

/**
 * 类说明
 *
 * @author wanggw
 * @date 2017/7/28
 */
public abstract class ImportThread extends Thread {

    protected static final String DONE = "import-done";

    protected BlockingDeque<String[]> queue;

    public ImportThread(BlockingDeque<String[]> queue) {
        this.queue = queue;
    }
}
