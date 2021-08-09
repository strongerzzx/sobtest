package com.example.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import apis.ApiServices
import base.BaseRetrofit
import beans.requestbeans.SendSmsVo
import com.example.base.BaseRet
import com.example.beans.requestbeans.UserInfo
import com.example.commonparams.CommonParms
import com.example.utils.MmkvUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.util.*



/**

 * 作者：zzx on 2021/8/7 18:19

 *  作用： xxxx
 */
class UserViewModel : ViewModel() {

    //登录
    private val _loginLiveData = MutableLiveData<BaseRet<String>>()
    val loginLiveData: LiveData<BaseRet<String>> = _loginLiveData

    //找回密码 --> 获取手机短信
    private val _getForgetPasswordPhoneCode = MutableLiveData<BaseRet<String>>()
    val getForgetPasswordPhoneCode: LiveData<BaseRet<String>> = _getForgetPasswordPhoneCode

    //短信校验结果
    private val _verifySmsCode = MutableLiveData<BaseRet<String>>()
    val verifySmsCode: LiveData<BaseRet<String>> = _verifySmsCode

    //短信找回密码
    private val _lookforForgetpassword = MutableLiveData<BaseRet<String>>()
    val lookforForgetpassword:LiveData<BaseRet<String>> = _lookforForgetpassword


    //验证码图片
    val checkPhoneCodePic = MutableLiveData<String>()


    //加载验证码图片
    fun loadCheckCodePic() {
        checkPhoneCodePic.postValue(BaseRetrofit.BASE_URL + "/uc/ut/captcha?code=${UUID.randomUUID()}")
    }

    //获取注册的手机验证码
    fun getPhoneCheckCode(sendSmsVo: SendSmsVo) {
        viewModelScope.launch(Dispatchers.Main) {
            BaseRetrofit.createApisService(ApiServices::class.java)
                .getPhoneCheckCode(MmkvUtil.getString(CommonParms.COOKIE_KEY), sendSmsVo)
                .flowOn(Dispatchers.IO)
                .collect {
                    Log.d(
                        TAG,
                        "check phone code --> $it  --> ${MmkvUtil.getString(CommonParms.COOKIE_KEY)}"
                    )
                }
        }
    }

    //根据手机获取头像
    fun getPhoneHeadPortrait(phoneNum: String) {
        viewModelScope.launch(Dispatchers.Main) {
            BaseRetrofit.createApisService(ApiServices::class.java)
                .getPhonePortrait(phoneNum)
                .flowOn(Dispatchers.IO)
                .collect {
                    if (it.success) {
                        MmkvUtil.saveString(CommonParms.LOGIN_HEAD_PORTRAIT_PIC, it.data)
                    }
                    Log.d(TAG, "phone portratit --> $it")
                }
        }
    }

    //登录
    fun doLogin(captcha: String, userInfo: UserInfo) {
        viewModelScope.launch(Dispatchers.Main) {
            BaseRetrofit.createApisService(ApiServices::class.java)
                .doLogin(MmkvUtil.getString(CommonParms.COOKIE_KEY), captcha, userInfo)
                .flowOn(Dispatchers.IO)
                .catch {
                    println("catch exception")
                }
                .collect {
                    _loginLiveData.value = it
                    Log.d(TAG, "doLogin --> $it")
                }
        }
    }

    //获取找回密码的手机验证码
    fun getForetCheckCodeByPhone(cookie: String, sendSmsVo: SendSmsVo) {
        viewModelScope.launch(Dispatchers.Main) {
            BaseRetrofit.createApisService(ApiServices::class.java)
                .getForgetPasswordPhoneYzm(cookie, sendSmsVo)
                .flowOn(Dispatchers.IO)
                .collect {
                    _getForgetPasswordPhoneCode.value = it
                    Log.d(TAG, "getForetCheckCodeByPhone --> $it")
                }
        }
    }

    //校验验证码
    fun doVerifyPhoneCode(cookie: String, phoneNum: String, smsCode: String) {
        viewModelScope.launch(Dispatchers.Main) {
            BaseRetrofit.createApisService(ApiServices::class.java)
                .doVerifyPhoneCode(cookie, phoneNum, smsCode)
                .flowOn(Dispatchers.IO)
                .catch {

                }
                .collect {
                    _verifySmsCode.value = it
                    Log.d(TAG, "doVerifyPhoneCode  -->  $it")
                }
        }
    }

    //找回密码 --> 短信找回
    fun getForgetPassword(cookie: String, smsCode: String, userInfo: UserInfo) {
        viewModelScope.launch(Dispatchers.Main) {
            BaseRetrofit.createApisService(ApiServices::class.java)
                .doForgetPassword(cookie, smsCode, userInfo)
                .flowOn(Dispatchers.IO)
                .collect {
                    _lookforForgetpassword.value = it
                    Log.d(TAG, "getForgetPassword  --> $it")
                }
        }
    }


    companion object {
        private const val TAG = "LoginViewModel"
    }

}