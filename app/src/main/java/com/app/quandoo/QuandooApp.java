package com.app.quandoo;

import android.app.Application;

/**
 * Created by Nauman Afzaal on 09/10/2018.
 */
public class QuandooApp extends Application
{
    public static QuandooApp context;
    @Override
    public void onCreate()
    {
        super.onCreate();
        context = this;
    }
}
