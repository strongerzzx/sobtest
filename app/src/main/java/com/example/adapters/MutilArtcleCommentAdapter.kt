package com.example.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.beans.resultbeans.Content
import com.example.sobdemo.R
import com.example.sobdemo.databinding.ItemMutilAreticleCommentChildLayoutBinding
import com.example.sobdemo.databinding.ItemMutilAreticleCommentParentLayoutBinding

public class MutilArtcleCommentAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mData = mutableListOf<Content>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (getItemViewType(viewType)) {
            TYPE_PAR_COMMENT -> {
                val inflate = LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_mutil_areticle_comment_parent_layout, parent, false)
                val parBind = ItemMutilAreticleCommentParentLayoutBinding.bind(inflate)
                return PareViewHolder(parBind)
            }
            TYPE_CHILD_COMMENT -> {
                val inflate = LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_mutil_areticle_comment_child_layout, parent, false)
                val childBind = ItemMutilAreticleCommentChildLayoutBinding.bind(inflate)
                return ChildViewHolder(childBind)
            }

            else -> {

            }
        }
        return super.createViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PareViewHolder -> {
                val content = mData[position]
                holder.parBinding.tvMutilParComment.text = content.commentContent
            }

            is ChildViewHolder -> {
                val subComment = mData[position].subComments[position]
                holder.childBinding.tvMutilChildCoomemt.text = subComment.content
            }
            else -> {
            }
        }
    }

    override fun getItemCount() = mData.size

    override fun getItemViewType(position: Int): Int {
        when (position) {
            0 -> {
                return TYPE_PAR_COMMENT
            }
            1 -> {
                return TYPE_CHILD_COMMENT
            }
            else -> {
            }
        }
        return super.getItemViewType(position)
    }

    fun setData(contentCommom: List<Content>) {
        mData.clear()
        mData.addAll(contentCommom)
        notifyDataSetChanged()
    }

    class PareViewHolder(itemView: ItemMutilAreticleCommentParentLayoutBinding) :
            RecyclerView.ViewHolder(itemView.root) {
        val parBinding = itemView
    }

    class ChildViewHolder(itemView: ItemMutilAreticleCommentChildLayoutBinding) :
            RecyclerView.ViewHolder(itemView.root) {
        val childBinding = itemView
    }

    companion object {
        private const val TYPE_PAR_COMMENT = 0
        private const val TYPE_CHILD_COMMENT = 1
    }
}