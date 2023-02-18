package com.hiy.monbie.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.hiy.soda.helper.visible
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.reflect.ParameterizedType

/**
 * auther: liusaideng
 * created on :  2022/11/24 3:11 下午
 * desc:
 */
abstract class BaseBusinessAc<T : PageViewModel> : BaseActivity() {

    private lateinit var contentContainer: FrameLayout
    private lateinit var loadingBottomContainer: View

    protected lateinit var viewModel: T

    abstract fun getContentLayoutId(): Int

    open fun getToolbarLayoutId(): Int {
        return R.layout.soda_core_layout_toolbar
    }

    abstract fun onViewCreated(decorView: View)

    open fun initToolbar(view: View) {

    }

    abstract fun initListener()

    abstract fun initObserve()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.soda_core_ac_base)

        initViewInternal()

        if (getContentLayoutId() != 0) {
            val contentView = LayoutInflater.from(this).inflate(getContentLayoutId(), contentContainer, false)
            contentContainer.addView(contentView)
        }

        findViewById<ViewGroup>(R.id.toolbar_container).apply {
            View.inflate(this@BaseBusinessAc, getToolbarLayoutId(), this)
            initToolbar(this)
        }

        val modelClass =
            (((javaClass.genericSuperclass as? ParameterizedType)?.actualTypeArguments?.get(0)) as? Class<T>) ?: throw RuntimeException("T is error")
        viewModel = ViewModelProvider.NewInstanceFactory().create(modelClass)

        viewModel.onActivityCreated()

        onViewCreated(findViewById(android.R.id.content))

        initInternal()

        initListener()

        initObserve()

        loadData()
    }

    open fun loadData() {
        viewModel.loadData()
    }

    private fun initViewInternal() {
        contentContainer = findViewById(R.id.content_container)
        loadingBottomContainer = findViewById(R.id.loading_bottom_layout)

    }

    private fun initInternal() {
        viewModel.observeState(PageViewModel.KEY_PAGE_STATE, this, Observer<PageState> {
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

        dispatchState(initPageState())
    }

    open fun initPageState() = PageState.Content

    inline fun <T> beginCoroutinesIO(params: T, crossinline callback: (T) -> Unit) {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                callback.invoke(params)
            }
        }
    }

    fun dispatchState(pageState: PageState) {
        viewModel.dispatchState(PageViewModel.KEY_PAGE_STATE, pageState)
    }

}