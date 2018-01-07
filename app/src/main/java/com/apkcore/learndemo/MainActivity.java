package com.apkcore.learndemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.apkcore.learndemo.fragment.ActivityResultInfo;
import com.apkcore.learndemo.fragment.AvoidOnResult;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

//    private OnResultManager mOnResultManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());
//        mOnResultManager = new OnResultManager(this);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AvoidOnResult(MainActivity.this)
                        .startForResult(ScrollingActivity.class, 9527)
                        .filter(new Predicate<ActivityResultInfo>() {
                            @Override
                            public boolean test(ActivityResultInfo activityResultInfo) throws Exception {
                                return activityResultInfo.getResultCode() == 9527;
                            }
                        })
                        .subscribe(new Consumer<ActivityResultInfo>() {
                            @Override
                            public void accept(ActivityResultInfo activityResultInfo) throws Exception {
                                Log.d("bsb3", activityResultInfo.toString());
                            }
                        });
            }
        });
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AvoidOnResult(MainActivity.this).startForResult(ScrollingActivity.class, 9527, new AvoidOnResult.Callbck() {
                    @Override
                    public void onActivityResult(int requestCode, int resultCode, Intent data) {
                        Log.d("bsb3", requestCode + "-" + resultCode + data.getStringExtra("a"));
                    }
                });
            }
        });
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
////        mOnResultManager.trigger(9527, 9527, data);
//    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
