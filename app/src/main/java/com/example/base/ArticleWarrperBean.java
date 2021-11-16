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
    private List<RichBean> mPriseList;
    private List<Content> mContentList;

    public List<Content> getContentList() {
        return mContentList;
    }

    public void setContentList(List<Content> contentList) {
        mContentList = contentList;
    }

    public List<RichBean> getPriseList() {
        return mPriseList;
    }

    public void setPriseList(List<RichBean> priseList) {
        mPriseList = priseList;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


}
