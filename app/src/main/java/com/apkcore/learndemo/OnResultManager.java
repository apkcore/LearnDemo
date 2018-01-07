package com.apkcore.learndemo;

import android.app.Activity;
import android.content.Intent;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.WeakHashMap;


/**
 * Created by apkcore on 2018/1/3. 22:36
 * mail:apkcore@gmail.com
 */
public class OnResultManager {
    private static final String TAG = OnResultManager.class.getSimpleName();

    private static WeakHashMap<Activity, HashMap<Integer, CallBack>> mCallbacks = new WeakHashMap<>();
    private WeakReference<Activity> mActivity;

    public OnResultManager(Activity activity) {
        mActivity = new WeakReference<Activity>(activity);
    }

    public void startForResult(Intent intent, int requestCode, CallBack callback) {
        Activity activity = getActivity();
        if (activity == null) {
            return;
        }
        addCallback(activity, requestCode, callback);
        activity.startActivityForResult(intent, requestCode);
    }

    public void trigger(int requestCode, int resultCode, Intent intent) {
        Activity activity = getActivity();
        if (activity == null) {
            return;
        }
        CallBack callback = findCallback(activity, requestCode);
        if (callback != null) {
            callback.onActivityResult(requestCode, resultCode, intent);
        }
    }

    private CallBack findCallback(Activity activity, int requestCode) {
        HashMap<Integer, CallBack> map = mCallbacks.get(activity);
        if (map == null) {
            return null;
        }
        return map.remove(requestCode);
    }

    private void addCallback(Activity activity, int requestCode, CallBack callback) {
        HashMap<Integer, CallBack> map = mCallbacks.get(activity);
        if (map == null) {
            map = new HashMap<>();
            mCallbacks.put(activity, map);
        }
        map.put(requestCode, callback);
    }

    private Activity getActivity() {
        return mActivity.get();
    }

    public interface CallBack {
        void onActivityResult(int requestCode, int resultCode, Intent data);
    }
}
