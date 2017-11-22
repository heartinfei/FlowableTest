package com.dianxiaohuo.flowabletest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.codingmaster.slib.S;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * 简介：
 *
 * @author 王强 on 2017/11/21 249346528@qq.com
 */
public class DisposeTestActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispose_test);
    }

    CompositeDisposable cd = new CompositeDisposable();
    Observable<byte[]> a = Observable.just(1)
            .map((Integer integer) -> {
                byte buffer[] = new byte[1024 * 1024 * 2];
                S.i("");
                Thread.sleep(Integer.MAX_VALUE);
                buffer[1] = 1;
                return buffer;
            });

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_one:
                a.subscribeOn(Schedulers.newThread())
                        .observeOn(Schedulers.io())
                        .subscribe(new Observer<byte[]>() {
                            Disposable d;

                            @Override
                            public void onSubscribe(Disposable d) {
                                cd.add(d);
                                this.d = d;
                            }

                            @Override
                            public void onNext(byte[] integer) {
                                S.i(d.isDisposed());
                            }

                            @Override
                            public void onError(Throwable e) {
                                S.i("error:" + d.isDisposed());
                            }

                            @Override
                            public void onComplete() {

                                S.i("com:" + d.isDisposed());
                            }
                        });
                break;
            case R.id.btn_all:
                break;
            case R.id.btn_check:
                cd.dispose();
                break;
        }
    }
}
