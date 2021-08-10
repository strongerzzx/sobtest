package com.example.adapters

import android.content.Context
import android.graphics.Color
import androidx.viewpager2.widget.ViewPager2
import com.example.beans.resultbeans.CategoryData
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView




class MagicIndicatorAdapter: CommonNavigatorAdapter() {

    private val mTitleList = mutableListOf<String>()
    private lateinit var mViewPage2:ViewPager2

    override fun getCount(): Int {
        return mTitleList.size
    }

    override fun getTitleView(context: Context?, index: Int): IPagerTitleView {
        val colorTransitionPagerTitleView = ColorTransitionPagerTitleView(context)
        colorTransitionPagerTitleView.normalColor = Color.parseColor("#99000000")
        colorTransitionPagerTitleView.selectedColor = Color.parseColor("#FE2941")
        colorTransitionPagerTitleView.text = mTitleList[index]

        colorTransitionPagerTitleView.setOnClickListener {
            mViewPage2.currentItem = index
        }

        return colorTransitionPagerTitleView
    }

    override fun getIndicator(context: Context?): IPagerIndicator {
        val indicator = LinePagerIndicator(context)
        indicator.mode = LinePagerIndicator.MODE_WRAP_CONTENT
        return indicator
    }

    fun setData(it: List<CategoryData>?, viewPage2: ViewPager2) {
        mViewPage2 = viewPage2
        it?.let {
            it.forEach { data ->
                mTitleList.add(data.categoryName)
            }
        }
    }
}