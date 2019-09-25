package com.example.processesdemo.http;

import java.io.File;

public interface JsDownloadListener {
    void onStartDownload(long length);
    void onProgress(int progress);
    void onFail(String errorInfo);
    void onSuccess(File file);
}
