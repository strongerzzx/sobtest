package com.example.apis

import com.example.base.BaseRet
import com.example.beans.resultbeans.MoYuHotBean
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path

interface MoYuApiService {

    @GET("/ct/moyu/hot/{size}")
    fun getMoYuHotList(@Path("size") size: Int): Flow<ResponseBody>

}