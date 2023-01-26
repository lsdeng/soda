package com.hiy.soda.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hiy.monbie.core.BaseBusinessAc
import com.hiy.monbie.core.PageViewModel
import com.hiy.soda.R
import com.hiy.soda.helper.SodaConstant
import com.hiy.soda.helper.startup.GsonHelper
import com.hiy.soda.ui.adapter.BaseViewHolder
import com.kunminx.architecture.domain.message.MutableResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlin.random.Random

/**
 * auther: liusaideng
 * created on :  2023/1/3 4:26 下午
 * desc:
 */
class PagingAc : BaseBusinessAc<PagingViewModel>() {

    private val dataViewModel by viewModels<CheeseViewModel>(null, {
        ViewModelProvider.AndroidViewModelFactory.getInstance(application)
    })


    override fun getContentLayoutId(): Int {
        return R.layout.activity_rv
    }

    override fun initObserve() {

    }

    override fun initListener() {
    }

    override fun onViewCreated(decorView: View) {
        val rv = findViewById<RecyclerView>(R.id.rv)
        rv.layoutManager = LinearLayoutManager(this@PagingAc)
        rv.adapter = UserAdapter(UserComparator)

        lifecycleScope.launchWhenCreated {
            dataViewModel.allCheeses.collectLatest {
                (rv.adapter as UserAdapter).submitData(it)
            }
        }
    }
}

class SodaPagingSource(
    val query: String,
) : PagingSource<Int, Int>() {

    override fun getRefreshKey(state: PagingState<Int, Int>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Int> {
        Log.d(SodaConstant.TAG, "paging-${GsonHelper.get().toJson(params)}")

        return LoadResult.Page(data = listOf(1, 2, 3, 4, 5), prevKey = null, nextKey = Random.nextInt(0, 100))
    }

}


class PagingViewModel : PageViewModel() {
    private val list by lazy {
        MutableResult<List<Int>>(
            listOf(
                1, 2, 3, 4, 5, 6, 7, 8, 9, 0,
                1, 2, 3, 4, 5, 6, 7, 8, 9, 0,
                1, 2, 3, 4, 5, 6, 7, 8, 9, 0,
                1, 2, 3, 4, 5, 6, 7, 8, 9, 0
            )
        )
    }

    fun getDataSource(): List<Int> {
        return list.value ?: listOf()
    }

    override fun getExternalStates(): Map<String, MutableResult<*>> {
        return mapOf()
    }

    override fun loadData() {

    }
}


class UserAdapter(diffCallback: DiffUtil.ItemCallback<Int>) :
    PagingDataAdapter<Int, BaseViewHolder>(diffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseViewHolder {
        return BaseViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_option, parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        Log.d(SodaConstant.TAG, "${getItem(position)}")
    }
}

object UserComparator : DiffUtil.ItemCallback<Int>() {
    override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
        // Id is unique.
        return true
    }

    override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
        return oldItem == newItem
    }
}


class CheeseViewModel() : ViewModel() {
    /**
     * We use the Kotlin [Flow] property available on [Pager]. Java developers should use the
     * RxJava or LiveData extension properties available in `PagingRx` and `PagingLiveData`.
     */
    val allCheeses: Flow<PagingData<Int>> = Pager(
        config = PagingConfig(
            /**
             * A good page size is a value that fills at least a few screens worth of content on a
             * large device so the User is unlikely to see a null item.
             * You can play with this constant to observe the paging behavior.
             *
             * It's possible to vary this with list device size, but often unnecessary, unless a
             * user scrolling on a large device is expected to scroll through items more quickly
             * than a small device, such as when the large device uses a grid layout of items.
             */
            pageSize = 60

        )
    ) {
        SodaPagingSource("")
    }.flow
        .map { pagingData ->
            pagingData
                // Map cheeses to common UI model.
                .map { cheese -> cheese }
        }
        .cachedIn(viewModelScope)
}
