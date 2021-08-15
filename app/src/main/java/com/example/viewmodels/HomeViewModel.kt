package com.example.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import base.BaseRetrofit
import com.example.apis.HomeApiService
import com.example.beans.resultbeans.BannearData
import com.example.beans.resultbeans.CategoryData
import com.example.beans.resultbeans.HomeTabSubBean

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    //轮播图
    private val _homeBannearLiveData = MutableLiveData<List<BannearData>>()
    val homeBannearLiveData: LiveData<List<BannearData>> = _homeBannearLiveData

    //tab数据
    private val _categoryListLiveData = MutableLiveData<List<CategoryData>>()
    val categoryListLiveData: LiveData<List<CategoryData>> = _categoryListLiveData

    //tab下的数据
    private val _tabListLiveData = MutableLiveData<HomeTabSubBean>()
    val tabListLivedata:LiveData<HomeTabSubBean> = _tabListLiveData

    //获取bannear
    fun getHomeBannearList() {
        viewModelScope.launch(Dispatchers.Main) {
            BaseRetrofit.createApisService(HomeApiService::class.java)
                .getHomeBannear()
                .flowOn(Dispatchers.IO)
                .catch {
                    Log.d(TAG, "bannear error --> ")
                }
                .collect {
                    if (it.success && it.data.isNotEmpty()) {
                        _homeBannearLiveData.value = it.data
                    }
                    Log.d(TAG, "bannear list --> $it")
                }
        }
    }

    //获取tab数据
    fun getCategoryList() {
        viewModelScope.launch(Dispatchers.Main) {
            BaseRetrofit.createApisService(HomeApiService::class.java)
                .getCategoryList()
                .flowOn(Dispatchers.IO)
                .catch {
                    Log.d(TAG, "getCategoryList --> ")
                }
                .collect {
                    _categoryListLiveData.value = it.data
                    Log.d(TAG, "getCategoryList --> $it")
                }
        }
    }


    //获取推荐内容
    fun getRecommandContent() {
        viewModelScope.launch(Dispatchers.Main) {
            BaseRetrofit.createApisService(HomeApiService::class.java)
                .getRecommend("1")
                .flowOn(Dispatchers.IO)
                .catch {
                    Log.d(TAG, "getRecommandContent --> ")
                }
                .collect {
                    Log.d(TAG, "getRecommandContent --> ${it.string()}")
                }
        }
    }

    //获取tab下的列表
    fun getHomeTabSubData(categoryId:String,page:String){
        viewModelScope.launch(Dispatchers.Main) {
            BaseRetrofit.createApisService(HomeApiService::class.java)
                    .getHomeSubTabList(categoryId,page)
                    .flowOn(Dispatchers.IO)
                    .collect {
                        _tabListLiveData.postValue(it)
                        Log.d(TAG,"getHomeTabSubData --> ${it.data.list.size}  ${it.data.list}")
                    }
        }
    }

    companion object {
        const val TAG = "HomeViewModel"
    }

}