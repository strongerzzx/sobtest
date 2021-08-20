package com.example.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import apis.ApiUserServices
import com.example.base.BaseRetrofit
import beans.requestbeans.SendSmsVo
import com.example.base.BaseRet
import com.example.beans.requestbeans.LoginInfo
import com.example.beans.userinfo.UserBasicInfo
import com.example.beans.userinfo.UserInfo
import com.example.commonparams.CommonParms
import com.example.utils.MmkvUtil
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*

/**

 * 作者：zzx on 2021/8/7 18:19

 *  作用： xxxx
 */
class LoginViewModel : ViewModel() {

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
    val lookforForgetpassword: LiveData<BaseRet<String>> = _lookforForgetpassword

    //checkToken
    private val _checkTokenLiveData = MutableLiveData<BaseRet<UserBasicInfo>>()
    val checkTokenLiveData: LiveData<BaseRet<UserBasicInfo>> = _checkTokenLiveData

    //验证码图片
    val checkPhoneCodePic = MutableLiveData<String>()

    //加载验证码图片
    fun loadCheckCodePic() {
        checkPhoneCodePic.postValue(BaseRetrofit.BASE_URL + "/uc/ut/captcha?code=${UUID.randomUUID()}")
    }

    //获取注册的手机验证码
    fun getPhoneCheckCode(sendSmsVo: SendSmsVo) {
        viewModelScope.launch(Dispatchers.Main) {
            BaseRetrofit.createApisService(ApiUserServices::class.java)
                .getPhoneCheckCode(MmkvUtil.getString(CommonParms.COOKIE_PIC_KEY), sendSmsVo)
                .flowOn(Dispatchers.IO)
                .collect {
                    Log.d(
                        TAG,
                        "check phone code --> $it  --> ${MmkvUtil.getString(CommonParms.COOKIE_PIC_KEY)}"
                    )
                }
        }
    }

    //根据手机获取头像
    fun getPhoneHeadPortrait(phoneNum: String) {
        viewModelScope.launch(Dispatchers.Main) {
            BaseRetrofit.createApisService(ApiUserServices::class.java)
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
    fun doLogin(captcha: String, loginInfo: LoginInfo) {
        viewModelScope.launch(Dispatchers.Main) {
            Log.d(TAG,"pic cookie --> ${MmkvUtil.getString(CommonParms.COOKIE_PIC_KEY)}")
            BaseRetrofit.createApisService(ApiUserServices::class.java)
                .doLogin(MmkvUtil.getString(CommonParms.COOKIE_PIC_KEY),captcha, loginInfo)
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

    //登录成功后 --> 检测token --> 获取userid
    fun doCheckToken() {
        viewModelScope.launch(Dispatchers.Main) {
            BaseRetrofit.createApisService(ApiUserServices::class.java)
                .checkToken(MmkvUtil.getString(CommonParms.COOKIE_LOGIN_KEY))
                .flowOn(Dispatchers.IO)
                .collect {
                    _checkTokenLiveData.value = it
                    Log.d(TAG, "doCheckToken  -->  $it")
                }
        }
    }

    fun getUserInfo(userId: String) {
        viewModelScope.launch(Dispatchers.Main) {
            BaseRetrofit.createApisService(ApiUserServices::class.java)
                .doGetUserInfo(userId)
                .flowOn(Dispatchers.IO)
                .collect {
                    saveUserInfo(it)
                    Log.d(TAG, "getUserInfo  -->  $it")
                }
        }
    }


    //获取找回密码的手机验证码
    fun getForetCheckCodeByPhone(cookie: String, sendSmsVo: SendSmsVo) {
        viewModelScope.launch(Dispatchers.Main) {
            BaseRetrofit.createApisService(ApiUserServices::class.java)
                .getForgetPasswordPhoneYzm(sendSmsVo)
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
            BaseRetrofit.createApisService(ApiUserServices::class.java)
                .doVerifyPhoneCode(phoneNum, smsCode)
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
    fun getForgetPassword(cookie: String, smsCode: String, loginInfo: LoginInfo) {
        viewModelScope.launch(Dispatchers.Main) {
            BaseRetrofit.createApisService(ApiUserServices::class.java)
                .doForgetPassword(smsCode, loginInfo)//cookie,
                .flowOn(Dispatchers.IO)
                .collect {
                    _lookforForgetpassword.value = it
                    Log.d(TAG, "getForgetPassword  --> $it")
                }
        }
    }

    private fun saveUserInfo(userInfo: UserInfo) {
        val gson = Gson()
        val localUserInfo = gson.toJson(userInfo)
        MmkvUtil.saveString(CommonParms.BASIC_USER_INFO, localUserInfo)
    }

    companion object {
        private const val TAG = "UserViewModel"
    }

}