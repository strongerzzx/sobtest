package apis

import beans.requestbeans.SendSmsVo
import beans.resultbeans.CheckPhoneCodeResult
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import retrofit2.Response

import retrofit2.http.*

/**

 * 作者：zzx on 2021/8/7 18:58

 *  作用： xxxx
 */
interface ApiServices {

    @GET("/uc/ut/captcha?")
    suspend fun getCapchaPic(@Query("code") random:Long):ResponseBody

    @POST("/uc/ut/join/send-sms")
     fun getPhoneCheckCode(@Body sendSmsVo: SendSmsVo): Flow<CheckPhoneCodeResult>

}