package com.hiy.monbie.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hiy.soda.helper.visible
import java.lang.reflect.ParameterizedType

/**
 * auther: liusaideng
 * created on :  2022/11/24 3:11 下午
 * desc:
 */
abstract class BaseBusinessAc<T : PageViewModel> : AppCompatActivity() {

    private lateinit var contentContainer: FrameLayout
    private lateinit var loadingBottomContainer: View

    protected lateinit var viewModel: T

    abstract fun getContentLayoutId(): Int

    abstract fun initObserve()

    abstract fun initListener()

    abstract fun onViewCreated(decorView: View)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.soda_core_ac_base)

        initViewInternal()

        if (getContentLayoutId() != 0) {
            val contentView = LayoutInflater.from(this).inflate(getContentLayoutId(), contentContainer, false)
            contentContainer.addView(contentView)
        }

        val modelClass =
            (((javaClass.genericSuperclass as? ParameterizedType)?.actualTypeArguments?.get(0)) as? Class<T>) ?: throw RuntimeException("T is error")
        viewModel = ViewModelProvider.NewInstanceFactory().create(modelClass)

        viewModel.onActivityCreated()

        onViewCreated(findViewById(android.R.id.content))

        initInternal()

        initListener()

        initObserve()
    }

    private fun initViewInternal() {
        contentContainer = findViewById(R.id.content_container)
        loadingBottomContainer = findViewById(R.id.loading_bottom_layout)

    }

    private fun initInternal() {
        viewModel.observePageState(PageViewModel.KEY_PAGE_STATE, this, Observer<PageState> {
            when (it) {
                PageState.LOADING_OF_BOTTOM -> {
                    contentContainer.visible(false)
                    loadingBottomContainer.visible(true)
                }
                PageState.Content -> {
                    contentContainer.visible(true)
                    loadingBottomContainer.visible(false)
                }
                PageState.ERROR -> {

                }
                PageState.LOADING_OF_TOP -> {

                }
                else -> {

                }
            }
        })

        dispatchPageState(initPageState())
    }

    open fun initPageState() = PageState.LOADING_OF_BOTTOM

    fun dispatchPageState(pageState: PageState) {
        viewModel.dispatchPageState(PageViewModel.KEY_PAGE_STATE, pageState)
    }
}