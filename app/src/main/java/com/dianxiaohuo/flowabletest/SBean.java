package com.dianxiaohuo.flowabletest;

/**
 * 简介：
 *
 * @author 王强（249346528@qq.com） 2017/11/17.
 */

public class SBean {
    private String data;
    private boolean isChecked;
    private int id;
    private int count;

    public SBean(String data, boolean isChecked, int id) {
        this.data = data;
        this.isChecked = isChecked;
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
