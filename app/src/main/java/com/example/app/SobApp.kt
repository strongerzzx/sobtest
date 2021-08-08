package app

import android.app.Application
import android.content.Context
import android.os.Build
import com.tencent.mmkv.MMKV

/**

 * 作者：zzx on 2021/8/8 17:19

 *  作用： xxxx
 */
class SobApp :Application() {

    companion object{
        lateinit var sContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        sContext = applicationContext

        //context.getFilesDir().getAbsolutePath() + "/mmkv"
        MMKV.initialize(this)
    }
}