package com.example.processesdemo;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.processesdemo.utils.ProcessUtils;
import com.example.processesdemo.utils.mylog.Logger;

import java.util.Set;

public class FloatingViewService extends Service {
    private WindowManager mWindowManager;
    private AVCallFloatView mFloatingView;

    public FloatingViewService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //设置WindowManger布局参数以及相关属性
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        //初始化位置
        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 10;
        params.y = 100;
        //获取WindowManager对象
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }
        mFloatingView = new AVCallFloatView(FloatingViewService.this);
        mFloatingView.setParams(params);
        mFloatingView.setIsShowing(true);
        mFloatingView.setOnClickListener(new AVCallFloatView.OnClickListener() {
            @Override
            public void onClick() {
                Set<String> allBackgroundProcesses = ProcessUtils.getAllBackgroundProcesses();
                for (String allBackgroundProcess : allBackgroundProcesses) {
                    Logger.d("%s+++++%s","guoyh",allBackgroundProcess);
                }
                String currentProcessName = ProcessUtils.getCurrentProcessName();
                Logger.d("%s++++++++%s","guoyhCurr",currentProcessName);
                String foregroundProcessName = ProcessUtils.getForegroundProcessName();
                Logger.d("guoyhFor+++++++%s",foregroundProcessName);
                boolean mainProcess = ProcessUtils.isMainProcess();
                Logger.d("guoyh+++++++%s",""+mainProcess);

                Toast.makeText(FloatingViewService.this, ""+mainProcess, Toast.LENGTH_SHORT).show();
            }
        });
        mWindowManager.addView(mFloatingView, params);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //移除FloatingView
        if (mFloatingView != null) mWindowManager.removeView(mFloatingView);
    }
}
