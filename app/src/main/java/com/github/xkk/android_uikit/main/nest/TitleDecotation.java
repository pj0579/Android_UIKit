package com.github.xkk.android_uikit.main.nest;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.List;

public class TitleDecotation extends RecyclerView.ItemDecoration {


    private List<ClothBean> titles;
    private Paint paint;
    private Paint textPaint;
    private int mTitleHeight = 150;

    public TitleDecotation(List<ClothBean> cloth) {
        this.titles = cloth;
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(75);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = (parent.getLayoutManager()).getPosition(view);
        if (position == 0) {//第一个条目肯定需要Title
            outRect.set(0, mTitleHeight, 0, 0);
        } else if (titles.get(position).isHead()) {
            //当前条目和上一个条目的第一个拼音不同时需要Title
            outRect.set(0, mTitleHeight, 0, 0);
        } else { //
            outRect.set(0, 0, 0, 0);
        }
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            int position = (parent.getLayoutManager()).getPosition(child);
            c.drawRect(0, child.getBottom(), parent.getRight(), child.getBottom(), paint);

            if (titles.get(position).isHead()) {
                //画背景
                c.drawRect(0, child.getTop() - mTitleHeight, parent.getRight(), child.getTop(), paint);
                String c1 = titles.get(position).getName();
                Rect rect = new Rect();
                textPaint.getTextBounds(c1, 0, 1, rect);
                //画文字
                c.drawText(c1, 0, child.getTop() - (mTitleHeight / 2 - rect.height() / 2), textPaint);
            }
        }
    }


    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        //第一个可见条目的位置
        int position = ((LinearLayoutManager) parent.getLayoutManager()).findFirstVisibleItemPosition();
        View secondChild = parent.getLayoutManager().findViewByPosition(position + 1);
        if (secondChild.getTop() < 2 * mTitleHeight && secondChild.getTop() > mTitleHeight && titles.get(position+1).isHead()) {
            //当第二个title和第一个title重合时移动画板,产生动画效果
            c.translate(0, secondChild.getTop() - 300);
        }

        //画背景
        c.drawRect(0, 0, parent.getRight(), mTitleHeight, paint);
        String c1 = titles.get(position).getName();
        Rect rect = new Rect();
        textPaint.getTextBounds(c1, 0, 1, rect);
        //画文字
        c.drawText(c1, 0, mTitleHeight / 2 + rect.height() / 2, textPaint);
    }


}
