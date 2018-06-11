package com.xsx.ncd.ncd_manager.Dao.Services;

import android.content.Context;

import com.xsx.ncd.ncd_manager.Dao.UserDao;
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
                emitter.onComplete();
            }
        });

        return observable;
    }

    public Observable<Boolean> deleteUserService(User user){
        Observable<Boolean> observable = Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {

                userDao.delete(user);

                emitter.onNext(true);
                emitter.onComplete();
            }
        });

        return observable;
    }

    public Observable<Boolean> updateUserService(User user){
        Observable<Boolean> observable = Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {

                userDao.update(user);

                emitter.onNext(true);

                emitter.onComplete();
            }
        });

        return observable;
    }

    public Observable<List<User>> queryAllUserService(){
        Observable<List<User>> observable = Observable.create(new ObservableOnSubscribe<List<User>>() {
            @Override
            public void subscribe(ObservableEmitter<List<User>> emitter) throws Exception {

                List<User> userList = userDao.all();

                emitter.onNext(userList);
                emitter.onComplete();
            }
        });

        return observable;
    }
}
