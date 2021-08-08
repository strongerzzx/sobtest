package base

import com.chenxyu.retrofit.adapter.BuildConfig
import com.chenxyu.retrofit.adapter.FlowCallAdapterFactory
import com.example.manager.glideconfig.CookiesManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**

 * 作者：zzx on 2021/8/7 19:03

 *  作用： xxxx
 */
object BaseRetrofit {

    const val BASE_URL = "https://api.sunofbeach.net"

    val cookieJar by lazy {
        CookiesManager()
    }

    fun getOkHttpClient(): OkHttpClient {
        val builder: OkHttpClient.Builder = OkHttpClient.Builder()
            .cookieJar(cookieJar)
            .readTimeout(60, TimeUnit.SECONDS) //设置读取超时时间
            .writeTimeout(60, TimeUnit.SECONDS) //设置写的超时时间
            .connectTimeout(60, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            builder.addInterceptor(httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            })
        }
        val client = builder.build()
        return client
    }

    fun getRetrofit(BASE_URL: String) = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(FlowCallAdapterFactory.create())
        .build()

    fun <T> createApisService(ApiService: Class<T>) = getRetrofit(BASE_URL).create(ApiService)

}