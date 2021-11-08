package com.example.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.beans.resultbeans.Content
import com.example.sobdemo.R
import com.example.sobdemo.databinding.ArticleCommentBlankLayoutBinding
import com.example.sobdemo.databinding.ArticleCommentChildLayoutBinding
import com.example.sobdemo.databinding.ArticleCommentParentLayoutBinding

class ArticelCommentAdpater : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mCommonList = mutableListOf<Content>()
    private var mSecondPos = -1

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
        //一级评论的个数
        Log.d(TAG, "onBindViewHolder --> $position ")

/*        val content = mCommonList[position]
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

        val subComments = content.subComments
        mSecondPos = (subComments.size - 1) % subComments.size

        holder.mBinding.clSecondComment.visibility = View.VISIBLE
        holder.mBinding.clSecondComment.setOnClickListener {
            mHeaderCommentListener.invoke(
                content.subComments[0].articleId,
                content.subComments[0].parentId,
                content.subComments[0].beUid,
                content.subComments[0].beNickname
            )
        }

        holder.mBinding.clSecondComment.visibility = View.VISIBLE
        holder.mBinding.tvArticleCommentSecondComment.text = subComments[mSecondPos].content
        holder.mBinding.tvArticleCommentSecondName.text = subComments[mSecondPos].yourNickname
        holder.mBinding.tvArticleCommentSecondPublishTime.text = subComments[mSecondPos].publishTime
        Glide.with(holder.itemView.context)
            .load(subComments[mSecondPos].yourAvatar)
            .into(holder.mBinding.ivArticleCommentSecondAvter)*/
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

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
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