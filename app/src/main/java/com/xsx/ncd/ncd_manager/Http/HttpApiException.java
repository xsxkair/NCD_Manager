package com.xsx.ncd.ncd_manager.Http;

public class HttpApiException extends Exception {

    public HttpApiException(String exceptionMessage) {
        super(exceptionMessage);
    }

    /**
     * 由于服务器传递过来的错误信息直接给用户看的话，用户未必能够理解
     * 需要根据错误码对错误信息进行一个转换，在显示给用户
     * @param code
     * @return
     */
    private static String getApiExceptionMessage(int code){

        return null;
    }
}
