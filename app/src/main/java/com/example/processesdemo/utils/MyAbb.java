package com.example.processesdemo.utils;

import android.app.Application;
import android.content.Intent;

import com.example.processesdemo.service.ListenerService;
import com.example.processesdemo.utils.mylog.Logger;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.qw.soul.permission.SoulPermission;

import java.security.UnresolvedPermission;

/**
 * Created by GUO_YH on 2019/9/23 22:23
 */
public class MyAbb extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        SoulPermission.init(this);
        LiveEventBus
                .config()
                .supportBroadcast(this)
                .lifecycleObserverAlwaysActive(true)
                .autoClear(false);
    }
}
