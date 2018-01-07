package com.apkcore.learndemo.fragment;

import android.content.Intent;

/**
 * Created by apkcore on 2018/1/3. 23:20
 * mail:apkcore@gmail.com
 */
public class ActivityResultInfo {
    private int requestCode;
    private int resultCode;
    private Intent data;

    public ActivityResultInfo(int requestCode, int resultCode, Intent data) {
        this.requestCode = requestCode;
        this.resultCode = resultCode;
        this.data = data;
    }

    public ActivityResultInfo() {
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public Intent getData() {
        return data;
    }

    public void setData(Intent data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return requestCode+" - "+resultCode+" - "+data.getStringExtra("a");
    }
}
