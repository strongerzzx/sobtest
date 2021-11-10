package com.example.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beans.resultbeans.ArticleCommenBean
import com.example.beans.resultbeans.Content
import com.example.beans.resultbeans.TestTypeBean
import com.example.sobdemo.R
import com.example.sobdemo.databinding.TestBlankReplyLayoutBinding
import com.example.sobdemo.databinding.TestMultiyReplyLayoutBinding
import com.example.sobdemo.databinding.TestSignleReplyLayoutBinding

class TestArticleAdpter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val mData = mutableListOf<TestTypeBean>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            TYPE_SINGLE_REPLAY -> {
                val inflate = LayoutInflater.from(parent.context)
                    .inflate(R.layout.test_signle_reply_layout, parent, false)
                val bind = TestSignleReplyLayoutBinding.bind(inflate)
                return SingleReplyViewHolder(bind)
            }
            TYPE_MULTIYPLE_REPLAY -> {
                val inflate = LayoutInflater.from(parent.context)
                    .inflate(R.layout.test_multiy_reply_layout, parent, false)
                val bind = TestMultiyReplyLayoutBinding.bind(inflate)
                return MultiyReplyViewHolder(bind)
            }

            else -> {
                val inflate = LayoutInflater.from(parent.context)
                    .inflate(R.layout.test_blank_reply_layout, parent, false)
                val bind = TestBlankReplyLayoutBinding.bind(inflate)
                return BlankViewHolder(bind)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val articleCommenBean = mData[position]
        val content = articleCommenBean.parentComment

        when (holder) {
            is SingleReplyViewHolder -> {
                holder.binding.tvArticleCommentPublishTime.text = content.publishTime
                holder.binding.tvArticleCommentName.text = content.nickname
                holder.binding.tvArticleCommentHead.text = content.commentContent
                Glide.with(holder.itemView.context)
                    .load(content.avatar)
                    .into(holder.binding.ivArticleCommentAvter)

            }
            is MultiyReplyViewHolder -> {
                holder.binding.tvArticleCommentPublishTime.text = content.publishTime
                holder.binding.tvArticleCommentName.text = content.nickname
                holder.binding.tvArticleCommentHead.text = content.commentContent
                Glide.with(holder.itemView.context)
                    .load(content.avatar)
                    .into(holder.binding.ivArticleCommentAvter)

                //TODO:父评论在1  --> 实际上子评论在0
                val subComment = content.subComments[position]
                holder.binding.tvArticleCommentSecondPublishTime.text = subComment.publishTime
                holder.binding.tvArticleCommentSecondName.text = subComment.beNickname
                holder.binding.tvArticleCommentSecondComment.text = subComment.content
                Glide.with(holder.itemView.context)
                    .load(subComment.yourAvatar)
                    .into(holder.binding.ivArticleCommentSecondAvter)

            }
            else -> {
            }
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }


    override fun getItemViewType(position: Int): Int {
        return mData[position].type
    }

    fun setData(contentCommom: List<TestTypeBean>) {
        mData.clear()
        mData.addAll(contentCommom)
    }


    companion object {
        const val TYPE_SINGLE_REPLAY = 0 //没留言回复的
        const val TYPE_MULTIYPLE_REPLAY = 1 //留言中含有回复的
    }

    inner class SingleReplyViewHolder(itemView: TestSignleReplyLayoutBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val binding = itemView
    }

    inner class MultiyReplyViewHolder(itemView: TestMultiyReplyLayoutBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val binding = itemView
    }

    inner class BlankViewHolder(itemView: TestBlankReplyLayoutBinding) :
        RecyclerView.ViewHolder(itemView.root) {

    }


}