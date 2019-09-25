package com.example.processesdemo.http;

import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle2.components.support.RxFragment;


import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HttpUtils {

    public static <T> void obserableNoBaseUtils(Observable<T> mObservable, RxFragment rxFragment, IBaseHttpResultCallBack<T> callBack) {
        mObservable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(rxFragment.bindUntilEvent(FragmentEvent.DETACH))//生命周期绑定 rxlife
                .subscribe(new Observer<T>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(T t) {
                        try {
                            callBack.onSuccess(t);
                        } catch (Exception e) {

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        try {
                            callBack.onError(e);
                        } catch (Exception exception) {

                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 不需要BaseResult
     *
     * @param mObservable
     * @param activity
     * @param callBack
     * @param <T>
     */
    public static <T> void obserableNoBaseUtils(Observable<T> mObservable, RxAppCompatActivity activity, IBaseHttpResultCallBack<T> callBack) {
        mObservable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(activity.bindUntilEvent(ActivityEvent.DESTROY))//生命周期绑定 rxlife
                .subscribe(new Observer<T>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(T t) {
                        try {
                            callBack.onSuccess(t);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        try {
                            callBack.onError(e);
                        } catch (Exception exception) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public static <T> void obserableNoBaseUtils(Observable<T> mObservable, IBaseHttpResultCallBack<T> callBack) {
        mObservable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<T>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(T t) {
                        try {
                            callBack.onSuccess(t);
                        } catch (Exception e) {

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        try {
                            callBack.onError(e);
                        } catch (Exception exception) {

                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}