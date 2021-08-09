package base

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.example.sobdemo.R
import com.gyf.immersionbar.ImmersionBar

abstract class BaseActivity<VM : ViewModel> : AppCompatActivity() {

    protected lateinit var mViewModel: VM
    private lateinit var mImmersionBar: ImmersionBar

    companion object {
        private const val TAG = "BaseActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        initViewModel()

        createStatusBarConfig()
    }

    protected fun getImmersionBar(): ImmersionBar {
        mImmersionBar = createStatusBarConfig()
        return mImmersionBar
    }

    open fun createStatusBarConfig(): ImmersionBar {
        return ImmersionBar
            .with(this)
            .statusBarDarkFont(isDarkBarFont())
            .navigationBarColor(R.color.white)
            .fitsSystemWindows(true) //加了整个属性 --> 防止状态栏和内容叠加
    }


    private fun initViewModel() {
        mViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(getSubViewModel())
    }

    abstract fun getSubViewModel(): Class<VM>

    abstract fun isDarkBarFont(): Boolean


    protected fun <T> readyGoFinish(clazz: Class<T>) {
        val intent = Intent(this, clazz)
        startActivity(intent)
        finish()
    }

    protected fun <T> readyGo(clazz: Class<T>) {
        val intent = Intent(this, clazz)
        startActivity(intent)
    }

    protected fun <T> readyGoBundle(clazz: Class<T>, bundle: Bundle) {
        val intent = Intent(this, clazz)
        intent.putExtras(bundle)
        startActivity(intent)
    }


    override fun onDestroy() {
        super.onDestroy()

    }

}