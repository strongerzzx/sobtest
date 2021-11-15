package com.example.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beans.resultbeans.Content
import com.example.sobdemo.R
import com.example.sobdemo.databinding.ArticleCommentSingleLayoutBinding

class ArticelCommentAdpater : RecyclerView.Adapter<ArticelCommentAdpater.InnViewHolder>() {

    private val mCommonList = mutableListOf<Content>()

    private lateinit var mHeaderCommentListener:
            (articleId: String, parentId: String, beUid: String, beNickname: String) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnViewHolder {
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.article_comment_single_layout,
                parent,
                false)
        val bind = ArticleCommentSingleLayoutBinding.bind(inflate)
        return InnViewHolder(bind)
    }


    override fun onBindViewHolder(holder: InnViewHolder, position: Int) {

        //1级评论
        val parContent = mCommonList[position]
        holder.binding.tvParent.text = parContent.commentContent;
        holder.binding.tvParPublishTimer.text = parContent.publishTime;
        holder.binding.tvParentName.text = parContent.nickname
        Glide.with(holder.itemView.context)
                .load(parContent.avatar)
                .into(holder.binding.ivParAvator)


        //2级评论
        val subComments = parContent.subComments
        if (subComments.isNullOrEmpty()) {
            holder.binding.childCommentView.visibility = View.GONE
            return
        }
        holder.binding.childCommentView.visibility = View.VISIBLE
        holder.binding.childCommentView.setData(subComments, subComments.size, false)

    }


    fun setHeadCommentClickListenr(listener: (articleId: String, parentId: String, beUid: String, beNickname: String) -> Unit) {
        this.mHeaderCommentListener = listener
    }

    override fun getItemCount(): Int = mCommonList.size

    fun setData(contentCommom: List<Content>) {
        mCommonList.clear()
        if (!contentCommom.isNullOrEmpty()) {
            mCommonList.addAll(contentCommom)
        }
        notifyDataSetChanged()
    }

    inner class InnViewHolder(itemView: ArticleCommentSingleLayoutBinding) : RecyclerView.ViewHolder(itemView.root) {
        val binding = itemView
    }

}