package com.example.views;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;


import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.beans.resultbeans.SubComment;
import com.example.sobdemo.R;
import com.example.sobdemo.databinding.ItemCommentSingleLoadMoreBinding;
import com.example.sobdemo.databinding.ItemVerCommentViewLayoutBinding;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;


public
/**
 * 作者：zzx on 2021/11/13 18:21
 *  作用： xxxx
 */
class VerCommentView extends LinearLayout implements ViewGroup.OnHierarchyChangeListener {
    private static final String TAG = "VerCommentView";
    private int mPos;
    private SimpleWeakObjectPool<View> commentViewPool;
    private LayoutParams mLayoutParams;
    private final int MAX_SHOW_COUNT = 3;
    private int mCommentVerticalSpace = 1;
    private List<SubComment> mList;

    public VerCommentView(Context context) {
        this(context, null);
    }

    public VerCommentView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerCommentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        commentViewPool = new SimpleWeakObjectPool<>();
        setOnHierarchyChangeListener(this);
        mList = new ArrayList<>();
    }

    public void setData(List<SubComment> subCommentList, int limit, boolean isMore) {
        mList.clear();
        if (subCommentList == null || subCommentList.isEmpty()) {
            return;
        }
        mList.addAll(subCommentList);
        int oldCount = getChildCount();
        if (!isMore && oldCount > 0) {
            removeViewsInLayout(0, oldCount);
        }
        int showCount = Math.min(limit, MAX_SHOW_COUNT);
        for (int i = 0; i < showCount; i++) {
            //如果下标比ViewGroup的孩子少 -->就代表里面有孩子
            boolean hasChild = i < oldCount;
            //也有可能是第一次加载
            View childView = hasChild ? getChildAt(i) : null;
            SubComment subComment = subCommentList.get(i);
            if (childView == null) {
                childView = commentViewPool.get();
                if (childView == null) {
                    addViewInLayout(makeCommentItemView(subComment, i)
                            , i, generateMarginLayoutParams(i), true);
                } else {
                    addCommentItemView(childView, subComment, i);
                }
            } else {
                updateCommentData(childView, subComment, i);
            }
        }

        //loadMore
        if (subCommentList.size() > MAX_SHOW_COUNT) {
            addViewInLayout(makeMoreView(true, mList)
                    , MAX_SHOW_COUNT - subCommentList.size(), generateMarginLayoutParams(showCount), true);
        }
        requestLayout();
    }

    private View makeMoreView(boolean isMore, List<SubComment> list) {
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.item_comment_single_load_more, null);
        ItemCommentSingleLoadMoreBinding loadMoreBinding = ItemCommentSingleLoadMoreBinding.bind(view);
        if (isMore) {
            loadMoreBinding.tvReplyLoadMore.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<SubComment> loadMoreList = list.subList(MAX_SHOW_COUNT, list.size());
                    for (int i = 0; i < loadMoreList.size(); i++) {
                        addView(makeView(loadMoreList.get(i), i));
                    }
                    loadMoreBinding.tvReplyLoadMore.setVisibility(View.GONE);
                    Log.d(TAG, "click load more reply --> ");
                }
            });
        }
        return view;
    }

    private void updateCommentData(View childView, SubComment subComment, int index) {
        bindView(childView, subComment);
    }

    private void addCommentItemView(View childView, SubComment subComment, int index) {
        View view = makeCommentItemView(subComment, index);
        addViewInLayout(view, index, generateMarginLayoutParams(index), true);
    }

    private LayoutParams generateMarginLayoutParams(int index) {
        if (mLayoutParams == null) {
            mLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                    , ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        if (mList != null && index > 0) {
            mLayoutParams.topMargin = (int) (mCommentVerticalSpace * 1.2f);
        }
        return mLayoutParams;
    }

    private View makeCommentItemView(SubComment subComment, int index) {
        return makeView(subComment, index);
    }

    private View makeView(SubComment subComment, int index) {
        View inflate = LayoutInflater.from(getContext())
                .inflate(R.layout.item_ver_comment_view_layout, this, false);
        bindView(inflate, subComment);
        return inflate;
    }

    private void bindView(View inflate, SubComment subComment) {
        ItemVerCommentViewLayoutBinding binding = ItemVerCommentViewLayoutBinding.bind(inflate);
        Glide.with(getContext())
                .load(subComment.getYourAvatar())
                .apply(RequestOptions.circleCropTransform())
                .into(binding.ivChildAvator);
        binding.tvReplyChildName.setVisibility(TextUtils.isEmpty(subComment.getBeNickname()) ? View.GONE : View.VISIBLE);
        binding.tvReplyChildName.setText("  回复  " + subComment.getBeNickname());
        binding.tvChildName.setText(subComment.getYourNickname());
        binding.tvChildComment.setText(subComment.getContent());
        binding.tvChildPublishTimer.setText(subComment.getPublishTime());
        binding.tvChildComment.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnSecCommentItemClick != null) {
                    mOnSecCommentItemClick.onSecItemClick(v, subComment, mPos);
                }
            }
        });


    }


    public void setPosition(int position) {
        this.mPos = position;
    }

    @Override
    public void onChildViewAdded(View parent, View child) {
        commentViewPool.put(child);
        Log.d(TAG, "pool size --> " + commentViewPool.size());
    }

    @Override
    public void onChildViewRemoved(View parent, View child) {
    }


    private OnSecCommentItemClick mOnSecCommentItemClick;

    public void setOnSecCommentItemClick(OnSecCommentItemClick onSecCommentItemClick) {
        mOnSecCommentItemClick = onSecCommentItemClick;
    }

    public interface OnSecCommentItemClick {
        void onSecItemClick(View view, SubComment content, int pos);
    }
}

