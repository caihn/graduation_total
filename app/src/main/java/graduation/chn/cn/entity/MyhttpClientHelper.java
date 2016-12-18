package graduation.chn.cn.entity;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by len on 2016/12/15.thiis is network request data
 */

public class MyhttpClientHelper {
    public static String base_url = "http://119.29.229.196:88/";
    public static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(Context context,String url,AsyncHttpResponseHandler handler) {
        client.get( context, getAbsoluteUrl(url),handler);
    }//发起http get请求
    public static void post(Context context,String url,RequestParams params,AsyncHttpResponseHandler handler){
        client.post(context,getAbsoluteUrl(url),params,handler);
    }//发起http post请求
    public static String getAbsoluteUrl(String url){
        return base_url+url;
    }
}
