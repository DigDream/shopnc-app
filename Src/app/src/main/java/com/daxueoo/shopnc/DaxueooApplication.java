package com.daxueoo.shopnc;

import android.app.Application;

import com.bugtags.library.Bugtags;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by user on 15-8-31.
 */
public class DaxueooApplication extends Application{
    public static String TAG;
    private static DaxueooApplication application;

    public static DaxueooApplication getInstance() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
        TAG = this.getClass().getSimpleName();
        application = this;

        //  移动应用测试
        Bugtags.start("da79d9f48cac87d186d86e1a8eba4814", this, Bugtags.BTGInvocationEventBubble);
    }
}
