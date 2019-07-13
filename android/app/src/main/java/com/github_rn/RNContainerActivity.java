package com.github_rn;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactInstanceManagerBuilder;
import com.facebook.react.ReactPackage;
import com.facebook.react.ReactRootView;
import com.facebook.react.bridge.CatalystInstanceImpl;
import com.facebook.react.bridge.JSBundleLoader;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.react.uimanager.ViewManager;
import com.lugg.ReactNativeConfig.ReactNativeConfigPackage;
import com.microsoft.codepush.react.CodePush;
import com.oblador.vectoricons.VectorIconsPackage;
import com.reactnativecommunity.webview.RNCWebViewPackage;
import com.swmansion.gesturehandler.react.RNGestureHandlerPackage;

import org.devio.rn.splashscreen.SplashScreenReactPackage;
import org.devio.trackshare.TrackShareReactPackage;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RNContainerActivity extends AppCompatActivity {
    private static final int REQUEST_PERMISSION_CODE = 1;
    private ReactRootView mReactRootView;
    private ReactInstanceManager mReactInstanceManager;
    private String appEntry;
    private String appName;
    private Long startTime;
    private Long endTime;

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private LinearLayout ll;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            for (int i = 0; i < permissions.length; i++) {
                Log.i("MainActivity", "申请的权限为：" + permissions[i] + ",申请结果：" + grantResults[i]);
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_PERMISSION_CODE);
            }
        }

        mReactRootView = new ReactRootView(this);
        String bundleFilePath = getSDPath();
        boolean hasAppName = getIntent().hasExtra("appName");
        if (hasAppName) {
            appName = getIntent().getExtras().getString("appName");
            if (appName.equals("fengxiaoge")) {
                bundleFilePath += "/fengxiaoge.android.bundle";
            } else if (appName.equals("fengxiaodai")) {
                bundleFilePath += "/fengxiaodai.android.bundle";
            } else {

            }
        } else {
            bundleFilePath+="/index.android.bundle";
        }
        startTime = System.currentTimeMillis();


        mReactInstanceManager = ReactInstanceManager.builder()
                    .setApplication(getApplication())
                    .setJSBundleFile(bundleFilePath)
//                .setBundleAssetName("index.android.bundle")
                    .setJSMainModulePath("index")
                    .addPackage(new MainReactPackage())
                    .addPackage(new ReactNativeConfigPackage())
                    .addPackage(new CustomToastPackage())
                    .addPackage(new RNGestureHandlerPackage())
                    .addPackage(new RNCWebViewPackage())
                    .addPackage(new TrackShareReactPackage())
                    .addPackage(new CodePush(getResources().getString(R.string.reactNativeCodePush_androidDeploymentKey),getApplication(),BuildConfig.DEBUG))
                    .addPackage(new VectorIconsPackage())
                    .addPackage(new SplashScreenReactPackage())
                    .setUseDeveloperSupport(false)
                    .setInitialLifecycleState(LifecycleState.RESUMED)
                    .build();

        endTime = System.currentTimeMillis();
        String time = String.valueOf(endTime - startTime);
        System.out.println("花费时间:"+time);
        // 这个"App1"名字一定要和我们在index.js中注册的名字保持一致AppRegistry.registerComponent()
        String moduleName = "Github_RN";
        boolean hasAppEntry = getIntent().hasExtra("appEntry");
        if (hasAppEntry) {
            appEntry = getIntent().getExtras().getString("appEntry");
            if (appEntry != null && appEntry != "") {
                moduleName = appEntry;
            }
        }
        if (appName != null && appName != "") {
            boolean b = CacheRNRootViewInstance.getInstance().getRNRoots().containsKey(appName);
            if (b){
                ReactRootView reactRootView = CacheRNRootViewInstance.getInstance().getRNRoots().get(appName);
                mReactRootView = reactRootView;
//                mReactRootView.startReactApplication(mReactInstanceManager, moduleName, null);
//                ReactContext reactContext = CacheRNRootViewInstance.getInstance().getRNContext().get(appName);
//                hookReactManager(mReactRootView.getReactInstanceManager(),reactContext);
//                mReactRootView.getReactInstanceManager().recreateReactContextInBackground();

            }else {
                mReactRootView.startReactApplication(mReactInstanceManager, moduleName, null);
//                CacheRNRootViewInstance.getInstance().getRNContext().put(appName,mReactRootView.getReactInstanceManager().getCurrentReactContext());
                mReactInstanceManager.addReactInstanceEventListener(new ReactInstanceManager.ReactInstanceEventListener() {
                    @Override
                    public void onReactContextInitialized(ReactContext context) {
                        CacheRNRootViewInstance.getInstance().getRNRoots().put(appName,mReactRootView);

                        CacheRNRootViewInstance.getInstance().getRNContext().put(appName,mReactRootView.getReactInstanceManager().getCurrentReactContext());

                    }
                });
            }

        } else {
            mReactRootView.startReactApplication(mReactInstanceManager, moduleName, null);

            mReactInstanceManager.addReactInstanceEventListener(new ReactInstanceManager.ReactInstanceEventListener() {
                @Override
                public void onReactContextInitialized(ReactContext context) {
                    CacheRNRootViewInstance.getInstance().getRNRoots().put(appName,mReactRootView);

                    CacheRNRootViewInstance.getInstance().getRNContext().put(appName,mReactRootView.getReactInstanceManager().getCurrentReactContext());


                }
            });
        }

        setContentView(R.layout.activity_main);
        ll = (LinearLayout)findViewById(R.id.ll);
        ViewGroup parent = (ViewGroup)mReactRootView.getParent();
        if (parent != null) {
            parent.removeView(mReactRootView);
        }
        ll.addView(mReactRootView);



