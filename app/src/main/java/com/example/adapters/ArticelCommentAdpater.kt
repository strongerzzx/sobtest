package com.example.adapters

import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.beans.resultbeans.Content
import com.example.sobdemo.R
import com.example.sobdemo.databinding.ArticleCommentAdapterLayoutBinding

class ArticelCommentAdpater : RecyclerView.Adapter<ArticelCommentAdpater.InnerViewHolder>() {

    private val mCommonList = mutableListOf<Content>()
    private var mSecondPos = -1

    inner class InnerViewHolder(itemView: ArticleCommentAdapterLayoutBinding) : RecyclerView.ViewHolder(itemView.root) {

        val mBinding = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {
        val inflate = LayoutInflater.from(parent.context)
                .inflate(R.layout.article_comment_adapter_layout, parent, false)
        val bind = ArticleCommentAdapterLayoutBinding.bind(inflate)

        Log.d(TAG, "onCreateViewHolder -->  ")

        return InnerViewHolder(bind)
    }


    override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {
        val content = mCommonList[position]
        Glide.with(holder.itemView.context)
                .load(content.avatar)
                .into(holder.mBinding.ivArticleCommentAvter)
        holder.mBinding.tvArticleCommentHead.text = content.commentContent
        holder.mBinding.tvArticleCommentName.text = content.nickname
        holder.mBinding.tvArticleCommentPublishTime.text = content.publishTime


        //二级评论
        if (content.subComments.isEmpty()) {
            return
        }
        mSecondPos = if (content.subComments.size == mCommonList.size) {
            position
        } else {
            (content.subComments.size % mCommonList.size) - 1
        }

        if (mSecondPos == mCommonList.size) {
            return
        }

        setSecondVisible(holder)

        val subComments = content.subComments
        holder.mBinding.tvArticleCommentSecondComment.text = subComments[mSecondPos].content
        holder.mBinding.tvArticleCommentSecondName.text = subComments[mSecondPos].yourNickname
        holder.mBinding.tvArticleCommentSecondPublishTime.text = subComments[mSecondPos].publishTime
        Glide.with(holder.itemView.context)
                .load(subComments[mSecondPos].yourAvatar)
                .into(holder.mBinding.ivArticleCommentSecondAvter)


        Log.d(TAG, "onBindViewHolder -->  ")
    }

    private fun setSecondVisible(holder: InnerViewHolder) {
        holder.mBinding.tvArticleCommentSecondComment.visibility = View.VISIBLE
        holder.mBinding.tvArticleCommentSecondName.visibility = View.VISIBLE
        holder.mBinding.tvArticleCommentSecondPublishTime.visibility = View.VISIBLE
        holder.mBinding.ivArticleCommentSecondAvter.visibility = View.VISIBLE
    }

    override fun getItemCount() = mCommonList.size

    fun setData(contentCommom: List<Content>) {
        mCommonList.clear()
        if (contentCommom.isNotEmpty()) {
            mCommonList.addAll(contentCommom)
        }
        notifyDataSetChanged()
        Log.d(TAG, "setData -->  ")

    }

    companion object {
        private const val TAG = "ArticelCommentAdpater"
    }

}