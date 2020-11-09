package com.github.xkk.android_uikit.main.nest;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.xkk.android_uikit.R;
import com.github.xkk.android_uikit.common.BaseActivity;
import com.github.xkk.android_uikit.common.BaseRecyclerViewAdapter;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LuxuryPriceActivity extends BaseActivity implements LuxuryItemAdapter.ItemClick, View.OnClickListener, SelelctClothAdapter.ItemClick {
    @BindView(R.id.banner)
    ImageView imageView;
    @BindView(R.id.header_ll)
    LinearLayout linearLayout;
    @BindView(R.id.container)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.category_des)
    RecyclerView recyclerView_cate;
    @BindView(R.id.category_detail)
    RightRecycleView recyclerView_item;
    @BindView(R.id.cloth_select_list)
    RecyclerView recyclerView_select;
    @BindView(R.id.footer)
    RelativeLayout relativeLayout;
    @BindView(R.id.cart_icon)
    ImageView icon;
    @BindView(R.id.cart_items)
    RelativeLayout cart_items;
    @BindView(R.id.items)
    RelativeLayout items;
    @BindView(R.id.mask)
    View mask;
    @BindView(R.id.total_number)
    TextView total_number;
    @BindView(R.id.total_money)
    TextView total_money;

    @BindView(R.id.clearCloth)
    TextView clearCloth;

    private List<String> list = new ArrayList<>();
    private List<ClothBean> child = new ArrayList<>();
    private List<ClothBean> selectList = new ArrayList<>();
    private boolean isLeftControll = false;
    private LuxuryCateAdapter luxuryCateAdapter;
    private LuxuryItemAdapter luxuryItemAdapter;
    private SelelctClothAdapter selelctClothAdapter;
    private boolean flag = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtiy_nest);
        ButterKnife.bind(this);
        init();
    }

    /**
     * 初始化数据
     */
    public void init() {
        for (int i = 0; i < 3; i++) {
            list.add(i + "");
        }
        List<Integer> array = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {


                ClothBean clothBean = new ClothBean();
                clothBean.setName("衣服名字" + i);
                clothBean.setIndex(i);
                clothBean.setSelect(true);
                clothBean.setMoney("10");
                if(j==0){
                   clothBean.setHead(true);
                }
                child.add(clothBean);
            }
        }

        // 传入子列表 为了控制跳转
        luxuryCateAdapter = new LuxuryCateAdapter(list, this, R.layout.luxury_cate);

        // 传入父列表 为了控制滑动
        luxuryItemAdapter = new LuxuryItemAdapter(child, this, R.layout.luxury_item, this);
        luxuryItemAdapter.setMultiTypeSupport(new BaseRecyclerViewAdapter.MultiTypeSupport<ClothBean>() {
            @Override
            public int getLayoutId(ClothBean item) {
                return !item.isTitle() ? R.layout.luxury_item : R.layout.cloth_title_item;
            }
        });

        // 购物车衣物
        selelctClothAdapter = new SelelctClothAdapter(selectList, this, R.layout.cart_cloth_item, this);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        LinearLayoutManager mLayoutManager_detail = new LinearLayoutManager(this);
        LinearLayoutManager mLayoutManager_cart = new LinearLayoutManager(this);

        recyclerView_cate.setLayoutManager(mLayoutManager);
        recyclerView_item.setLayoutManager(mLayoutManager_detail);
        recyclerView_select.setLayoutManager(mLayoutManager_cart);

        recyclerView_cate.setAdapter(luxuryCateAdapter);
        recyclerView_item.setAdapter(luxuryItemAdapter);
        recyclerView_item.addItemDecoration(new TitleDecotation(child));
        recyclerView_select.setAdapter(selelctClothAdapter);

        recyclerView_select.setTag("test");
        setRightListScroll();
        setLeftListClick(array);
        relativeLayout.setOnClickListener(this);
        mask.setOnClickListener(this);
        items.setOnClickListener(this);
        clearCloth.setOnClickListener(this);
        setCartNumber(0);
        Glide.with(this).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1592485510473&di=a2d17e8d65f5d566380799ec0e4cc71e&imgtype=0&src=http%3A%2F%2Fimglf3.nosdn.127.net%2Fimg%2FL0lHRjFqbkVQa2pIQXFwYWE2WUd6Q3A4dUN5aFJhZlBOaTd0b3VDVFlQNUNTeGpQUGpZbGF3PT0.jpg%3FimageView%26thumbnail%3D1680x0%26quality%3D96%26stripmeta%3D0%26type%3Djpg%257Cwatermark%26type%3D2%26text%3Dwqkg").into(
                imageView
        );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mask:
                clickCart();
                break;
            case R.id.footer:
                clickCart();
                break;
            case R.id.items:
                break;
            case R.id.clearCloth:
                clearCloth();
                break;
        }
    }


    /**
     * 左边列表点击 右边列表响应
     *
     * @param indexToPositon
     */
    public void setLeftListClick(final List<Integer> indexToPositon) {
        luxuryCateAdapter.setOnItemClickListener(
                new BaseRecyclerViewAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        ObjectAnimator t = ObjectAnimator.ofFloat(linearLayout, "translationY", linearLayout.getTranslationY(), -linearLayout.getHeight());
                        t.start();
                        isLeftControll = true;
                        luxuryCateAdapter.setDefualtSelectIndex(position);
                        final SmoothScrollLayoutManager mTopScroller = new SmoothScrollLayoutManager(LuxuryPriceActivity.this);
                        mTopScroller.setTargetPosition(position*4);
                        recyclerView_item.getLayoutManager().startSmoothScroll(mTopScroller);
                        luxuryCateAdapter.notifyDataSetChanged();
                    }
                }
        );
    }

    /**
     * 右边列表滚动 左边列表响应
     */
    public void setRightListScroll() {
        recyclerView_item.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!isLeftControll) {
                    LinearLayoutManager rightLayoutManager =
                            (LinearLayoutManager) recyclerView_item.getLayoutManager();
                    int firstRightIndex = rightLayoutManager.findFirstCompletelyVisibleItemPosition();
                    luxuryCateAdapter.setDefualtSelectIndex(child.get(firstRightIndex).getIndex());
                    luxuryCateAdapter.notifyDataSetChanged();
                    LinearLayoutManager leftLayoutManager =
                            (LinearLayoutManager) recyclerView_cate.getLayoutManager();

                    int firstLeftIndex = leftLayoutManager.findFirstCompletelyVisibleItemPosition();
                    int lastLeftIndex = leftLayoutManager.findLastCompletelyVisibleItemPosition();
                    int needShowIndex = child.get(firstRightIndex).getIndex();
                    if (firstLeftIndex > needShowIndex) {
                        // 需要向上滚动
                        recyclerView_cate.getLayoutManager().scrollToPosition(needShowIndex);
                    } else if (needShowIndex > lastLeftIndex) {
                        // 需要向下滚动
                        recyclerView_cate.getLayoutManager().scrollToPosition(needShowIndex);
                    }
                }
            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == 0) {
                    isLeftControll = false;
                }
            }
        });

    }

    /**
     * add按钮点击
     *
     * @param fromView
     */
    @Override
    public void onItemAddClick(View fromView) {
        int[] addLoc = new int[2];
        fromView.getLocationOnScreen(addLoc);
        final int[] carLoc = new int[2];
        icon.getLocationOnScreen(carLoc);
        final int[] root = new int[2];
        coordinatorLayout.getLocationInWindow(root);
        final TextView textView = new TextView(this);
        textView.setBackgroundResource(R.drawable.add);
        textView.setText("");
        textView.setTextColor(Color.WHITE);
        textView.setGravity(Gravity.CENTER);
        CoordinatorLayout.LayoutParams lp = new CoordinatorLayout.LayoutParams(fromView.getWidth(), fromView.getHeight());
        coordinatorLayout.addView(textView, lp);
        Point controll = new Point(carLoc[0] - root[0], addLoc[1] - root[1]);
        Point startPoint = new Point(addLoc[0] - root[0], addLoc[1] - root[1]);
        Point endPoint = new Point(carLoc[0] - root[0], carLoc[1] - root[1]);
        ValueAnimator animator = ObjectAnimator.ofObject(new PointTypeEvaluator(controll), startPoint, endPoint);
        animator.setDuration(1000);
        animator.start();
        animator.addUpdateListener(
                new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        Point value = (Point) animation.getAnimatedValue();
                        textView.setY(value.getY());
                        textView.setX(value.getX());
                        if (value.getY() == carLoc[1] - root[1]) {
                            coordinatorLayout.removeView(textView);
                            selectList.clear();
                            SparseArray<ClothBean> clothBeanSparseArray = luxuryItemAdapter.getSelectList();

                            for (int i = 0; i < clothBeanSparseArray.size(); i++) {
                                selectList.add(clothBeanSparseArray.valueAt(i));
                            }
                            selelctClothAdapter.notifyDataSetChanged();
                            int cart_number = 0;
                            for (int i = 0; i < clothBeanSparseArray.size(); i++) {
                                if (clothBeanSparseArray.valueAt(i).isSelect()) {
                                    cart_number = cart_number + clothBeanSparseArray.valueAt(i).getCount();
                                }
                            }
                            setCartNumber(cart_number);
                        }
                    }
                }
        );
    }

    /**
     * 减少按钮
     *
     * @param view
     */
    @Override
    public void onItemreduceClick(View view) {
        selectList.clear();
        SparseArray<ClothBean> clothBeanSparseArray = luxuryItemAdapter.getSelectList();
        for (int i = 0; i < clothBeanSparseArray.size(); i++) {
            selectList.add(clothBeanSparseArray.valueAt(i));
        }
        selelctClothAdapter.notifyDataSetChanged();
        int cart_number = 0;
        for (int i = 0; i < clothBeanSparseArray.size(); i++) {
            if (clothBeanSparseArray.valueAt(i).isSelect()) {
                cart_number = cart_number + clothBeanSparseArray.valueAt(i).getCount();
            }
        }
        setCartNumber(cart_number);
    }

    /**
     * 购物车点击
     */
    public void clickCart() {
        if (flag && luxuryItemAdapter.getCount() != 0) {
            flag = false;
            cart_items.setTranslationY(0);
            selelctClothAdapter.notifyDataSetChanged();
        } else {
            flag = true;
            cart_items.setTranslationY(coordinatorLayout.getHeight());
        }
    }

    /**
     * 购物车衣物add
     *
     * @param view
     */
    @Override
    public void onCartItemAddClick(View view) {
        SparseArray<ClothBean> clothBeanSparseArray = luxuryItemAdapter.getSelectList();
        int cart_number = 0;
        for (int i = 0; i < clothBeanSparseArray.size(); i++) {
            if (clothBeanSparseArray.valueAt(i).isSelect()) {
                cart_number = cart_number + clothBeanSparseArray.valueAt(i).getCount();
            }
        }
        setCartNumber(cart_number);
        luxuryItemAdapter.notifyDataSetChanged();
    }

    /**
     * 购物车衣物减少
     *
     * @param view
     */
    @Override
    public void onCartItemreduceClick(View view) {
        SparseArray<ClothBean> clothBeanSparseArray = luxuryItemAdapter.getSelectList();
        int cart_number = 0;
        int cart_number_total = 0;
        for (int i = 0; i < clothBeanSparseArray.size(); i++) {
            if (clothBeanSparseArray.valueAt(i).isSelect()) {
                cart_number = cart_number + clothBeanSparseArray.valueAt(i).getCount();
            }
            cart_number_total = cart_number_total + clothBeanSparseArray.valueAt(i).getCount();
        }
        if (cart_number_total == 0) {
            clickCart();
        }
        setCartNumber(cart_number);
        luxuryItemAdapter.notifyDataSetChanged();
    }

    @Override
    public void resetCount() {
        SparseArray<ClothBean> clothBeanSparseArray = luxuryItemAdapter.getSelectList();
        int cart_number = 0;
        for (int i = 0; i < clothBeanSparseArray.size(); i++) {
            if (clothBeanSparseArray.valueAt(i).isSelect()) {
                cart_number = cart_number + clothBeanSparseArray.valueAt(i).getCount();
            }
        }
        setCartNumber(cart_number);
    }


    public void setCartNumber(int number) {
        total_number.setText(" " + number + " ");
        if (number == 0) {
            total_number.setVisibility(View.GONE);
        } else {
            total_number.setVisibility(View.VISIBLE);
        }
        SparseArray<ClothBean> clothBeanSparseArray = luxuryItemAdapter.getSelectList();
        int cart_money = 0;
        for (int i = 0; i < clothBeanSparseArray.size(); i++) {
            if (clothBeanSparseArray.valueAt(i).isSelect()) {
                Log.v("money",clothBeanSparseArray.valueAt(i).getMoney()+"");
                cart_money = cart_money+ Integer.parseInt(clothBeanSparseArray.valueAt(i).getMoney()) *clothBeanSparseArray.valueAt(i).getCount();
            }
        }
        total_money.setText("¥" + cart_money + " ");
    }

    public void clearCloth() {
        SparseArray<ClothBean> clothBeanSparseArray = luxuryItemAdapter.getSelectList();
        setCartNumber(luxuryItemAdapter.getCount());
        for (int i = 0; i < clothBeanSparseArray.size(); i++) {
            clothBeanSparseArray.valueAt(i).setCount(0);
        }
        clickCart();
        selelctClothAdapter.notifyDataSetChanged();
        luxuryItemAdapter.notifyDataSetChanged();
        setCartNumber(0);
    }
}
