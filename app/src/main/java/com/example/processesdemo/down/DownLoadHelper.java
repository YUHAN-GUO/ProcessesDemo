package com.example.processesdemo.down;

import android.util.Log;

import com.example.processesdemo.http.HttpUtils;
import com.example.processesdemo.http.IBaseHttpResultCallBack;
import com.example.processesdemo.http.JsDownloadListener;
import com.example.processesdemo.http.MyDataService;
import com.example.processesdemo.utils.mylog.Logger;
import com.luck.picture.lib.tools.Constant;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;


public class DownLoadHelper {
    private String  TAG = "guoyh";
    private DownLoadHelper() {
    }

    private volatile static DownLoadHelper mInstance;

    public static DownLoadHelper getInstance() {
        if (mInstance == null) {
            synchronized (DownLoadHelper.class) {
                if (mInstance == null) {
                    mInstance = new DownLoadHelper();
                }
            }
        }
        return mInstance;
    }

    public void downFile(String url, File file, JsDownloadListener callBack) {
        long range = 0;
        Observable<ResponseBody> download = MyDataService.getService().download(url);
        HttpUtils.obserableNoBaseUtils(download, new IBaseHttpResultCallBack<ResponseBody>() {
            @Override
            public void onSuccess(ResponseBody responseBody) {
                writeResponseBodyToDisk(responseBody,file,callBack);
            }

            @Override
            public void onError(Throwable e) {
                Logger.d("%s+++++++++%s","guoyh","Fail");

                callBack.onFail(e.getMessage());
            }
        });
    }

    /**
     * 将输入流写入文件
     *
     * @param inputString
     * @param file
     */
    private void writeFile(InputStream inputString, File file) {


    }


    /*

     Logger.d("%s+++++++++%s","guoyh","Success");
                InputStream is = null;
                OutputStream os = null;
                try {
                    long totalLen = responseBody.contentLength();
                    is = responseBody.byteStream();
                    os = new FileOutputStream(file);
                    byte[] buffer = new byte[8 * 1024];
                    int curlen = 0;
                    int bufferLen = 0;
                    while ((bufferLen = is.read(buffer)) != -1) {
                        os.write(buffer, 0, bufferLen);
                        os.flush();
                        curlen += bufferLen;
                        Logger.d("%s++++++++++%s","guoyh","++++++");
                        callBack.onProgress((int) (curlen * 1.0f / totalLen * 100));
                    }
                    file.setExecutable(true,false);
                    file.setReadable(true,false);
                    file.setWritable(true,false);
                    callBack.onSuccess(file);
                }catch (Throwable ignored){
                    ignored.printStackTrace();
                    callBack.onFail(ignored.getMessage());
                }finally {
                    if (is!=null){
                        try {
                            is.close();
                            if (os!=null) {
                                os.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            callBack.onFail(e.getMessage());
                        }
                    }
                }
     */
    private void writeResponseBodyToDisk(ResponseBody body,File file,final JsDownloadListener downloadListener) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (downloadListener != null)
                        downloadListener.onStartDownload(body.contentLength());
                    try {
                        // 改成自己需要的存储位置
                        Log.e(TAG, "writeResponseBodyToDisk() file=" + file.getPath());
//            if (file.exists()) {
//                file.delete();
//            }
                        InputStream inputStream = null;
                        OutputStream outputStream = null;

                        try {
                            byte[] fileReader = new byte[4096];

                            long fileSize = body.contentLength();
                            long fileSizeDownloaded = 0;

                            inputStream = body.byteStream();
                            outputStream = new FileOutputStream(file);

                            while (true) {
                                int read = inputStream.read(fileReader);

                                if (read == -1) {
                                    break;
                                }

                                outputStream.write(fileReader, 0, read);

                                fileSizeDownloaded += read;

                                //计算当前下载百分比，并经由回调传出
                                if (downloadListener != null)
                                    downloadListener.onProgress((int) (100 * fileSizeDownloaded / fileSize));
                                Log.d(TAG, "file download: " +(int) (100 * fileSizeDownloaded / fileSize));
                            }

                            if (downloadListener != null)
                                downloadListener.onSuccess(file);
                            outputStream.flush();

                        } catch (IOException e) {
                            if (downloadListener != null)
                                downloadListener.onFail("" + e.getMessage());
                        } finally {
                            if (inputStream != null) {
                                inputStream.close();
                            }

                            if (outputStream != null) {
                                outputStream.close();
                            }
                        }
                    } catch (IOException e) {
                        Logger.d("%s++++++++++%s","guoyh",e.getMessage());
                    }

                }
            }).start();
    }
}
