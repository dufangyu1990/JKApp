package com.wuxiaolong.pullloadmorerecyclerview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by WuXiaolong on 2015/7/2.
 * github:https://github.com/WuXiaolong/PullLoadMoreRecyclerView
 * weibo:http://weibo.com/u/2175011601
 * 微信公众号：吴小龙同学
 * 个人博客：http://wuxiaolong.me/
 */
public class PullLoadMoreRecyclerView extends LinearLayout {
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private PullLoadMoreListener mPullLoadMoreListener;
    private boolean hasMore = true;
    private boolean isRefresh = false;
    private boolean isLoadMore = false;
    private boolean pullRefreshEnable = true;//下拉刷新
    private boolean pushRefreshEnable = true;//上拉加载
    private View mFooterView;
    private Context mContext;
    private TextView loadMoreText;
    private ProgressBar mprogressBar;
    private LinearLayout loadMoreLayout;
    RecyclerViewOnScroll scrollListener;


    public PullLoadMoreRecyclerView(Context context) {
        super(context);
        initView(context);
    }

    public PullLoadMoreRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        View view = LayoutInflater.from(context).inflate(com.wuxiaolong.pullloadmorerecyclerview.R.layout.pull_loadmore_layout, null);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(com.wuxiaolong.pullloadmorerecyclerview.R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_dark, android.R.color.holo_blue_dark, android.R.color.holo_orange_dark);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayoutOnRefresh(this));

        mRecyclerView = (RecyclerView) view.findViewById(com.wuxiaolong.pullloadmorerecyclerview.R.id.recycler_view);
        mRecyclerView.setVerticalScrollBarEnabled(true);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


        mRecyclerView.setOnTouchListener(new onTouchRecyclerView());

        mFooterView = view.findViewById(R.id.footerView);

        loadMoreLayout = (LinearLayout) view.findViewById(R.id.loadMoreLayout);
        loadMoreText = (TextView) view.findViewById(R.id.loadMoreText);
        mprogressBar = (ProgressBar)view.findViewById(R.id.loadMoreProgressBar) ;
        mFooterView.setVisibility(View.GONE);

        this.addView(view);

    }


    /**
     * LinearLayoutManager
     */
    public void setLinearLayout() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