//        private void com.facebook.react.ReactInstanceManager.setupReactContext(com.facebook.react.bridge.ReactApplicationContext)
    }

    private void hookReactManager(Object obj, Context context){
        Method declaredMethods = null;
        try {
            declaredMethods = ReactInstanceManager.class.getDeclaredMethod("setupReactContext", ReactApplicationContext.class);
            declaredMethods.setAccessible(true);
            declaredMethods.invoke(obj,context);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        mReactInstanceManager.onHostResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mReactInstanceManager.onHostPause(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

//        boolean b = CacheRNRootViewInstance.getInstance().getRNRoots().containsKey(appName);
//        if (b){
//            ReactRootView reactRootView = CacheRNRootViewInstance.getInstance().getRNRoots().get(appName);
////            reactRootView.unmountReactApplication();
//            ViewGroup parent = (ViewGroup)reactRootView.getParent();
//            if (parent != null) {
//                parent.removeView(reactRootView);
//            }
//        }

        ViewGroup parent = (ViewGroup)mReactRootView.getParent();
        if (parent != null) {
            parent.removeView(mReactRootView);
        }
//        mReactInstanceManager.onHostDestroy(this);
//        mReactInstanceManager.destroy();
    }

    public class StartActivity extends ReactContextBaseJavaModule {

        private static final String DURATION_SHORT_KEY = "fengxiaoge";
        private static final String DURATION_LONG_KEY = "fengxiaodai";
        public StartActivity(ReactApplicationContext reactContext) {
            super(reactContext);
        }

        @javax.annotation.Nullable
        @Override
        public Map<String, Object> getConstants() {
            final Map<String, Object> constants = new HashMap<>();
            constants.put(DURATION_SHORT_KEY, "fengxiaoge");
            constants.put(DURATION_LONG_KEY, "fengxiaodai");
            return super.getConstants();
        }
        @ReactMethod
        public void startNewPage(String appName, String appEntry) {
            Intent intent = new Intent(RNContainerActivity.this,RNContainerActivity.class);
            intent.putExtra("appName",appName);
            intent.putExtra("appEntry",appEntry);
            startActivity(intent);
        }
        @Override
        public String getName() {
            return "startActivity";
        }
    }

    public class CustomToastPackage implements ReactPackage {

        @Override
        public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
            return Collections.emptyList();
        }

        @Override
        public List<NativeModule> createNativeModules(
                ReactApplicationContext reactContext) {
            List<NativeModule> modules = new ArrayList<>();

            modules.add(new StartActivity(reactContext));

            return modules;
        }

    }

    public String getSDPath(){
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);//判断sd卡是否存在
        if(sdCardExist)
        {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir.toString();
    }
}
