package com.apkcore.learndemo.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.SparseArray;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

/**
 * 使启动activity和返回都是在无界面的fragment中进行
 * Created by apkcore on 2018/1/3. 23:10
 * mail:apkcore@gmail.com
 */
public class AvoidOnResultFragment extends Fragment {

    private SparseArray<PublishSubject<ActivityResultInfo>> mSubjects = new SparseArray<>();
    private SparseArray<AvoidOnResult.Callbck> mCallbcks = new SparseArray<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //保存状态
        setRetainInstance(true);
    }

    /**
     * 使用Observable返回
     *
     * @param intent
     * @param requestCode
     * @return
     */
    public Observable<ActivityResultInfo> startForResult(final Intent intent, final int requestCode) {
        PublishSubject<ActivityResultInfo> subject = PublishSubject.create();
        mSubjects.put(requestCode, subject);
        return subject.doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                startActivityForResult(intent, requestCode);
            }
        });
    }

    public void startForResult(Intent intent, int requestCode, AvoidOnResult.Callbck callBack) {
        mCallbcks.put(requestCode, callBack);
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        AvoidOnResult.Callbck callbck = mCallbcks.get(requestCode);
        if (callbck != null) {
            callbck.onActivityResult(requestCode, resultCode, data);
        }

        PublishSubject<ActivityResultInfo> subject = mSubjects.get(requestCode);
        if (subject != null) {
            subject.onNext(new ActivityResultInfo(requestCode, resultCode, data));
            subject.onComplete();
        }
    }
}
