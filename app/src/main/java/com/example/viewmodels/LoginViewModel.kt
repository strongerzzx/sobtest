package viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import apis.ApiServices
import base.BaseRetrofit
import beans.requestbeans.SendSmsVo
import beans.resultbeans.CheckPhoneCodeResult
import com.example.utils.MmkvUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.*

/**

 * 作者：zzx on 2021/8/7 18:19

 *  作用： xxxx
 */
class LoginViewModel : ViewModel() {

    //获取注册的手机验证码（注册）
    private val _registerLiveData = MutableLiveData<CheckPhoneCodeResult>()
    val registerLivedata:LiveData<CheckPhoneCodeResult> = _registerLiveData

    companion object {
        private const val TAG = "LoginViewModel"
    }

    fun getRandomPic(){
        viewModelScope.launch {

        }
    }

    fun getPhoneCheckCode(sendSmsVo: SendSmsVo) {
        viewModelScope.launch(Dispatchers.Main) {
             BaseRetrofit.createApisService(ApiServices::class.java)
                .getPhoneCheckCode(MmkvUtil.getString("cookie"),sendSmsVo)
                .flowOn(Dispatchers.IO)
                .collect {
                    Log.d(TAG,"check phone code --> $it  --> ${MmkvUtil.getString("cookie")}")
                }
        }

    }

}