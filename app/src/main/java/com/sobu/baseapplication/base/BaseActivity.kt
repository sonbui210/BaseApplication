package com.sobu.baseapplication.base

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelLazy
import androidx.viewbinding.ViewBinding
import com.google.gson.Gson
import com.sobu.baseapplication.BuildConfig
import com.sobu.baseapplication.base.viewmodel.BaseViewModel
import com.sobu.baseapplication.commons.Constants
import com.sobu.baseapplication.commons.extensions.Extensions.goBackPressed
import com.sobu.baseapplication.commons.firebase.FirebaseUtils.recordException
import com.sobu.baseapplication.commons.koin.DIComponent
import com.sobu.baseapplication.commons.utils.LocaleHelper
import com.sobu.baseapplication.utils.hideStatusBar
import kotlin.reflect.KClass

abstract class BaseActivity<viewModel : BaseViewModel, viewBinding : ViewBinding>(
    viewModelClass: KClass<viewModel>,
    private val shouldActivityBackPress: Boolean = false
) : AppCompatActivity() {

    private val baseTAG = "BaseTAG"

    lateinit var timeRunnable: Runnable

    protected val diComponent by lazy { DIComponent() }

    protected val viewModel by ViewModelLazy(
        viewModelClass,
        { viewModelStore },
        { defaultViewModelProviderFactory }
    )

    protected lateinit var viewBinding: viewBinding
    abstract fun inflateViewBinding(inflater: LayoutInflater): viewBinding


//    protected val admobBannerAds by lazy { AdmobBannerAds() }
//    protected val admobNativeAds by lazy { AdmobNativeAds() }

    lateinit var gson: Gson
    lateinit var sharedPeref: SharedPreferences
    lateinit var sharedPerefEditor: SharedPreferences.Editor

    // Create a Handler associated with the current Looper
    var handler = Handler(Looper.myLooper() ?: Looper.getMainLooper())

    protected abstract fun initialize()
    protected abstract fun initLoadAds()
    open fun onRecreate() {}
    open fun onBack() {}

    private fun registerBackPressDispatcher() {
        goBackPressed {
            onBack()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        registerBackPressDispatcher()
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(
            LocaleHelper.setLocale(
                newBase,
                diComponent.sharedPreferenceUtils.selectedLanguageCode
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = inflateViewBinding(layoutInflater)
        hideStatusBar()
        setContentView(viewBinding.root)
        initApp()
        initialize()
        initLoadAds()
        onSubscribeObserver()

        if (!shouldActivityBackPress) {
            registerBackPressDispatcher()
        }

    }

    private fun initApp() {
        gson = Gson()
        sharedPeref = this.getSharedPreferences(
            Constants.nameDataSaveApp, Context.MODE_PRIVATE
        )
        sharedPerefEditor = sharedPeref.edit()

        onSetPremium()
    }

    private fun onSetPremium() {
    }

    fun showLoading(isShow: Boolean) {

    }

    open fun onSubscribeObserver() {
        viewModel.run {

        }
    }

    fun handleApiError(throwable: Throwable) {

    }

    protected fun showToast(message: String) {
        try {
            runOnUiThread {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        } catch (ex: Exception) {
            ex.recordException("showToast : ${javaClass.simpleName}")
        }
    }

    protected fun debugToast(message: String) {
        if (BuildConfig.DEBUG) {
            showToast(message)
        }
    }

    protected fun withDelay(delay: Long = 300, block: () -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed(block, delay)
    }

    protected fun showKeyboard() {
        try {
            val imm: InputMethodManager? =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        } catch (ex: Exception) {
            ex.recordException("showKeyBoardTag")
        }
    }

    protected fun hideKeyboard() {
        try {
            val inputMethodManager: InputMethodManager =
                getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            val view: IBinder? = findViewById<View?>(android.R.id.content)?.windowToken
            inputMethodManager.hideSoftInputFromWindow(view, 0)
        } catch (ex: Exception) {
            ex.recordException("hideKeyboard")
        }
    }


}