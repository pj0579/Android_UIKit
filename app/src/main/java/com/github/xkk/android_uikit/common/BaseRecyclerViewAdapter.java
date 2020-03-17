package com.github.xkk.android_uikit.common;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * 万能列表适配器
 * 使用方法：
 *
 *
 * @param <T>
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewAdapter.ViewHolder> {

    private MultiTypeSupport<T> multiTypeSupport;
    protected List<T> tList;
    protected Context mContext;
    protected LayoutInflater mLayoutInflater;
    protected int layId;
    private OnItemClickListener mOnItemClickListener;

    public BaseRecyclerViewAdapter(List<T> tList, Context mContext, int layId) {
        this.tList = tList;
        this.mContext = mContext;
        this.layId = layId;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public void onBindViewHolder(final BaseRecyclerViewAdapter.ViewHolder holder, int position) {
        convert(holder, tList.get(position),position);
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 为什么不使用position position不是实时变化的 getAdapterPosition也有问题 在调用未完成重新绘制时会返回-1 这个时候需要等待 因为数据已经变化
                    if (holder.getAdapterPosition() < 0) {
                        return;
                    }
                    mOnItemClickListener.onItemClick(holder.getAdapterPosition());
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 多布局支持
        if (multiTypeSupport != null) {
            layId = viewType;//这个就是getItemViewType方法的返回值，我们直接使用布局Id来做返回值，可以省略设置静态int值。
        }
        View view = mLayoutInflater.inflate(layId, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    /**
     * 点击事件接口
     */
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;

    }

    /**
     * 绑定数据和view
     *
     * @param holder
     * @param itemData
     */

    public abstract void convert(ViewHolder holder, T itemData,int positon);
    public void setMultiTypeSupport(MultiTypeSupport<T> multiTypeSupport) {
        this.multiTypeSupport = multiTypeSupport;
    }

    @Override
    public int getItemViewType(int position) {
        if (multiTypeSupport != null) {
            return multiTypeSupport.getLayoutId(tList.get(position));
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return tList.size();
    }

    /**
     * 多布局接口
     *
     * @param <T>
     */
    public interface MultiTypeSupport<T> {
        int getLayoutId(T item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private SparseArray<View> mViewArray;

        public ViewHolder(View itemView) {
            super(itemView);
            mViewArray = new SparseArray<>();
        }

        /**
         * 通过id得到view
         *
         * @param viewId
         * @param <V>
         * @return
         */
        @SuppressWarnings("unchecked")
        public <V extends View> V getViewAtId(int viewId) {
            View view = mViewArray.get(viewId);
            if (view == null) {
                view = itemView.findViewById(viewId);
                mViewArray.put(viewId, view);
            }
            return (V) view;
        }

        /**
         * 设置文字
         *
         * @param viewId
         * @param text
         * @return
         */
        public ViewHolder setText(int viewId, CharSequence text) {
            TextView textView = getViewAtId(viewId);
            textView.setText(text);
            return this;
        }


        /**
         * 通过网址设置图片
         *
         * @param viewId
         * @param imgUrl
         * @return
         */
        public ViewHolder setImageFromUrl(int viewId, String imgUrl) {
            ImageView imageView = getViewAtId(viewId);
            Glide.with(imageView.getContext()).load(imgUrl).into(imageView);
            return this;
        }

        /**
         * 通过资源来设置图片资源
         *
         * @param viewId
         * @param resId
         * @return
         */
        public ViewHolder setImageFromRes(int viewId, int resId) {
            ImageView imageView = getViewAtId(viewId);
            imageView.setImageResource(resId);
            return this;
        }

        /**
         * 通过bitmao来设置图片资源
         * @param viewId
         * @param bitmap
         * @return
         */
        public ViewHolder setBitmap(int viewId, Bitmap bitmap) {
            ImageView imageView = getViewAtId(viewId);
            imageView.setImageBitmap(bitmap);
            return this;
        }



        /**
         * 设置控件透明度
         *
         * @param viewId
         * @param visibility
         * @return
         */
        public ViewHolder setViewVisibility(int viewId, int visibility) {
            View view = getViewAtId(viewId);
            view.setVisibility(visibility);
            return this;
        }


    }
}