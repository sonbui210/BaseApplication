package com.sobu.baseapplication.ui.activities

import android.view.LayoutInflater
import com.sobu.baseapplication.base.BaseActivity
import com.sobu.baseapplication.databinding.ActivityMainBinding
import com.sobu.baseapplication.ui.viewmodels.MainViewModel

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(MainViewModel::class) {
    override fun inflateViewBinding(inflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(inflater)
    }

    override fun initialize() {

    }

    override fun initLoadAds() {

    }

}