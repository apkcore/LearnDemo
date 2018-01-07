package com.apkcore.taskfragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by apkcore on 2018/1/5. 00:40
 * mail:apkcore@gmail.com
 */
public class TaskFragment extends Fragment {
    private static final String TAG = "TaskFragment";

    public interface TaskCallbacks {
        void onPreExecute();

        void onProgressUpdtae(int percent);

        void onCancelled();

        void onPostExecute();
    }

    private TaskCallbacks mTaskCallbacks;
    private DummyTask mDummyTask;

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: ");
        mTaskCallbacks = (TaskCallbacks) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        setRetainInstance(true);

        mDummyTask = new DummyTask();
        mDummyTask.execute();
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach: ");
        super.onDetach();
        mTaskCallbacks = null;
    }

    private class DummyTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 0; !isCancelled() && i < 100; i++) {
                SystemClock.sleep(100);
                publishProgress(i);
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            if (mTaskCallbacks != null) {
                mTaskCallbacks.onPreExecute();
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            if (mTaskCallbacks != null) {
                mTaskCallbacks.onProgressUpdtae(values[0]);
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (mTaskCallbacks != null) {
                mTaskCallbacks.onPostExecute();
            }
        }

        @Override
        protected void onCancelled() {
            if (mTaskCallbacks != null) {
                mTaskCallbacks.onCancelled();
            }
        }

    }
}
