package com.example.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beans.resultbeans.Content
import com.example.sobdemo.R
import com.example.sobdemo.databinding.ArticleCommentAdapterLayoutBinding

class ArticelCommentAdpater : RecyclerView.Adapter<ArticelCommentAdpater.InnerViewHolder>() {

    private val mCommonList = mutableListOf<Content>()
    private var mSecondPos = -1

    private lateinit var mHeaderCommentListener:
                (articleId: String, parentId: String, beUid: String, beNickname: String) -> Unit

    inner class InnerViewHolder(itemView: ArticleCommentAdapterLayoutBinding) :
        RecyclerView.ViewHolder(itemView.root) {

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
        //一级评论的个数
        Log.d(TAG, "onBindViewHolder --> $position ")

        val content = mCommonList[position]
        Glide.with(holder.itemView.context)
            .load(content.avatar)
            .into(holder.mBinding.ivArticleCommentAvter)
        holder.mBinding.tvArticleCommentHead.text = content.commentContent
        holder.mBinding.tvArticleCommentName.text = content.nickname
        holder.mBinding.tvArticleCommentPublishTime.text = content.publishTime

        //二级评论
        if (content.subComments.isEmpty()) {
            holder.mBinding.clSecondComment.visibility = View.GONE
            return
        } else {
            holder.mBinding.clSecondComment.visibility = View.VISIBLE
        }

        holder.mBinding.clSecondComment.setOnClickListener {
            mHeaderCommentListener.invoke(
                content.subComments[mSecondPos].articleId,
                content.subComments[mSecondPos].parentId,
                content.subComments[mSecondPos].beUid,
                content.subComments[mSecondPos].beNickname
            )
        }

        val subComments = content.subComments
        mSecondPos = (subComments.size - 1) % subComments.size

        holder.mBinding.clSecondComment.visibility = View.VISIBLE
        holder.mBinding.tvArticleCommentSecondComment.text = subComments[mSecondPos].content
        holder.mBinding.tvArticleCommentSecondName.text = subComments[mSecondPos].yourNickname
        holder.mBinding.tvArticleCommentSecondPublishTime.text = subComments[mSecondPos].publishTime
        Glide.with(holder.itemView.context)
            .load(subComments[mSecondPos].yourAvatar)
            .into(holder.mBinding.ivArticleCommentSecondAvter)
    }

    override fun getItemViewType(position: Int): Int {
        Log.d(TAG, "getItemViewType -->  ")
        if (position < mCommonList.size) {
            //加载一级？？
        }

        //TODO:一开始最多 只显示3个
        //TODO:如果超过3个 显示更多按钮


        return super.getItemViewType(position)
    }


    fun setHeadCommentClickListenr(listener: (articleId: String, parentId: String, beUid: String, beNickname: String) -> Unit) {
        this.mHeaderCommentListener = listener
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

        const val TYPE_HEAD_COMMENT = 0
        const val TYPE_SECOND_COMMENT = 1
        private const val TAG = "ArticelCommentAdpater"
    }

}