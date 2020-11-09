package com.github.xkk.android_uikit.main.nest;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.github.xkk.android_uikit.R;
import com.github.xkk.android_uikit.common.BaseRecyclerViewAdapter;

import java.util.List;

public class SelelctClothAdapter extends BaseRecyclerViewAdapter<ClothBean> {
    private ItemClick itemClick;
    private List mList;

    public SelelctClothAdapter(List list, Context mContext, int layId, ItemClick click) {
        super(list, mContext, layId);
        this.itemClick = click;
        this.mList = list;
    }

    public SelelctClothAdapter(List list, Context mContext, int layId) {
        super(list, mContext, layId);
    }

    @Override
    public void convert(ViewHolder holder, ClothBean itemData, final int positon) {
        if(itemData==null){
           return;
        }
        holder.setText(R.id.luxury_item, itemData.getName());
        holder.setText(R.id.number, itemData.getCount() + "");
        holder.setText(R.id.price, "¥100.00");
        holder.setImageFromUrl(R.id.image, "http://pic1.sc.chinaz.com/files/pic/pic9/202006/hpic2539.jpg");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("click", "item");
            }
        });

        holder.getViewAtId(R.id.add).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 添加
                ClothBean clothBean = (ClothBean) SelelctClothAdapter.this.mList.get(positon);
                clothBean.setCount(clothBean.getCount() + 1);
                SelelctClothAdapter.this.itemClick.onCartItemAddClick(v);
                notifyDataSetChanged();
            }
        });

        holder.getViewAtId(R.id.reduce).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 减少
                ClothBean clothBean = (ClothBean) SelelctClothAdapter.this.mList.get(positon);
                if (clothBean.getCount() == 1) {
                    clothBean.setCount(clothBean.getCount() - 1);
                    mList.remove(positon);
                    notifyDataSetChanged();
                } else if (clothBean.getCount() > 1) {
                    clothBean.setCount(clothBean.getCount() - 1);
                    notifyDataSetChanged();
                }
                SelelctClothAdapter.this.itemClick.onCartItemreduceClick(v);
            }
        });

        holder.getViewAtId(R.id.checkbox).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 减少
                ClothBean clothBean = (ClothBean) SelelctClothAdapter.this.mList.get(positon);
                clothBean.setSelect(!clothBean.isSelect());
                SelelctClothAdapter.this.itemClick.resetCount();
            }
        });
    }

    interface ItemClick {
        public void onCartItemAddClick(View view);

        public void onCartItemreduceClick(View view);

        public void resetCount();
    }

}
