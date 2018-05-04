package com.xsx.ncd.ncd_manager.Dao.Services;

import android.content.Context;
import android.util.Log;

import com.xsx.ncd.ncd_manager.Dao.UserDao;
import com.xsx.ncd.ncd_manager.SerialDriver.SerialDefine;
import com.xsx.ncd.ncd_manager.SerialDriver.SerialEntity;
import com.xsx.ncd.ncd_manager.entity.User;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class UserService {

    private UserDao userDao = null;

    public UserService(Context context){
        userDao = new UserDao(context);
    }

    public Observable<Boolean> addNewUserService(User user){
        Observable<Boolean> observable = Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {

                userDao.add(user);

                emitter.onNext(true);
            }
        });

        return observable;
    }

    public Observable<List<User>> queryAllUserService(){
        Observable<List<User>> observable = Observable.create(new ObservableOnSubscribe<List<User>>() {
            @Override
            public void subscribe(ObservableEmitter<List<User>> emitter) throws Exception {

                List<User> userList = userDao.all();
                Log.d("xsx", "user list size: "+userList.size());
                emitter.onNext(userList);
                emitter.onComplete();
            }
        });

        return observable;
    }
}
