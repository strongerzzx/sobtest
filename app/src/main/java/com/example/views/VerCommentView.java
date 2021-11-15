package com.example.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;


import com.bumptech.glide.Glide;
import com.example.beans.resultbeans.SubComment;
import com.example.sobdemo.R;
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
    private int mTotalCount;
    private int mPos;
    private SimpleWeakObjectPool<View> commentViewPool;
    private LayoutParams mLayoutParams;
    private int mCommentVerticalSpace;
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

    public void setTotalCount(int totalCount) {
        this.mTotalCount = totalCount;
    }

    public void setData(List<SubComment> subCommentList, int limit, boolean isMore) {
        mList.clear();
        if (subCommentList == null || subCommentList.isEmpty()) {
            return;
        }

        this.mList.addAll(subCommentList);

        int oldCount = getChildCount();
        if (!isMore && oldCount > 0) {
            removeViewsInLayout(0, oldCount);
        }
        int showCount = Math.min(limit, subCommentList.size());
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

      /*  if (secondBeansList.size() > 0) {
            addViewInLayout(makeMoreView(mTotalCount > showCount)
                    , showCount, generateMarginLayoutParams(showCount), true);
        }*/

        requestLayout();

    }

  /*  private View makeMoreView(boolean isMore) {
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.item_comment_single_load_more, null);
        if (isMore) {
            //TODO:
        }
        return view;
    }*/

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
        RoundedImageView ivChildAvator = inflate.findViewById(R.id.iv_child_avator);
        TextView tvChildPublishTimer = inflate.findViewById(R.id.tv_child_publish_timer);
        TextView tvChildComment = inflate.findViewById(R.id.tv_child_comment);
        TextView tvChildName = inflate.findViewById(R.id.tv_child_name);
        Glide.with(getContext())
                .load(subComment.getYourAvatar())
                .into(ivChildAvator);
        Log.d(TAG,"child name --> "+subComment.getBeNickname()+" : "+subComment.getYourNickname());
        tvChildName.setText(subComment.getYourNickname());
        tvChildPublishTimer.setText(subComment.getPublishTime());
        tvChildComment.setText(subComment.getContent());
        tvChildComment.setOnClickListener(new OnClickListener() {
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

    }

    @Override
    public void onChildViewRemoved(View parent, View child) {
        commentViewPool.put(child);
    }

    private OnSecCommentItemClick mOnSecCommentItemClick;

    public void setOnSecCommentItemClick(OnSecCommentItemClick onSecCommentItemClick) {
        mOnSecCommentItemClick = onSecCommentItemClick;
    }

    public interface OnSecCommentItemClick {
        void onSecItemClick(View view, SubComment content, int pos);
    }
}

