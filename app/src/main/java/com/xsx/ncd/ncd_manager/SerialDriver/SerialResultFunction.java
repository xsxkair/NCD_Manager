package com.xsx.ncd.ncd_manager.SerialDriver;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

public class SerialResultFunction<T, R> implements Function<T, R> {

    @Override
    public R apply(T result) throws Exception {
        SerialEntity serialEntity = null;
        String dataStr = null;

        if(result instanceof SerialEntity){
            serialEntity = (SerialEntity) result;

            Type type = new TypeToken<R>(){}.getType();
            Gson gson = new Gson();

            try{
                dataStr = new String(serialEntity.getData());
            }catch (Exception e){
                throw new Exception("recv data can not be changed to string");
            }

            try{
                return gson.fromJson(dataStr, type);

            }catch (Exception e){
                throw new Exception("recv data can not be changed to string");
            }
        }
        else
            throw new Exception("recv data error!");

    }
}
