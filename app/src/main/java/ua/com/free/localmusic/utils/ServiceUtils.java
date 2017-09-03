package ua.com.free.localmusic.utils;

import android.app.ActivityManager;
import android.content.Context;

/**
 * Created by Dmitry Risovanyi
 * on 30.08.2017.
 */

public class ServiceUtils {

    public static boolean isMyServiceRunning(Context context, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
