package com.example.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apis.UserApiService
import com.example.base.BaseRetrofit
import com.example.beans.resultbeans.UserCustomAchieve
import com.example.beans.resultbeans.UserUnReadMsg
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


    companion object {
        private const val TAG = "UserViewModel"
    }
}