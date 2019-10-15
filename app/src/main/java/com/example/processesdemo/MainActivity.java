package com.example.processesdemo;


import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.processesdemo.bean.LocationBean;
import com.example.processesdemo.down.DownLoadHelper;
import com.example.processesdemo.http.HttpUtils;
import com.example.processesdemo.http.IBaseHttpResultCallBack;
import com.example.processesdemo.http.JsDownloadListener;
import com.example.processesdemo.http.MyDataService;
import com.example.processesdemo.service.ListenerService;
import com.example.processesdemo.service.NotificationService;
import com.example.processesdemo.utils.DeviceUtils;
import com.example.processesdemo.utils.LocationUtils;
import com.example.processesdemo.utils.PermissionUtils;
import com.example.processesdemo.utils.mylog.Logger;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends RxAppCompatActivity implements View.OnClickListener {

    //用户访问权限
    private int TYPE_USEAG = 0x1;
    //安装应用程序
    private int TYPE_INSALL = 0x2;

    private Button btn;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private PackageManager pm;
    private ProgressDialog progressDialog;

    //如果缓存过大，会造成app 体积过大 需要在Activity销毁前删除 和下载完成后删除
    private Boolean flagIsDelete = true;
    private Boolean flagIsCom = true;

    private String apkName = "ZhouzhiHouse.apk";
    private File apkFile;
    private Button btn5;
    private Button btn6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
        pm = getPackageManager();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (apkFile != null) {
            boolean delete = FileUtils.delete(apkFile.getAbsolutePath());
            if (delete) {
                Logger.d("%s+++++++++++%s", "guoyh", "DeleteSuccess");
            } else {
                Logger.d("%s+++++++++++%s", "guoyh", "DeleteFail");
            }
        }

    }

    private void initListener() {
        LiveEventBus.get(ListenerService.TASK_COM).observe(this, new Observer<Object>() {
            @Override
            public void onChanged(Object o) {
                btn2.setText("完成任务");
            }
        });
    }

    private void initView() {
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(this);

        btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(this);

        btn3 = (Button) findViewById(R.id.btn3);
        btn3.setOnClickListener(this);

        btn4 = (Button) findViewById(R.id.btn4);
        btn4.setOnClickListener(this);

        btn5 = (Button) findViewById(R.id.btn5);
        btn5.setOnClickListener(this);
        btn6 = (Button) findViewById(R.id.btn6);
        btn6.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                //判断浮窗权限 2.判断 使用情况 3.启动service
                checkDrawPermiss();
                break;
            case R.id.btn2:
                /**
                 * 1.首先得判断 使用情况权限 是否开启
                 * 2. 判断手机上是否有该APP
                 *      2a:如果有申请失败 需要删除
                 * 3.开启后 判断是否有对应 应用商店
                 *      3a 如果有跳转到对应的AppShop
                 *      3b:没有 进行下载
                 * 4.下载对应的App 进行安装
                 * 5.安装成功 后按钮 改成 打开APP试玩 -- 秒
                 * 6任务完成后   按钮 改成 上传截图
                 */
                checkUsagePermiss();
                break;
            case R.id.btn3:
                startNotifService();
                break;
            case R.id.btn4:
                flagIsCom = true;
                apkFile = new File(FileUtils.getApkPath(MainActivity.this), apkName);

                String apkPath = apkFile.getAbsolutePath();
                Logger.d("%s++++++++++++%s", "guoyh", apkPath);

                boolean delete = FileUtils.delete(apkPath);
                if (delete) {
                    Logger.d("%s+++++++++++%s", "guoyh", "DeleteSuccess");
                } else {
                    Logger.d("%s+++++++++++%s", "guoyh", "DeleteFail");
                }
//                Logger.d("%s+++++++++%s","guoyh",apkPath);
                downLoad();
                break;
            case R.id.btn5:
                getId();
                break;
                /*
                获取当前位置 所需权限
                <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
<uses-permission android:name="android.permission.INTERNET" />
                 */
            case R.id.btn6:
                //http://ip.taobao.com/service/getIpInfo.php?ip=123.149.237.181
                //http://ip.taobao.com/service/getIpInfo.php?ip=123.149.237.181

//                HttpUtils.obserableNoBaseUtils(MyDataService.getService().getLocation(), new IBaseHttpResultCallBack<LocationBean>() {
//                    @Override
//                    public void onSuccess(LocationBean data) {
//                        Logger.d("%s++++++++++%s","guoyh",data.toString());
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Logger
//                                .d("%s++++++++++%s","guoyh",e.getMessage());
//                    }
//                });

                LocationUtils.register(this, 0, 0, new LocationUtils.OnLocationChangeListener() {
                    @Override
                    public void getLastKnownLocation(Location location) {
                        Log.e("xyh", "onLocationChanged: " + location.getLatitude());
                    }

                    @Override
                    public void onLocationChanged(Location location) {
                        //位置信息变化时触发
                        Log.e("guoyh", "定位方式：" + location.getProvider());
                        Log.e("guoyh", "纬度：" + location.getLatitude());
                        Log.e("guoyh", "经度：" + location.getLongitude());
                        Log.e("guoyh", "海拔：" + location.getAltitude());
                        Log.e("guoyh", "时间：" + location.getTime());
                        Log.e("guoyh", "国家：" + LocationUtils.getCountryName(MainActivity.this, location.getLatitude(), location.getLongitude()));
                        Log.e("guoyh", "获取地理位置：" + LocationUtils.getAddress(MainActivity.this, location.getLatitude(), location.getLongitude()));
                        Log.e("guoyh", "所在地：" + LocationUtils.getLocality(MainActivity.this, location.getLatitude(), location.getLongitude()));
                        Log.e("guoyh", "所在街道：" + LocationUtils.getStreet(MainActivity.this, location.getLatitude(), location.getLongitude()));

                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {
                    }
                });
                break;
        }
    }

    private void getId() {
        try {
//            getAsync();
        } catch (Exception e) {

            Logger.d("%s++++++++++++%s", "guoyh", e.getMessage());
        }
        String UUID = DeviceUtils.getUUID();
        Logger.d("%s++++++++++++%s", "guoyh", UUID);
    }

    private void downLoad() {
        checkCanInsatllPermiss();
    }

    private void startNotifService() {
        Intent intent = new Intent(this, NotificationService.class);
        startService(intent);
    }

    /**
     *
     * @param b 判断是否完成任务
     */
    private void startListenerService(boolean b) {
        Logger.d("%s+++++++++++++%s", "guoyh", "开启监听");
        Intent intent = new Intent(this, ListenerService.class);
        intent.putExtra(ListenerService.TASK_PACKNAME, "com.tencent.qqlive");
        if (b) {
            startService(intent);
        } else {
            stopService(intent);
        }
    }


    private void checkDrawPermiss() {
        if (PermissionUtils.isGrantedDrawOverlays()) {
            startFloatService();
            checkUsagePermiss();
        } else {
            openDraw();
        }
    }

    private void checkCanInsatllPermiss() {
        checkCanInsatllPermiss(false);
    }

    private void checkCanInsatllPermiss(boolean b) {
        if (PermissionUtils.isGrantedCanInstall()) {
            downFile();
        } else {
            if (b) {
                Toast.makeText(this, "您拒绝开启安装应用程序权限", Toast.LENGTH_SHORT).show();
            } else {
                openInstall();
            }
        }
    }


    private void checkUsagePermiss() {
        checkUsagePermiss(false);
    }

    private void checkUsagePermiss(boolean b) {
        if (PermissionUtils.isGrantedUsageStats()) {
            if (btn2.getText().toString().equals("完成任务")) {
                startListenerService(false);
                btn2.setText("申请任务");
            } else {
                startListenerService(true);
            }
        } else {
            if (b) {
                Toast.makeText(this, "您拒绝开启访问使用权限", Toast.LENGTH_SHORT).show();
            } else {
                openUsage();
            }
        }
    }

    private void openInstall() {
        PermissionUtils.startCanInstallPermissActivity(this, TYPE_INSALL);

    }

    private void openUsage() {
        PermissionUtils.startUsageStatusPermissionActivity(this, TYPE_USEAG);
    }

    private void openDraw() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionUtils.requestDrawOverlays(new PermissionUtils.SimpleCallback() {
                @Override
                public void onGranted() {
                    checkUsagePermiss();
                }

                @Override
                public void onDenied() {
                    Toast.makeText(MainActivity.this, "你拒绝了开启悬浮权限", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logger.d("%s++++++++++%s", "guoyh", "++++++++++++");
        if (requestCode == TYPE_USEAG) {
            checkUsagePermiss(true);
        }
        if (requestCode == TYPE_INSALL) {
            checkCanInsatllPermiss(true);
        }

    }

    private void toInsall(File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { //android N的权限问题
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//授权读权限
            Uri contentUri = FileProvider.getUriForFile(MainActivity.this, "com.example.processesdemo.fileprovider", file);//注意修改
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        startActivity(intent);
    }

    /**
     * 前往开启浮窗Service
     */
    private void startFloatService() {
        Intent intent = new Intent(MainActivity.this, FloatingViewService.class);
        startService(intent);

    }

    /**
     * 判断手机是否安装某个应用
     *
     * @param context
     * @param appPackageName 应用包名
     * @return true：安装，false：未安装
     */
    public static boolean isApplicationAvilible(Context context, String appPackageName) {
        PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (appPackageName.equals(pn)) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * ---------------------------------------------Download--------------------------------------
     */
    public void downFile() {
        Logger.d("%s+++++++%s", "guoyh", "开始下载");
        //进度条，在下载的时候实时更新进度，提高用户友好度
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("正在下载");
        progressDialog.setMessage("请稍候...");
        progressDialog.setProgress(0);
        progressDialog.show();
        //获取文件路径
        toDown("myapp/1105999355/muses/10030965_com.tencent.tako.muses_h101_1.0.101_319e47.apk", apkFile);
    }

    private void toDown(String url, File file) {
        DownLoadHelper.getInstance().downFile(url, file, new JsDownloadListener() {
            @Override
            public void onStartDownload(long length) {

            }

            @Override
            public void onProgress(int progress) {
                if (progress > 98) {
                    Logger.d("%s+++++++++++%s", "guoyh", "下载中" + progress);
                }
                progressDialog.setProgress(progress);
            }

            @Override
            public void onFail(String errorInfo) {

            }

            @Override
            public void onSuccess(File file) {
                Logger.d("%s+++++++++++%s", "guoyh", "下载完成" + 123456);
                downSuccess(file);
            }
        });

    }

    private void downSuccess(File file) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        Logger.d("%s++++++++++%s", "guoyh", "下载完成");
        if (flagIsCom) {
            toInsall(file);
        } else {
            Logger.d("%s++++++++++%s", "guoyh", "下载失败");
            Toast.makeText(this, "下载失败", Toast.LENGTH_SHORT).show();
        }

    }

}
