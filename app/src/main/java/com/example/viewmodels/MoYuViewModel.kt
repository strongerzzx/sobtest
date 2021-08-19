package com.example.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apis.MoYuApiService
import com.example.base.BaseRetrofit
import com.example.beans.resultbeans.MoYuHotBean
import com.example.parsefactory.MoYuHotParse
import com.example.sobdemo.BuildConfig
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


class MoYuViewModel : ViewModel() {

    //摸鱼热门动态列表
    private val _moYouHotList = MutableLiveData<MoYuHotBean>()
    val moYouHotListLiveData: LiveData<MoYuHotBean> = _moYouHotList

    //获取热门动态列表
    fun getHotMoYuList(size: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            BaseRetrofit.createApisService(MoYuApiService::class.java)
                .getMoYuHotList(size)
                .map {
                    val source = it.string()
                    if (BuildConfig.DEBUG) {
                        Log.d(TAG, "getHotMoYuList onResponse -->  $source");
                    }
                    MoYuHotParse.parseMoYuData(source)
                }
                .catch {
                    Log.d(TAG, "getHotMoYuList error -->")
                }
                .flowOn(Dispatchers.IO)
                .collect {
                    _moYouHotList.value = it
                    Log.d(TAG, "getHotMoYuList  --> $it")
                }
        }

    }


    companion object {
        private const val TAG = "MoYuViewModel"
    }

}