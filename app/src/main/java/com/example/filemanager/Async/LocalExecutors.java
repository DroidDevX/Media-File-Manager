package com.example.filemanager.Async;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


/**
 * Helper class that provides threads to do work on the background and also
 * provides a main thread to return results from a background thread. <br/> <br/>
 *
 * */
public class LocalExecutors {

    private static LocalExecutors singleInstance;
    private Executor singleThreadExecutor;
    private Executor UIThread;

    private static int DEFAULT_THREAD_POOL_SIZE =2;
    private Executor threadPoolExecutor;

    public static LocalExecutors getInstance(){
        if(singleInstance==null){
            synchronized (LocalExecutors.class){
                if(singleInstance==null)
                    singleInstance = new LocalExecutors();
            }
        }
        return singleInstance;
    }

    private LocalExecutors(){
        singleThreadExecutor = Executors.newSingleThreadExecutor();
        threadPoolExecutor   = Executors.newFixedThreadPool(DEFAULT_THREAD_POOL_SIZE);
        UIThread = new MainThreadExecutor();
    }

    public synchronized Executor singleThreadExecutor(){
        return singleThreadExecutor;
    }

    public synchronized Executor threadPoolExecutor(){
        return threadPoolExecutor;
    }

    public synchronized Executor UIThread(){
        return UIThread;
    }

    private static class MainThreadExecutor implements Executor{
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(Runnable runnable) {
            mainThreadHandler.post(runnable);
        }
    }
}

