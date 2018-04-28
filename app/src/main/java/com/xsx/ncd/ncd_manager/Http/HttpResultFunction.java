package com.xsx.ncd.ncd_manager.Http;

import android.util.Log;

import com.xsx.ncd.ncd_manager.entity.User;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class HttpResultFunction<T, T1> implements Function<T, T1> {
    @Override
    public T1 apply(T httpResult) throws Exception {
        if(httpResult == null)
        {
            Log.d("xsx", "null data");
            throw new HttpApiException("登录失败");
        }

        return (T1)httpResult.toString();
    }
}
