package com.apkcore.taskfragment;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements TaskFragment.TaskCallbacks {

    private static final String TAG = "TaskFragment";
    private TaskFragment mTaskFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getFragmentManager();
        mTaskFragment = (TaskFragment) fm.findFragmentByTag(TAG);
        if (mTaskFragment == null) {
            mTaskFragment = new TaskFragment();
            fm.beginTransaction().add(mTaskFragment, TAG).commitAllowingStateLoss();
        }
    }

    @Override
    public void onPreExecute() {
        Log.d(TAG, "onPreExecute: ");
    }

    @Override
    public void onProgressUpdtae(int percent) {
        Log.d(TAG, "onProgressUpdtae: " + percent);
    }

    @Override
    public void onCancelled() {
        Log.d(TAG, "onCancelled: ");
    }

    @Override
    public void onPostExecute() {
        Log.d(TAG, "onPostExecute: ");
    }
}
