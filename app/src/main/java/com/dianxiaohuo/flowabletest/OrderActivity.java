package com.dianxiaohuo.flowabletest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 简介：
 *
 * @author 王强（249346528@qq.com） 2017/11/17
 */
public class OrderActivity extends AppCompatActivity implements View.OnClickListener {
    private FlowableEmitter<Integer> emitter;
    private final FlowableOnSubscribe<Integer> flowable = (e) -> this.emitter = e;
    private Subscription d;

    RecyclerView rlv;
    RAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        rlv = findViewById(R.id.rlv);
        rlv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RAdapter();
        adapter.notifyDataSetChanged();
        rlv.setAdapter(adapter);
        Flowable.create(flowable, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map((s) -> {
                    //TODO: some stuff
//                    Thread.sleep(1000);
                    return s;
                })
                .onErrorReturnItem(-1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        d = s;
                        d.request(1);
                    }

                    @Override
                    public void onNext(Integer s) {
                        Log.i("WTEST", "处理结果:" + s);
                        //TODO: LiveData.postValue(s);
                        if (s < 0) {
                            return;
                        }
                        SBean sBean = new SBean("测试数据" + s, true, s);
                        int p = adapter.addItem(sBean);
//                        ((LinearLayoutManager) rlv.getLayoutManager()).scrollToPosition(p);
                        rlv.smoothScrollToPosition(p);
                        d.request(1);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    int i = 0;

    @Override
    public void onClick(View v) {
        i++;
        Log.i("WTEST", i + "");
        switch (v.getId()) {
            case R.id.btn_repeat:
//                int item = i % 3;
                emitter.onNext(50);
                break;
            case R.id.btn_add:
                for (int j = 0; j < 100; j++) {
                    emitter.onNext(j);
                }
                break;
            default:
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        emitter.onComplete();
        d.cancel();
    }
}
