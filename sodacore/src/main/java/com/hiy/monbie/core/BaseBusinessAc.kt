package com.hiy.monbie.core

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

/**
 * auther: liusaideng
 * created on :  2022/11/24 3:11 下午
 * desc:
 */
abstract class BaseBusinessAc<T : PageViewModel> : AppCompatActivity() {

    protected lateinit var viewModel: T

    abstract fun getViewModelClass(): Class<T>

    abstract fun getContentLayoutId(): Int

    open fun initViewModel() {

    }

    abstract fun initObserve()

    abstract fun initListener()

    abstract fun onViewCreated(decorView: View)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.soda_core_ac_base)

        val contentContainer = findViewById<FrameLayout>(R.id.content_container)
        if (getContentLayoutId() != 0) {
            val contentView = LayoutInflater.from(this).inflate(getContentLayoutId(), contentContainer, false)
            contentContainer.addView(contentView)
        }

        viewModel = ViewModelProvider.NewInstanceFactory().create(getViewModelClass())
        initViewModel()

        viewModel.observePageState(this, Observer<PageState> {
            Log.d(HiyHelper.tag, it.desc)
        })
        initObserve()

        initListener()

        viewModel.initData()

        onViewCreated(findViewById(android.R.id.content))
    }
}