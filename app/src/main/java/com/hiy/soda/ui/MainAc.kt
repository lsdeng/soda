package com.hiy.soda.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.hiy.monbie.core.BaseActivity
import com.hiy.monbie.core.HiyHelper
import com.hiy.monbie.core.PageState
import com.hiy.monbie.core.PageViewModel
import com.hiy.soda.R
import com.hiy.soda.bean.dto.RollGoods
import com.hiy.soda.provider.IProvider
import com.kunminx.architecture.domain.message.MutableResult
import java.util.*

class MainAc : BaseActivity<CountViewModel>() {
    override fun getViewModelClass(): Class<CountViewModel> {
        return CountViewModel::class.java
    }

    override fun getContentLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initViewModel() {
    }

    override fun initObserve() {
        viewModel.observePageState(this, Observer {
            Log.d(HiyHelper.tag, "mainac-pageState${it.desc}")
        })

        viewModel.observeCountState(this, Observer {
            Log.d(HiyHelper.tag, "mainac-count = $it")
        })
    }

    override fun initListener() {

    }

    override fun onViewCreated(decorView: View) {
        Log.d(HiyHelper.tag, "onViewCreated")
        viewModel.loadData()
        val bookList = ServiceLoader.load(IProvider::class.java, javaClass.classLoader).toList()
        bookList.forEach {
            Log.d("MainActivity", it.name())
        }

    }

//
//
//
//
//    private lateinit var girlIv: ImageView
//    private lateinit var mRootView: View
//    private lateinit var mRv: RecyclerView;
//    private val mGoodsList = mutableListOf<RollGoods>()
//
//    private val handler = object : Handler(Looper.getMainLooper(), Callback { true }) {
//        override fun handleMessage(msg: Message) {
//            super.handleMessage(msg)
//        }
//    }
//
//    private val rollVm by lazy {
//        ViewModelProvider(this@MainAc, ViewModelProvider.NewInstanceFactory()).get(RollVm::class.java)
//    }
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        initView()
//
//        initListener()
//
//        loadData()
////        Log.d("lsd", "$HiyHelper.tag")
//
//
//        handler.sendMessage(Message.obtain())
//
//        lifecycle.addObserver(object : LifecycleEventObserver {
//            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
//                when (event) {
//                    Lifecycle.Event.ON_RESUME -> {
//
//                    }
//                }
//            }
//        })
//
//
//        val threadLocalValue = ThreadLocal<Int>()
//        threadLocalValue.set(6)
//
//        findViewById<TextView>(R.id.tv).apply {
//            setOnClickListener {
//                //跳转的默认扫码界面
//                //跳转的默认扫码界面
//                startActivityForResult(Intent(this@MainAc, CaptureActivity::class.java), 1)
//            }
//
//        }
//
//
//        PermissionX.init(this@MainAc)
//            .permissions(Manifest.permission.CAMERA)
//            .request { allGranted, grantedList, deniedList ->
//                if (allGranted) {
//                    Toast.makeText(this, "All permissions are granted", Toast.LENGTH_LONG).show()
//                } else {
//                    Toast.makeText(this, "These permissions are denied: $deniedList", Toast.LENGTH_LONG).show()
//                }
//            }
//    }
//
//    private fun loadData() {
//        var i = 0
//        i++
//        rollVm.attachGoods()
////        rollVm.getGirlList()
//    }
//
//    override fun onResume() {
//        super.onResume()
//        initListener()
//    }
//
//    private fun initListener() {
//        rollVm.goods.observe(this, object : Observer<List<RollGoods>> {
//            @SuppressLint("NotifyDataSetChanged")
//            override fun onChanged(t: List<RollGoods>?) {
//                Log.d("lsd-goods-onChanged", "")
//                mGoodsList.clear()
//                if (t != null) {
//                    mGoodsList.addAll(t)
//                }
//                mRv.adapter?.notifyDataSetChanged()
//            }
//        })
//
//        rollVm.girls.observe(this) {
//            it.firstOrNull()?.let { url ->
////                Glide.with(this@MainAc).load(url.imageUrl).into(girlIv)
//            }
//        }
//    }
//
//    val list : MutableList<Any> = mutableListOf()
//
//    private fun initView() {
//        val titleTv : TextView= findViewById(R.id.tv)
//        titleTv.text = "${HiyHelper.tag}-${ConstantUI.TAG}"
//
//        mRv = findViewById(R.id.rv)
//        girlIv = findViewById(R.id.girl_iv)
//
//        list.add(OptionBo("rv", "RecyclerView"))
//        val adapter = MultiTypeAdapter(list)
//        adapter.register(OptionBo::class.java, OptionItemViewBinder())
//        mRv.adapter = adapter
//        mRootView = findViewById(R.id.root_view)
//        mRootView.post {
//
//        }
//
//        titleTv.setOnClickListener {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                val channel = NotificationChannel("111", "ceshi", NotificationManager.IMPORTANCE_HIGH)
//                channel.description = "111"
//
//                val notifyManager = this@MainAc.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
//                notifyManager.createNotificationChannel(channel)
//            } else {
//            }
//
//        }
//
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == RESULT_OK) {
//            when (requestCode) {
//                1 -> {
//                    CameraScan.parseScanResult(data)?.let {
//                        Log.d("lsd11", it)
////                        rollVm.getBarcodeGoodsDetails(it)
//                    }
//                }
//            }
//        }
//    }


}

class CountViewModel : PageViewModel() {
    val count: MutableResult<Int> = MutableResult<Int>(0)

    fun observeCountState(lifecycleOwner: LifecycleOwner, observer: Observer<Int>) {
        count.observe(lifecycleOwner, observer)
    }

    override fun initData() {
        super.initData()
    }


    fun loadData() {
        Thread {
            Thread.sleep(1000)
            count.postValue(1000)
            dispatchPageState(PageState.Content)
        }.start()
    }
}

class GoodsViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
    companion object {
        fun getLayoutId(): Int {
            return R.layout.list_ite_goods
        }
    }


    fun fillData(goods: RollGoods) {
        val nameTv = itemView.findViewById<TextView>(R.id.name_tv)
        nameTv.text = goods.goodsName
    }
}