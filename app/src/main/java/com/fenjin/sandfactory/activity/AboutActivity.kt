package com.fenjin.sandfactory.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.fenjin.sandfactory.R
import com.fenjin.sandfactory.app.BaseActivity
import com.fenjin.sandfactory.databinding.ActivityAboutBinding
import com.fenjin.sandfactory.viewmodel.AboutViewModel
import com.qmuiteam.qmui.util.QMUIStatusBarHelper

class AboutActivity : BaseActivity() {

    private var viewModel: AboutViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        QMUIStatusBarHelper.translucent(this)
        val binding = DataBindingUtil.setContentView<ActivityAboutBinding>(this, R.layout.activity_about)
        viewModel = ViewModelProviders.of(this).get(AboutViewModel::class.java)
        binding.viewModel = viewModel
        registerObserver()
    }

    private fun registerObserver() {

        viewModel!!.finishActivity.observe(this, Observer { finish() })

    }
}
