package com.dianxiaohuo.flowabletest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
/**
 * 简介：
 * @author 王强
 */
public class DebounceActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debounce);

    }

    @Override
    public void onClick(View v) {
        Log.i("WTEST", "click");
        Observable.just(v.getId())
                .debounce(1, TimeUnit.SECONDS)
                .subscribe((i) -> Log.i("WTEST", "Exec:" + i));
    }
}
