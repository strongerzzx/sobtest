package com.example.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beans.resultbeans.MoYuHotData
import com.example.sobdemo.R
import com.example.sobdemo.databinding.ItemMoYuHotLayoutBinding

class MoYuHotAdapter : RecyclerView.Adapter<MoYuHotAdapter.InnverViewHolder>() {

    private val mMoYuHotList = mutableListOf<MoYuHotData>()


    inner class InnverViewHolder(itemView: ItemMoYuHotLayoutBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val binding = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnverViewHolder {
        val inflate = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_mo_yu_hot_layout, parent, false)

        val binding = ItemMoYuHotLayoutBinding.bind(inflate)
        return InnverViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InnverViewHolder, position: Int) {
        val moYuHotData = mMoYuHotList[position]
        Glide.with(holder.itemView.context)
            .load(moYuHotData.avatar)
            .fallback(R.mipmap.ic_default_avatar)//url为空的时候
            .into(holder.binding.ivMoYuHotAvter)

        if (moYuHotData.images.isNotEmpty()) {
            holder.binding.ivMoYuHotCommentContent.visibility = View.VISIBLE
            Glide.with(holder.itemView.context)
                .load(moYuHotData.images[0])
                .into(holder.binding.ivMoYuHotCommentContent)
        }

        holder.binding.tvMoYuHotPublishTime.text = moYuHotData.createTime
        holder.binding.tvMoYuHotName.text = moYuHotData.nickname
        holder.binding.tvMoYuHotCommentContent.text = moYuHotData.content
        holder.binding.tvMoYuHotDianZan.text = moYuHotData.thumbUpCount.toString()
        holder.binding.tvMoYuHotCommentCount.text = moYuHotData.commentCount.toString()


        //TODO:点击后评论
//        holder.binding.tvMoYuInputHotComment

    }

    override fun getItemCount() = mMoYuHotList.size

    fun setData(data: List<MoYuHotData>) {
        mMoYuHotList.clear()
        mMoYuHotList.addAll(data)
        notifyDataSetChanged()
    }
}