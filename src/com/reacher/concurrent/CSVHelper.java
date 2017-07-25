package com.reacher.concurrent;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import java.io.*;

/**
 * 类说明
 *
 * @author wanggw
 * @date 2017/7/24
 */
public class CSVHelper {

    public static CSVReader read(String name, String code) throws Exception {
        return new CSVReader(new BufferedReader(new InputStreamReader(new FileInputStream(name), code)));
    }

    public static CSVWriter write(String name, String code) throws Exception {
        return new CSVWriter(new OutputStreamWriter(new FileOutputStream(name), code));
    }

    public static void close(Closeable ...files) throws Exception {
        if (null == files || 0 == files.length) {
            return;
        }
        for(Closeable file: files) {
            if (file instanceof Writer) {
                ((Writer) file).flush();
            }
            file.close();
        }
    }

}
