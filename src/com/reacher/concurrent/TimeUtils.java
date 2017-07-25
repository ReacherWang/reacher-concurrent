package com.reacher.concurrent;

/**
 * 类说明
 *
 * @author wanggw
 * @date 2017/7/24
 */
public class TimeUtils {

    private static final int HOUR = 60 * 60 * 1000;

    private static final int MINUTE = 60 * 1000;

    private static final int SECONDS = 1000;

    public static String print(Long time) {
        Long hour = time / HOUR;

        time = null == hour ? time : (time - hour * HOUR);

        Long minute = time / MINUTE;

        time = null == minute ? time : (time - minute * MINUTE);

        Long seconds = time / SECONDS;

        time = null == seconds ? time : (time - seconds * SECONDS);

        return (null == hour ? 0 : hour) + "时" + (null == minute ? 0 : minute) + "分" + (null == seconds ? 0 : seconds) + "秒" + (null == time ? 0 : time) + "毫秒";
    }

}
