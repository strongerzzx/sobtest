package com.example.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apis.UserApiService
import com.example.base.BaseRet
import com.example.base.BaseRetrofit
import com.example.beans.resultbeans.RichBean
import com.example.beans.resultbeans.RichListBean
import com.example.beans.resultbeans.UserCustomAchieve
import com.example.beans.resultbeans.UserUnReadMsg
import com.example.sobdemo.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    //获取未读信息
    private val _unUserReadMsg = MutableLiveData<UserUnReadMsg>()
    val unUserReadMsg: LiveData<UserUnReadMsg> = _unUserReadMsg

    //个人成就
    private val _userAcievementLivedata = MutableLiveData<UserCustomAchieve>()
    val userAcievementLivedata: LiveData<UserCustomAchieve> = _userAcievementLivedata


    private val _richListLivedata = MutableLiveData<RichListBean>()
    val richListLivedata: LiveData<RichListBean> = _richListLivedata


    //个人成就
    fun getUserAchievement() {
        viewModelScope.launch(Dispatchers.Main) {
            BaseRetrofit.createApisService(UserApiService::class.java)
                .getUserAchievement()
                .catch {
                    Log.d(TAG, "getUserAchievement error --> $")
                }
                .flowOn(Dispatchers.IO)
                .collect {
                    _userAcievementLivedata.value = it
                    Log.d(TAG, "getUserAchievement --> $it")
                }
        }
    }


    //未获未读信息
    fun getUnReadMessage() {
        viewModelScope.launch(Dispatchers.Main) {
            BaseRetrofit.createApisService(UserApiService::class.java)
                .getUserUnReadMsg()
                .catch {
                    Log.d(TAG, "getUnReadMessage error --> ")
                }
                .flowOn(Dispatchers.IO)
                .collect {
                    _unUserReadMsg.value = it
                    Log.d(TAG, "getUnReadMessage  --> $it")
                }
        }
    }

    //获取富豪榜
    fun getRichList(count: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            BaseRetrofit.createApisService(UserApiService::class.java)
                .getRichList(count)
                .catch {
                    Log.d(TAG, "getRichList error --> ")
                }
                .flowOn(Dispatchers.IO)
                .collect {
                    if (it.success) {
                        var num = 0
                        for (datum in it.data) {
                            //设置排名
                            datum.rank = ++num
                            when (num) {
                                1 -> {
                                    datum.rankIcon = R.mipmap.ic_rank_1
                                }
                                2 -> {
                                    datum.rankIcon = R.mipmap.ic_ran_2
                                }
                                3 -> {
                                    datum.rankIcon = R.mipmap.ic_rank_3
                                }
                                else -> {
                                    datum.rankIcon = 0
                                }
                            }
                        }
                        _richListLivedata.value = it
                    }
                }
        }
    }


    companion object {
        private const val TAG = "UserViewModel"
    }
}