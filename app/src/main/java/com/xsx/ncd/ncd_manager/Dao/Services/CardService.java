package com.xsx.ncd.ncd_manager.Dao.Services;

import android.content.Context;

import com.xsx.ncd.ncd_manager.Dao.CardDao;
import com.xsx.ncd.ncd_manager.entity.Card;
import com.xsx.ncd.ncd_manager.entity.TestData;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class CardService {

    private CardDao cardDao = null;

    public CardService(Context context){
        cardDao = new CardDao(context);
    }

    public CardDao getCardDao() {
        return cardDao;
    }

    public Observable<Boolean> saveNewCardService(Card card){
        Observable<Boolean> observable = Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {

                cardDao.add(card);

                emitter.onNext(true);
                emitter.onComplete();
            }
        });

        return observable;
    }

    public Observable<Card> queryCardService(int id){
        Observable<Card> observable = Observable.create(new ObservableOnSubscribe<Card>() {
            @Override
            public void subscribe(ObservableEmitter<Card> emitter) throws Exception {

                Card card = cardDao.queryById(id);

                emitter.onNext(card);
                emitter.onComplete();
            }
        });

        return observable;
    }
}
