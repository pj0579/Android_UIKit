package com.github.xkk.android_uikit.main.nest;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.github.xkk.android_uikit.R;
import com.github.xkk.android_uikit.common.BaseRecyclerViewAdapter;

import java.util.List;

public class LuxuryCateAdapter extends BaseRecyclerViewAdapter<String> {
    private ScrollControll scrollControll;
    // 现在被选中的下标
    private int selectIndex = 0;
    // 子列表
    private RecyclerView child;
    // index对应的子列表的position

    private List<Integer> indexToPositon;

    public LuxuryCateAdapter(List<String> strings, Context mContext, int layId, RecyclerView childRecyclerView, List<Integer> indexToPosition, ScrollControll scrollControll) {
        super(strings, mContext, layId);
        this.child = childRecyclerView;
        this.indexToPositon = indexToPosition;
        this.scrollControll = scrollControll;
    }

    public LuxuryCateAdapter(List<String> strings, Context mContext, int layId) {
        super(strings, mContext, layId);
    }

    @Override
    public void convert(final ViewHolder holder, String itemData, final int positon) {
        holder.setText(R.id.cate_name, itemData);
        if (positon == selectIndex) {
            holder.itemView.setBackgroundColor(Color.WHITE);
        } else {
            holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.un_select));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectIndex = positon;
                notifyDataSetChanged();
            }
        });
    }

    public void setDefualtSelectIndex(int index) {
        selectIndex = index;
    }

    interface ScrollControll {
        public void scroll(int y);
    }
}
