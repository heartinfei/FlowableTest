package com.dianxiaohuo.flowabletest;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.annimon.stream.Optional;
import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.List;

/**
 * 简介：
 *
 * @author 王强（249346528@qq.com） 2017/11/17.
 */

public class RAdapter extends RecyclerView.Adapter<RAdapter.VH> {
    private List<SBean> dataList = new ArrayList<>();

    public RAdapter() {
        super();
//        dataList.add(new SBean("测试数据", true, 100000000));
    }

    public void addItem(SBean s) {
        clearStatus();
        Optional<SBean> o = Stream.of(dataList)
                .filter((value) -> s.getId() == value.getId()).findFirst();
        if (Optional.empty().equals(o)) {
            dataList.add(s);
            notifyItemInserted(dataList.size() - 1);
        } else {
            SBean cache = o.get();
            cache.setCount(cache.getCount() + 1);
            cache.setChecked(true);
            notifyItemChanged(dataList.indexOf(cache));
        }
    }

    private void clearStatus() {
        Optional<SBean> co = Stream.of(dataList)
                .filter((v) -> v.isChecked()).findFirst();
        if (!Optional.empty().equals(co)) {
            SBean checkedBean = co.get();
            checkedBean.setChecked(false);
            notifyItemChanged(dataList.indexOf(checkedBean));
        }
    }


    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        SBean sBean = dataList.get(position);
        holder.ctv.setText(sBean.getData() + "---------数量：" + sBean.getCount());
        holder.ctv.setChecked(sBean.isChecked());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        CheckedTextView ctv;

        public VH(View itemView) {
            super(itemView);
            ctv = itemView.findViewById(R.id.ctv);
        }
    }
}
