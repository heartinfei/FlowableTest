package com.dianxiaohuo.flowabletest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codingmaster.slib.S;

import java.util.ArrayList;
import java.util.List;

public class RecyAdapterTestActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView rlv;
    RecyclerView.Adapter adapter;
    List<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recy_adapter_test);
        rlv = findViewById(R.id.rlv);
        for (int i = 0; i < 100; i++) {
            data.add(i + "");
        }
        adapter = new RecyclerView.Adapter<VH>() {
            @Override
            public VH onCreateViewHolder(ViewGroup parent, int viewType) {
                S.i("");
                return new VH(getLayoutInflater().inflate(android.R.layout.simple_list_item_1, null, false));
            }

            @Override
            public void onBindViewHolder(VH holder, int position) {
                S.i(position);
                holder.tv.setText(data.get(position));
            }

            @Override
            public int getItemCount() {
                return data.size();
            }
        };
        rlv.setLayoutManager(new LinearLayoutManager(this));
        rlv.setAdapter(adapter);
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tv;

        public VH(View itemView) {
            super(itemView);
            tv = itemView.findViewById(android.R.id.text1);
        }
    }

    @Override
    public void onClick(View v) {
        S.i("click");
        adapter.notifyDataSetChanged();
    }
}
