package com.github.xkk.android_uikit.main.nest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.github.xkk.android_uikit.R;
import com.github.xkk.android_uikit.common.BaseRecyclerViewAdapter;

import java.util.List;

public class LuxuryItemAdapter extends BaseRecyclerViewAdapter<ClothBean> {
    private ItemClick itemClick;

    public void setCount(int count) {
        this.count = count;
    }

    private int count = 0;
    // 存储购物车🛒里的衣物
    private SparseArray<ClothBean> selectList = new SparseArray<>();

    public LuxuryItemAdapter(List<ClothBean> strings, Context mContext, int layId, ItemClick click) {
        super(strings, mContext, layId);
        this.itemClick = click;
    }

    public LuxuryItemAdapter(List<ClothBean> strings, Context mContext, int layId) {
        super(strings, mContext, layId);
    }

    @Override
    public void convert(ViewHolder holder, final ClothBean itemData, final int positon) {
        if (!itemData.isTitle()) {
            holder.setText(R.id.luxury_item, itemData.getName());
            holder.setText(R.id.number, itemData.getCount() + "");
            holder.setText(R.id.price, "¥100.00");
            holder.setImageFromUrl(R.id.image, "https://cube.elemecdn.com/e/82/181fd9702ad609fb0b21539078dcdjpeg.jpeg?x-oss-process=image/resize,m_lfit,w_140,h_140/watermark,g_se,x_4,y_4,image_YS8xYS82OGRlYzVjYTE0YjU1ZjJlZmFhYmIxMjM4Y2ZkZXBuZy5wbmc_eC1vc3MtcHJvY2Vzcz1pbWFnZS9yZXNpemUsUF8yOA%3D%3D/quality,q_90/format,webp");
            holder.getViewAtId(R.id.add).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    itemData.setCount(itemData.getCount() + 1);
                    notifyDataSetChanged();
                    LuxuryItemAdapter.this.itemClick.onItemAddClick(v);
                    count++;
                    selectList.put(positon, itemData);
                }
            });
            if (itemData.getCount() == 0) {
                holder.getViewAtId(R.id.number).setVisibility(View.GONE);
                holder.getViewAtId(R.id.reduce).setVisibility(View.GONE);
            } else {
                holder.getViewAtId(R.id.number).setVisibility(View.VISIBLE);
                holder.getViewAtId(R.id.reduce).setVisibility(View.VISIBLE);
            }

            holder.getViewAtId(R.id.reduce).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (itemData.getCount() != 0) {
                        LuxuryItemAdapter.this.itemClick.onItemreduceClick(v);
                        itemData.setCount(itemData.getCount() - 1);
                        count--;
                        notifyDataSetChanged();
                        if (itemData.getCount() == 0) {
                            selectList.remove(positon);
                        }
                        LuxuryItemAdapter.this.itemClick.onItemreduceClick(v);
                    }
                }
            });
        } else {
            holder.setText(R.id.title, itemData.getName());
        }
    }

    interface ItemClick {
        public void onItemAddClick(View view);

        public void onItemreduceClick(View view);
    }


    public int getCount() {
        return count;
    }

    public SparseArray<ClothBean> getSelectList() {
        return selectList;
    }
}
