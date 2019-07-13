package com.github_rn;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactPackage;
import com.facebook.react.ReactRootView;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RNContainerActivity2 extends AppCompatActivity {
    private ReactRootView mReactRootView;
    private ReactInstanceManager mReactInstanceManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mReactRootView = new ReactRootView(this);
        String bundleFilePath = getSDPath();
        boolean hasAppName = getIntent().hasExtra("appName");
        if (hasAppName) {
            String appName = getIntent().getExtras().getString("appName");
            if (appName.equals("fengxiaoge")) {
                bundleFilePath += "/fengxiaoge.android.bundle";
            } else if (appName.equals("fengxiaodai")) {
                bundleFilePath += "/fengxiaodai.android.bundle";
            } else {

            }
        } else {
            bundleFilePath+="/index.android.bundle";
        }

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
        // 这个"App1"名字一定要和我们在index.js中注册的名字保持一致AppRegistry.registerComponent()
        String moduleName = "Github_RN";
        boolean hasAppEntry = getIntent().hasExtra("appEntry");
        if (hasAppEntry) {
            String appEntry = getIntent().getExtras().getString("appEntry");
            if (appEntry != null && appEntry != "") {
                moduleName = appEntry;
            }
        }
        mReactRootView.startReactApplication(mReactInstanceManager, moduleName, null);
        setContentView(R.layout.activity_main);
        LinearLayout ll = (LinearLayout)findViewById(R.id.ll);
        ll.addView(mReactRootView);


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
        mReactInstanceManager.onHostDestroy(this);
        mReactInstanceManager.destroy();
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
            Intent intent = new Intent(RNContainerActivity2.this, RNContainerActivity2.class);
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
                .equals(Environment.MEDIA_MOUNTED);//判断sd卡是否存在
        if(sdCardExist)
        {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir.toString();
    }
}
