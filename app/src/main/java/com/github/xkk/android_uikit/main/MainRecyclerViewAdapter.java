package com.github.xkk.android_uikit.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.github.xkk.android_uikit.R;
import com.github.xkk.android_uikit.common.BaseRecyclerViewAdapter;

import java.util.List;

public class MainRecyclerViewAdapter extends BaseRecyclerViewAdapter<String> {

    public MainRecyclerViewAdapter(List list, Context mContext, int layId) {
        super(list, mContext, layId);
    }

    @Override
    public void convert(ViewHolder holder, String itemData, int positon) {
        holder.setText(R.id.title, itemData);
    }
}