//        int spacingInPixels = mContext.getResources().getDimensionPixelSize(R);
//        mRecyclerView.addItemDecoration(new SpaceItemDecoratio(spacingInPixels));
    }

    /**
     * GridLayoutManager
     */

    public void setGridLayout(int spanCount) {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, spanCount);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(gridLayoutManager);
    }


    /**
     * StaggeredGridLayoutManager
     */

    public void setStaggeredGridLayout(int spanCount) {
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(spanCount, LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return mRecyclerView.getLayoutManager();
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public void setItemAnimator(RecyclerView.ItemAnimator animator) {
        mRecyclerView.setItemAnimator(animator);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration decor, int index) {
        mRecyclerView.addItemDecoration(decor, index);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration decor) {
        mRecyclerView.addItemDecoration(decor);
    }

    public void scrollToTop() {
        mRecyclerView.scrollToPosition(0);
    }


    public void setAdapter(RecyclerView.Adapter adapter) {
        if (adapter != null) {
            mRecyclerView.setAdapter(adapter);
        }
    }


    public void setPullRefreshEnable(boolean enable) {
        pullRefreshEnable = enable;
        setSwipeRefreshEnable(enable);
    }

    public boolean getPullRefreshEnable() {
        return pullRefreshEnable;
    }

    public void setSwipeRefreshEnable(boolean enable) {
        mSwipeRefreshLayout.setEnabled(enable);
    }

    public boolean getSwipeRefreshEnable() {
        return mSwipeRefreshLayout.isEnabled();
    }


    public void setColorSchemeResources(int... colorResIds) {
        mSwipeRefreshLayout.setColorSchemeResources(colorResIds);

    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return mSwipeRefreshLayout;
    }

    public void setRefreshing(final boolean isRefreshing) {
        Log.d("dfy","setRefreshing  = "+isRefreshing);
        mSwipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {
                Log.d("dfy","pullRefreshEnable  = "+pullRefreshEnable);
                if (pullRefreshEnable)
                    mSwipeRefreshLayout.setRefreshing(isRefreshing);
                //只出现下拉的动画，不支持下拉刷新功能，所以不判断pullRefreshEnable
//                mSwipeRefreshLayout.setRefreshing(isRefreshing);
            }



        });

    }



    //添加滑动接口
    public void setScrollListener(int count,int dataSize)
    {
        Log.d("dfy","scrollListener = "+scrollListener);
        if(scrollListener==null)
        {
            scrollListener = new RecyclerViewOnScroll(this,mSwipeRefreshLayout);
            mRecyclerView.addOnScrollListener(scrollListener);
        }
        scrollListener.setHideCount(count);
        scrollListener.setTotalCount(dataSize);


    }


    //添加滑动接口(金控用)
    public void setScrollListenerJK(int count,int dataSize)
    {



        if(scrollListener==null)
        {
            scrollListener = new RecyclerViewOnScroll(this,mSwipeRefreshLayout);
            mRecyclerView.addOnScrollListener(scrollListener);

        }
        scrollListener.setCurPageCount(count,dataSize);



    }



    public void smoothToTop()
    {
        mRecyclerView.smoothScrollToPosition(0);
    }









    /**
     * Solve IndexOutOfBoundsException exception
     */
    public class onTouchRecyclerView implements OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return isRefresh || isLoadMore;
        }
    }


    public boolean getPushRefreshEnable() {
        return pushRefreshEnable;
    }

    public void setPushRefreshEnable(boolean pushRefreshEnable) {
        this.pushRefreshEnable = pushRefreshEnable;
    }

    public LinearLayout getFooterViewLayout() {
        return loadMoreLayout;
    }

    public void setFooterViewBackgroundColor(int color) {
        loadMoreLayout.setBackgroundColor(ContextCompat.getColor(mContext, color));
    }

    public void setFooterViewText(CharSequence text) {
        loadMoreText.setText(text);
    }

    public void setFooterViewText(int resid) {
        loadMoreText.setText(resid);
    }

    public void setFooterViewTextColor(int color) {
        loadMoreText.setTextColor(ContextCompat.getColor(mContext, color));
    }


    public void refresh() {
        Log.d("dfy","refresh");
        if (mPullLoadMoreListener != null) {
            mPullLoadMoreListener.onRefresh();
        }
    }

    public void loadMore() {
        if (mPullLoadMoreListener != null && hasMore) {
            mFooterView.animate()
                    .translationY(0)
                    .setDuration(300)
                    .setInterpolator(new AccelerateDecelerateInterpolator())
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            mprogressBar.setVisibility(View.VISIBLE);
                            loadMoreText.setText(mContext.getString(R.string.load_more_text));
                            mFooterView.setVisibility(View.VISIBLE);
                        }
                    })
                    .start();
            invalidate();
            mPullLoadMoreListener.onLoadMore();

        }
    }



    public void noMoreData()
    {
        if(pushRefreshEnable)
        {
            mFooterView.animate()
                    .translationY(0)
                    .setDuration(300)
                    .setInterpolator(new AccelerateDecelerateInterpolator())
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            mFooterView.setVisibility(View.VISIBLE);
                            mprogressBar.setVisibility(View.GONE);
                            loadMoreText.setText("无更多数据");
                        }
                    }).start();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mFooterView.animate().translationY(mFooterView.getHeight())
                            .setDuration(300)
                            .setInterpolator(new AccelerateDecelerateInterpolator()).start();
                }
            },1500);

            //防止每次下滑到最后一条都弹出提示，改成false后就第一次弹出，以后不弹出
//            pushRefreshEnable = false;
        }

    }




    public void onScroll(int state)
    {
        if (mPullLoadMoreListener != null)
        {
            mPullLoadMoreListener.onScrolling(state);
        }
    }

    public void setPullLoadMoreCompleted() {
        isRefresh = false;
        setRefreshing(false);
        isLoadMore = false;
        mFooterView.animate()
                .translationY(mFooterView.getHeight())
                .setDuration(300)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();

    }


    public void setOnPullLoadMoreListener(PullLoadMoreListener listener) {
        mPullLoadMoreListener = listener;
    }


    public boolean isLoadMore() {
        return isLoadMore;
    }

    public void setIsLoadMore(boolean isLoadMore) {
        this.isLoadMore = isLoadMore;
    }

    public boolean isRefresh() {
        return isRefresh;
    }

    public void setIsRefresh(boolean isRefresh) {
        this.isRefresh = isRefresh;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public interface PullLoadMoreListener {
        void onRefresh();
        void onLoadMore();
        void onScrolling(int state);
        void onHide();
        void onShow();
    }

    public void onHide()
    {
        if (mPullLoadMoreListener != null)
        {
            mPullLoadMoreListener.onHide();
        }
    }
   public  void onShow()
    {
        if (mPullLoadMoreListener != null)
        {
            mPullLoadMoreListener.onShow();
        }
    }


}
