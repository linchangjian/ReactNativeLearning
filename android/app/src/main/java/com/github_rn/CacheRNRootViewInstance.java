package com.github_rn;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;

import java.util.HashMap;
import java.util.Map;

public class CacheRNRootViewInstance {

    private static volatile CacheRNRootViewInstance mInstance = null;

    private Map<String, ReactRootView> RNRoots = new HashMap<>();
    private Map<String, ReactContext> RNContext = new HashMap<>();

    private CacheRNRootViewInstance(){

    }

    public static CacheRNRootViewInstance getInstance(){
        if (mInstance == null) {
            synchronized (CacheRNRootViewInstance.class) {
                if (mInstance == null){
                    mInstance = new CacheRNRootViewInstance();
                }
            }

        }
        return mInstance;
    }

    public Map<String, ReactRootView> getRNRoots() {
        return RNRoots;
    }

    public void setRNRoots(Map<String, ReactRootView> RNRoots) {
        this.RNRoots = RNRoots;
    }

    public Map<String, ReactContext> getRNContext() {
        return RNContext;
    }

    public void setRNContext(Map<String, ReactContext> RNContext) {
        this.RNContext = RNContext;
    }
}
