package graduation.chn.cn.entity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by len on 2016/12/15. t
 * his is check network state class
 */

public class CheckNetState {
    public static String checkState(Context context) {
        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info != null && info.isAvailable()) {
                int netType = info.getType();
                if (netType == ConnectivityManager.TYPE_WIFI) {
                    return "wifi";
                } else if (netType == ConnectivityManager.TYPE_MOBILE) {
                    return "mobile";
                } else {
                    return "other";
                }
            } else {
                Toast.makeText(context, "网络连接失败，请检查您的网络！", Toast.LENGTH_SHORT).show();
            }
        }
       return null;
    }
}
