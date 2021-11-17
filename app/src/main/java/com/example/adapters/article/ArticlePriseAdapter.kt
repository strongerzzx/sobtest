package com.example.adapters.article

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.beans.resultbeans.RichBean
import com.example.sobdemo.R
import com.example.sobdemo.databinding.ItemArticlePriseLayoutBinding

class ArticlePriseAdapter : RecyclerView.Adapter<ArticlePriseAdapter.InnverHolder>() {

    private val mList = mutableListOf<RichBean>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnverHolder {
        val inflate = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_article_prise_layout, parent, false)
        val bind = ItemArticlePriseLayoutBinding.bind(inflate)
        return InnverHolder(bind)
    }

    override fun onBindViewHolder(holder: InnverHolder, position: Int) {
        val richBean = mList[position]
        Glide.with(holder.itemView.context)
            .load(richBean.avatar)
            .apply(RequestOptions.circleCropTransform())
            .into(holder.binding.ivPriseAvator)
        holder.binding.tvPriseName.text = richBean.nickname
        holder.binding.tvPriseSob.text = richBean.sob.toString()
    }

    override fun getItemCount() = mList.size

    fun setData(data: List<RichBean>) {
        mList.clear()
        mList.addAll(data)
        notifyDataSetChanged()
    }


    inner class InnverHolder(itemView: ItemArticlePriseLayoutBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val binding = itemView
    }
}