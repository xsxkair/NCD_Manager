package com.xsx.ncd.ncd_manager.SerialDriver;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import io.reactivex.functions.Function;

public class SerialResultFunction<SerialEntity> implements Function<SerialEntity, String> {
    @Override
    public String apply(SerialEntity result) throws Exception {
        if(result == null)
        {
            throw new NullPointerException("recv null data");
        }
        else{

            try{
                String dataStr = new String(result.getData());
                return dataStr;
            }catch (Exception e){
                throw new Exception("can not change to string");
            }
        }

        return null;
    }
}
