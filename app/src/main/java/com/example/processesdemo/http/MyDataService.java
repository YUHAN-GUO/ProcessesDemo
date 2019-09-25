package com.example.processesdemo.http;

public class MyDataService   {
    private static String baseUrl = "http://dlied5.myapp.com/";

    public static MyService getService() {
        return (MyService) BaseDataService.getService(MyService.class,baseUrl);
    }
}
