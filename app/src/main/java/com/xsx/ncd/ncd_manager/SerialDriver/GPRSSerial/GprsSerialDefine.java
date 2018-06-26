package com.xsx.ncd.ncd_manager.SerialDriver.GPRSSerial;

public class GprsSerialDefine {

    public static final String SERIAL_FILE_NAME = "/dev/ttySAC3";
    public static final long SERIAL_BAUD = 115200;
    public static final int SERIAL_READ_WAIT_TIME = 500000;                      //ms

    public static final int SERIAL_READBUF_LEN = 4096;

    public static final String GPRS_AT_CMD = "AT\r";
}
