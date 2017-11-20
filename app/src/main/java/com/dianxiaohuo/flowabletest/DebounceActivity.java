package com.dianxiaohuo.flowabletest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * 简介：
 *
 * @author 王强 on 2017/11/20 249346528@qq.com
 */
public class DebounceActivity extends AppCompatActivity implements View.OnClickListener {
    ObservableEmitter<Integer> emitter;

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debounce);
        Observable.create((ObservableEmitter<Integer> e) -> emitter = e)
                .debounce(1, TimeUnit.SECONDS)
                .subscribe((i) -> Log.i("WTEST", "Exec:" + i));
    }

    @Override
    public void onClick(View v) {
        Log.i("WTEST", "click");
        emitter.onNext(v.getId());

    }
}
