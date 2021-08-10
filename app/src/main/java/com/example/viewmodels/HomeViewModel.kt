package com.example.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import apis.ApiServices
import base.BaseRetrofit
import com.example.beans.userinfo.UserBasicInfo
import com.example.commonparams.CommonParms
import com.example.utils.MmkvUtil
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {


    companion object {
        const val TAG = "HomeViewModel"
    }
}