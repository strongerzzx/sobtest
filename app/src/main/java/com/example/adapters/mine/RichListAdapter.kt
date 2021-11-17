package com.example.adapters.mine

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.beans.resultbeans.RichBean
import com.example.sobdemo.R
import com.example.sobdemo.databinding.ItemRichListLayoutBinding
import com.example.utils.CircleBroaderTranslate

class RichListAdapter : RecyclerView.Adapter<RichListAdapter.InnerViewHolder>() {

    private val mRichList = mutableListOf<RichBean>()

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): InnerViewHolder {
        val inflate = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_rich_list_layout, parent, false)

        val bind = ItemRichListLayoutBinding.bind(inflate)

        return InnerViewHolder(bind)
    }

    override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {
        val richBean = mRichList[position]

        holder.mBinding.tvRichVip.visibility = if (richBean.vip) {
            View.VISIBLE
        } else {
            View.GONE
        }
        holder.mBinding.tvRichRank.text = richBean.rank.toString()
        holder.mBinding.tvRichPickNum.text = richBean.sob.toString()
        holder.mBinding.tvRichName.text = richBean.nickname

        Glide.with(holder.itemView.context)
                .load(richBean.rankIcon)
                .into(holder.mBinding.ivRankIcon)

        val options = RequestOptions()
                .transform(CircleBroaderTranslate(4, Color.RED))
        Glide.with(holder.itemView.context)
                .load(richBean.avatar)
//                .apply(options)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.mBinding.ivRichAvater)
    }

    override fun getItemCount() = mRichList.size

    fun setData(data: List<RichBean>) {
        mRichList.clear()
        mRichList.addAll(data)
        notifyDataSetChanged()
    }


    inner class InnerViewHolder(itemView: ItemRichListLayoutBinding) :
            RecyclerView.ViewHolder(itemView.root) {
        val mBinding = itemView
    }

}