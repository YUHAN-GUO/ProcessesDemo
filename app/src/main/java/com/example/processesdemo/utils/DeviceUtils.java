package com.example.processesdemo.utils;

import android.provider.Settings;
import android.text.TextUtils;

import java.util.UUID;

public class DeviceUtils {
    private static String PREF_KEY_UUID = "prefKeyId";
    public static String getUUID() {
        String uuid = getDeviceUUid();
        if (TextUtils.isEmpty(uuid)) {
            uuid = getAppUUid();
        }
        return uuid;
    }

    private static String getAppUUid() {
        String uuid = (String) SPUtil.getParam(Utils.getApp(),PREF_KEY_UUID,"");
        if (TextUtils.isEmpty(uuid)) {
            uuid = UUID.randomUUID().toString();
            //这里需要保存到SharedPreference中
            SPUtil.setParam(Utils.getApp(),PREF_KEY_UUID,uuid);
        }
        return uuid;
    }

    private static String getDeviceUUid() {
        String androidId = DeviceUtils.getAndroidID();
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) androidId.hashCode() << 32));
        return deviceUuid.toString();
    }

    private static String getAndroidID() {
        String id = Settings.Secure.getString(
                Utils.getApp().getContentResolver(),
                Settings.Secure.ANDROID_ID
        );
        return id == null ? "" : id;
    }
}
