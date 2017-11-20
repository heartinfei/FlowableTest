package com.dianxiaohuo.flowabletest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 简介：
 *
 * @author 王强（249346528@qq.com） 2017/11/17
 */
public class MainActivity extends AppCompatActivity {
    RecyclerView rlv;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rlv = findViewById(R.id.recycler);
        rlv.setLayoutManager(new LinearLayoutManager(this));
        rlv.setAdapter(adapter = new MyAdapter());
        setUpData();
    }

    private void setUpData() {
        Observable.just("")
                .map((s) -> {
                    ActivityInfo[] activities = getPackageManager()
                            .getPackageInfo(getPackageName(), PackageManager.GET_ACTIVITIES)
                            .activities;
                    return Arrays.asList(activities);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((data) -> adapter.setData(data));
    }

    /**
     * 简介：
     *
     * @author
     */
    static class MyAdapter extends RecyclerView.Adapter<MyAdapter.VH> {
        private List<ActivityInfo> data = new ArrayList<>();

        public void setData(List<ActivityInfo> data) {
            this.data = data;
            notifyDataSetChanged();
        }

        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false));
        }

        @Override
        public void onBindViewHolder(VH holder, int position) {
            ActivityInfo info = data.get(position);
            holder.tv.setText(info.name);
            holder.tv.setClickable(true);
            holder.tv.setOnClickListener((v) -> {
                ComponentName componentName = new ComponentName(info.packageName, info.name);
                Intent intent = new Intent();
                intent.setComponent(componentName);
                v.getContext().startActivity(intent);

            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        static class VH extends RecyclerView.ViewHolder {
            TextView tv;

            public VH(View itemView) {
                super(itemView);
                tv = itemView.findViewById(R.id.ctv);
            }
        }
    }


}
