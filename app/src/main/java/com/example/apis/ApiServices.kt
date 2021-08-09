package apis

import beans.requestbeans.SendSmsVo
import com.example.base.BaseRet
import com.example.beans.UserInfo
import com.example.beans.requestbeans.LoginInfo
import kotlinx.coroutines.flow.Flow

import retrofit2.http.*

/**

 * 作者：zzx on 2021/8/7 18:58

 *  作用： xxxx
 */
interface ApiServices {

    //获取注册的手机验证码
    @POST("/uc/ut/join/send-sms")
    fun getPhoneCheckCode(
        @Header("cookie") cookie: String, @Body sendSmsVo: SendSmsVo
    ): Flow<BaseRet<String>>


    //通过手机号码获取头像
    @GET("/uc/user/avatar/{phoneNum}")
    fun getPhonePortrait(@Path("phoneNum") phoneNum: String): Flow<BaseRet<String>>


    //登录
    @POST("/uc/user/login/{captcha}")
    fun doLogin(
        @Header("cookie") cookie: String, @Path("captcha") captcha: String, @Body loginInfo: LoginInfo
    ): Flow<BaseRet<String>>


    //登录成功 --> 解析Token --> 获取userid
    @GET("/uc/user/checkToken")
    fun checkToken():Flow<BaseRet<UserInfo>>



    //获取找回密码的手机验证码（找回密码）
    @POST("/uc/ut/forget/send-sms")
    fun getForgetPasswordPhoneYzm(
        @Header("cookie") cookie: String, @Body sendSmsVo: SendSmsVo
    ): Flow<BaseRet<String>>


    //找回密码
    @PUT("/uc/user/forget/{smsCode}")
    fun doForgetPassword(
        @Header("cookie") cookie: String, @Path("smsCode") smsCode: String, @Body loginInfo: LoginInfo
    ): Flow<BaseRet<String>>


    //检查手机验证码是否正确
    @GET("/uc/ut/check-sms-code/{phoneNumber}/{smsCode}")
    fun doVerifyPhoneCode(
        @Header("cookie") cookie: String,
        @Path("phoneNumber") phoneNumber: String,
        @Path("smsCode") smsCode: String,
    ): Flow<BaseRet<String>>


}