package app

import android.app.Application
import android.content.Context

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
    }
}