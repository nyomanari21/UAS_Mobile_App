package edu.upi.cs.yudiwbs.uas_template;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class ApiBored {
    private static final String BASE_URL = "https://www.boredapi.com/api/activity";
    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(RequestParams params, AsyncHttpResponseHandler responseHandler) {
        Log.d("debug_ari","panggil");
//        client.get(getAbsoluteUrl(url), params, responseHandler);
        client.get(BASE_URL, params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
