package base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.example.sobdemo.R

abstract class BaseActivity<VM : ViewModel> : AppCompatActivity() {

    protected lateinit var mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        initViewModel()

    }



    private fun initViewModel() {
        mViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(getSubViewModel())
    }

    abstract fun getSubViewModel(): Class<VM>


}