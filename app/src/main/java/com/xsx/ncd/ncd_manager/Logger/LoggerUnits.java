package com.xsx.ncd.ncd_manager.Logger;

import android.content.Context;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LoggerUnits {

    private static final String MYLOG_PATH_SDCARD_DIR = "/mnt/sdcard/whnewcando/Log/";
    private static final String MYLOG_INFO_TAG = "xsx_info";
    private static final String MYLOG_ERROR_TAG = "xsx_error";

    private static final String MYLOG_INSERT_SPACE = "     ";
    private static final String MYLOG_MAOHAO = " : ";
    private static final String MYLOG_SPIDER = " $ ";

    private static SimpleDateFormat myLogSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 日志的输出格�??
    private static SimpleDateFormat logfile = new SimpleDateFormat("yyyy-MM-dd");// 日志文件格式

    private static final String MYLOG_INFO_FILE_NAME = "INFO";
    private static final String MYLOG_ERROR_FILE_NAME = "ERROR";
    private static String logDirPath = null;


    public static void LoggerUnitsInit(Context context){

        File logDir = new File(MYLOG_PATH_SDCARD_DIR);
        if(!logDir.exists())
            logDir.mkdir();
    }

    public static void info(String message){
        String str = makeLogContent(message);
        Log.i(MYLOG_INFO_TAG, str);
        write(MYLOG_INFO_FILE_NAME, MYLOG_INFO_TAG, str, null);
    }

    public static void info(String message, Throwable throwable){
        String str = makeLogContent(message);
        Log.i(MYLOG_INFO_TAG, str, throwable);
        write(MYLOG_INFO_FILE_NAME, MYLOG_INFO_TAG, str, throwable);
    }

    public static void error(String message){
        String str = makeLogContent(message);
        Log.e(MYLOG_ERROR_TAG, str);
        write(MYLOG_ERROR_FILE_NAME, MYLOG_ERROR_TAG, str, null);
    }

    public static void error(String message, Throwable throwable){
        String str = makeLogContent(message);
        Log.e(MYLOG_ERROR_TAG, str, throwable);
        write(MYLOG_ERROR_FILE_NAME, MYLOG_ERROR_TAG, str, throwable);
    }

    private static final void write(String logLevel, String tag, String msg, Throwable throwable) {
        Date date = Calendar.getInstance().getTime();
        String timeStamp = myLogSdf.format(date);
        String logFilePath = String.format("%s%s-%s.txt", MYLOG_PATH_SDCARD_DIR, logLevel, logfile.format(date));
        String logContent = String.format("%s %s | %s  %s %s", timeStamp, logLevel, tag, msg, Log.getStackTraceString(throwable));

        try {
            // 打开文件
            File file = new File(logFilePath);
            if(!file.exists())
                file.createNewFile();

            FileWriter filerWriter = new FileWriter(file, true);// 后面这个参数代表是不是要接上文件中原来的数据，不进行覆盖
            BufferedWriter bufWriter = new BufferedWriter(filerWriter);
            bufWriter.write(logContent);
            bufWriter.newLine();
            bufWriter.close();
            filerWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String makeLogContent(String message){
        StringBuffer stringBuffer = new StringBuffer();
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[4];

        stringBuffer.append(MYLOG_INSERT_SPACE);
        stringBuffer.append(stackTraceElement.getFileName());
        stringBuffer.append(MYLOG_SPIDER);
        stringBuffer.append(stackTraceElement.getMethodName());
        stringBuffer.append(MYLOG_SPIDER);
        stringBuffer.append(stackTraceElement.getLineNumber());
        stringBuffer.append(MYLOG_MAOHAO);
        stringBuffer.append(message);

        return stringBuffer.toString();
    }

}
