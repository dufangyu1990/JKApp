package com.wuxiaolong.pullloadmorerecyclerview;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

/**
 * Created by WuXiaolong
 * on 2015/7/7.
 * github:https://github.com/WuXiaolong/PullLoadMoreRecyclerView
 * weibo:http://weibo.com/u/2175011601
 * 微信公众号：吴小龙同学
 * 个人博客：http://wuxiaolong.me/
 */
public class RecyclerViewOnScroll extends RecyclerView.OnScrollListener {
    private PullLoadMoreRecyclerView mPullLoadMoreRecyclerView;
    private static final int HIDE_THRESHOLD = 20;

    private int mScrolledDistance = 0;
    private boolean mControlsVisible = true;
    private int count;//滑动到第几个位置,隐藏ToolBar
    private int dataSize;//数据总数,用来判定是否需要显示加载更多
    private int pageCount;//当前页面返回的数据条数,用来判定是否需要显示加载更多
    private int shouldCount;//应该返回的条数
    private SwipeRefreshLayout swipeRefreshLayout;
    public RecyclerViewOnScroll(PullLoadMoreRecyclerView pullLoadMoreRecyclerView,SwipeRefreshLayout swipeRefreshLayout) {
        this.mPullLoadMoreRecyclerView = pullLoadMoreRecyclerView;
        this.swipeRefreshLayout = swipeRefreshLayout;
    }
    public RecyclerViewOnScroll(PullLoadMoreRecyclerView pullLoadMoreRecyclerView,int count,int dataSize) {
        this.mPullLoadMoreRecyclerView = pullLoadMoreRecyclerView;
        this.count = count;
        this.dataSize = dataSize;
    }



    public void setHideCount(int count)
    {
        this.count =count;
    }

    public void setTotalCount(int dataSize)
    {
        this.dataSize = dataSize;
    }

    public void setCurPageCount(int pageCount,int shouldCount)
    {
        this.pageCount= pageCount;
        this.shouldCount = shouldCount;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (newState == RecyclerView.SCROLL_STATE_IDLE)
        {
            mPullLoadMoreRecyclerView.onScroll(0);
        }else if(newState == RecyclerView.SCROLL_STATE_DRAGGING)
        {
            mPullLoadMoreRecyclerView.onScroll(1);
        }


    }



    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int topRowVerticalPosition = (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
        swipeRefreshLayout.setEnabled(topRowVerticalPosition >= 0);



        int lastItem = 0;
        int firstItem = 0;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int totalItemCount = layoutManager.getItemCount();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = ((GridLayoutManager) layoutManager);
            firstItem = gridLayoutManager.findFirstCompletelyVisibleItemPosition();
            //Position to find the final item of the current LayoutManager
            lastItem = gridLayoutManager.findLastCompletelyVisibleItemPosition();
            if (lastItem == -1) lastItem = gridLayoutManager.findLastVisibleItemPosition();
        } else if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = ((LinearLayoutManager) layoutManager);
            firstItem = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
            lastItem = linearLayoutManager.findLastCompletelyVisibleItemPosition();
            if (lastItem == -1) lastItem = linearLayoutManager.findLastVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = ((StaggeredGridLayoutManager) layoutManager);
            // since may lead to the final item has more than one StaggeredGridLayoutManager the particularity of the so here that is an array
            // this array into an array of position and then take the maximum value that is the last show the position value
            int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
            staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(lastPositions);
            lastItem = findMax(lastPositions);
            firstItem = staggeredGridLayoutManager.findFirstVisibleItemPositions(lastPositions)[0];
        }


        if (firstItem == 0) {
            if(!mControlsVisible) {
                mPullLoadMoreRecyclerView.onShow();
                mControlsVisible = true;
            }
        } else {
            if(firstItem ==count)
            {
                if (mScrolledDistance > HIDE_THRESHOLD && mControlsVisible) {
                    mPullLoadMoreRecyclerView.onHide();
                    mControlsVisible = false;
                    mScrolledDistance = 0;
                } else if (mScrolledDistance < -HIDE_THRESHOLD && !mControlsVisible) {
                    mPullLoadMoreRecyclerView.onShow();
                    mControlsVisible = true;
                    mScrolledDistance = 0;
                }
            }

        }
        if((mControlsVisible && dy>0) || (!mControlsVisible && dy<0)) {
            mScrolledDistance += dy;
        }



        if (firstItem == 0 || firstItem == RecyclerView.NO_POSITION) {
            if (mPullLoadMoreRecyclerView.getPullRefreshEnable())
                mPullLoadMoreRecyclerView.setSwipeRefreshEnable(true);
        } else {
            mPullLoadMoreRecyclerView.setSwipeRefreshEnable(false);
        }


//        Log.d("dfy","getPushRefreshEnable() = "+mPullLoadMoreRecyclerView.getPushRefreshEnable());
//        Log.d("dfy","isRefresh() = "+mPullLoadMoreRecyclerView.isRefresh());
//        Log.d("dfy","isHasMore = "+mPullLoadMoreRecyclerView.isHasMore());
//        Log.d("dfy","isLoadMore() = "+mPullLoadMoreRecyclerView.isLoadMore());
//        Log.d("dfy","lastItem = "+lastItem);
//        Log.d("dfy","totalItemCount = "+totalItemCount);
//        Log.d("dfy","pageCount = "+pageCount);
        if (mPullLoadMoreRecyclerView.getPushRefreshEnable()
                && !mPullLoadMoreRecyclerView.isRefresh()
                && mPullLoadMoreRecyclerView.isHasMore()
                && (lastItem == totalItemCount - 1)
                && !mPullLoadMoreRecyclerView.isLoadMore()
//                && (totalItemCount<dataSize)
                && (pageCount==shouldCount)
                && (dx > 0 || dy > 0)) {
            Log.d("dfy","上拉加载");
            mPullLoadMoreRecyclerView.setIsLoadMore(true);
            mPullLoadMoreRecyclerView.loadMore();
        }else if((dx > 0 || dy > 0)&&(lastItem == totalItemCount - 1) && (pageCount<shouldCount)){
            mPullLoadMoreRecyclerView.setIsLoadMore(false);
            mPullLoadMoreRecyclerView.noMoreData();

        }

    }

    //To find the maximum value in the array
    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }
}
