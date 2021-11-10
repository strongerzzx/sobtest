package com.example.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beans.resultbeans.ArticleCommenBean
import com.example.beans.resultbeans.Content
import com.example.beans.resultbeans.TestTypeBean
import com.example.sobdemo.R
import com.example.sobdemo.databinding.ArticleCommentBlankLayoutBinding
import com.example.sobdemo.databinding.ArticleCommentChildLayoutBinding
import com.example.sobdemo.databinding.ArticleCommentParentLayoutBinding
import java.lang.IllegalArgumentException

class ArticelCommentAdpater : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mList: List<TestTypeBean>? = null
    private val mCommonList = mutableListOf<Content>()
    private val mData = mutableListOf<ArticleCommenBean>()


    private lateinit var mHeaderCommentListener:
                (articleId: String, parentId: String, beUid: String, beNickname: String) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            TYPE_PARENT_COMMENT -> {
                //父评论
                val inflate = LayoutInflater.from(parent.context)
                    .inflate(R.layout.article_comment_parent_layout, parent, false)
                val bind = ArticleCommentParentLayoutBinding.bind(inflate)
                Log.d(TAG, "onCreateViewHolder -->  ")
                return ParentViewHolder(bind)
            }
            TYPE_CHILD_COMMENT -> {
                //子评论
                val inflate = LayoutInflater.from(parent.context)
                    .inflate(R.layout.article_comment_child_layout, parent, false)
                val bind = ArticleCommentChildLayoutBinding.bind(inflate)
                Log.d(TAG, "onCreateViewHolder -->  ")
                return ChildViewHolder(bind)
            }
            else -> {
                //空评论
                val inflate = LayoutInflater.from(parent.context)
                    .inflate(R.layout.article_comment_blank_layout, parent, false)
                val bind = ArticleCommentBlankLayoutBinding.bind(inflate)
                Log.d(TAG, "onCreateViewHolder -->  ")
                return BlankViewHolder(bind)
            }
        }
    }




    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val testTypeBean = mList?.get(position)
        if (testTypeBean?.type == TYPE_PARENT_COMMENT) {
            if (holder is ParentViewHolder) {
                val parentComment = testTypeBean.parentComment
                Glide.with(holder.itemView.context)
                    .load(parentComment.avatar)
                    .into(holder.mBinding.ivArticleCommentAvter)
                holder.mBinding.tvArticleCommentHead.text = parentComment.commentContent
                holder.mBinding.tvArticleCommentName.text = parentComment.nickname
                holder.mBinding.tvArticleCommentPublishTime.text = parentComment.publishTime
            }
        } else if (testTypeBean?.type == TYPE_CHILD_COMMENT) {
            if (holder is ChildViewHolder) {
                val subComment = testTypeBean.parentComment.subComments[position]
                Glide.with(holder.itemView.context)
                    .load(subComment.yourAvatar)
                    .into(holder.mBinding.ivArticleCommentSecondAvter)
                holder.mBinding.tvArticleCommentSecondComment.text = subComment.content
                holder.mBinding.tvArticleCommentSecondName.text = subComment.yourNickname
                holder.mBinding.tvArticleCommentSecondPublishTime.text =
                    subComment.publishTime
            }
        } else {
            if (holder is BlankViewHolder) {
                holder.mBinding.tvBlankComment.text = "暂无评论..."
            }
        }

    }


    fun setHeadCommentClickListenr(listener: (articleId: String, parentId: String, beUid: String, beNickname: String) -> Unit) {
        this.mHeaderCommentListener = listener
    }

    override fun getItemCount(): Int = mList?.size ?: 0

    fun setData(contentCommom: List<Content>) {
        mCommonList.clear()
        if (contentCommom.isNotEmpty()) {
            mCommonList.addAll(contentCommom)
        }
        notifyDataSetChanged()
        Log.d(TAG, "setData -->  ")
    }

    override fun getItemViewType(position: Int): Int {
        return mData[position].type
    }

    fun setTestData(it: List<TestTypeBean>?) {
        this.mList = it
        notifyDataSetChanged()
    }


    companion object {
        const val TYPE_PARENT_COMMENT = 0
        const val TYPE_CHILD_COMMENT = 1
        const val TYPE_BLANK_COMMENT = 2
        private const val TAG = "ArticelCommentAdpater"
    }


    inner class ParentViewHolder(itemView: ArticleCommentParentLayoutBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val mBinding = itemView
    }

    inner class ChildViewHolder(itemView: ArticleCommentChildLayoutBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val mBinding = itemView
    }

    inner class BlankViewHolder(itemView: ArticleCommentBlankLayoutBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val mBinding = itemView
    }

}