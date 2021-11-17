package com.example.base;

import com.example.beans.resultbeans.ArticleCommenBean;
import com.example.beans.resultbeans.Content;
import com.example.beans.resultbeans.Data;
import com.example.beans.resultbeans.RichBean;
import com.example.beans.resultbeans.RichListBean;

import java.util.List;

public class ArticleWarrperBean {

    public static final int TYPE_ONLY_COMMENT = 0;
    public static final int TYPE_ONLY_PRISE_LIST = 1;
    public static final int TYPE_BOTH_ALL_ARTICLE = 2;

    private int type;
    private RichListBean mPriseList;
    private Data mContentList;


    public RichListBean getPriseList() {
        return mPriseList;
    }

    public void setPriseList(RichListBean priseList) {
        mPriseList = priseList;
    }

    public Data getContentList() {
        return mContentList;
    }

    public void setContentList(Data contentList) {
        mContentList = contentList;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return "ArticleWarrperBean{" +
                "type=" + type +
                ", mPriseList=" + mPriseList +
                ", mContentList=" + mContentList +
                '}';
    }
}
