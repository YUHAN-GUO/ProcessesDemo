package com.example.processesdemo.http;

/*
 * created by taofu on 2018/11/29
 **/
public interface IBaseHttpResultCallBack<T> {
    void onSuccess(T data);
    void onError(Throwable e);

}
