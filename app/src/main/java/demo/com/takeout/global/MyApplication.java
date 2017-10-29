package demo.com.takeout.global;

import android.app.Application;

import com.mob.MobSDK;

/**
 * Created by HASEE on 2017/1/10.
 */
public class MyApplication extends Application {

    public static int statusBarHeight;
    @Override
    public void onCreate() {
        super.onCreate();

        // 通过代码注册你的AppKey和AppSecret
        MobSDK.init(this, "21f8e0883e73c", "ea5dec86e217a4ab163215643bc87546");

        //获取status_bar_height资源的ID
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
    }
}
