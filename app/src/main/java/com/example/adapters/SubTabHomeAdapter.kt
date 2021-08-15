package com.example.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beans.resultbeans.HomeSubItem
import com.example.sobdemo.R
import com.example.sobdemo.databinding.ItemSubTabHomeLayoutBinding

/**

 * 作者：zzx on 2021/8/15 13:32

 *  作用： xxxx
 */
class SubTabHomeAdapter : RecyclerView.Adapter<SubTabHomeAdapter.InnerViewHolder>() {

    private val mCurrentList = mutableListOf<HomeSubItem>()

    inner class InnerViewHolder(itemView: ItemSubTabHomeLayoutBinding) : RecyclerView.ViewHolder(itemView.root) {
        val mBinding = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.item_sub_tab_home_layout, parent, false)
        val binding= ItemSubTabHomeLayoutBinding.bind(inflate)
        return InnerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {
        val homeSubItem = mCurrentList[position]
        Glide.with(holder.itemView.context)
            .load(homeSubItem.covers[0])
            .into(holder.mBinding.ivSubTabCover)

    }

    override fun getItemCount() = mCurrentList.size

    fun setData(list: List<HomeSubItem>) {
        mCurrentList.addAll(list)
        Log.d(TAG,"sub home size --> ${mCurrentList.size}")
        notifyDataSetChanged()

    }

    companion object{
        private const val TAG = "SubTabHomeAdapter"
    }
}