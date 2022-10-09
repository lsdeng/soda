package com.hiy.soda.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Handler.Callback
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hiy.monbie.core.HiyHelper
import com.hiy.soda.R
import com.hiy.soda.bean.dto.RollGoods
import com.hiy.soda.vm.RollVm
import com.king.zxing.CameraScan
import com.king.zxing.CaptureActivity
import com.permissionx.guolindev.PermissionX


class MainAc : AppCompatActivity() {


    private lateinit var girlIv: ImageView
    private lateinit var mRootView: View
    private lateinit var mRv: RecyclerView;
    private val mGoodsList = mutableListOf<RollGoods>()

    private val handler = object : Handler(Looper.getMainLooper(), Callback { true }) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
        }
    }

    private val rollVm by lazy {
        ViewModelProvider(this@MainAc, ViewModelProvider.NewInstanceFactory()).get(RollVm::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()

        initListener()

        loadData()


        handler.sendMessage(Message.obtain())

        lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                when (event) {
                    Lifecycle.Event.ON_RESUME -> {

                    }
                }
            }
        })


        val threadLocalValue = ThreadLocal<Int>()
        threadLocalValue.set(6)

        findViewById<View>(R.id.tv).setOnClickListener {
            //跳转的默认扫码界面
            //跳转的默认扫码界面
            startActivityForResult(Intent(this@MainAc, CaptureActivity::class.java), 1)
        }


        PermissionX.init(this@MainAc)
            .permissions(Manifest.permission.CAMERA)
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    Toast.makeText(this, "All permissions are granted", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "These permissions are denied: $deniedList", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun loadData() {
        var i = 0
        i++
        rollVm.attachGoods()
        rollVm.getGirlList()
    }

    override fun onResume() {
        super.onResume()
        initListener()
    }

    private fun initListener() {
        rollVm.goods.observe(this, object : Observer<List<RollGoods>> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onChanged(t: List<RollGoods>?) {
                Log.d("lsd-goods-onChanged", "")
                mGoodsList.clear()
                if (t != null) {
                    mGoodsList.addAll(t)
                }
                mRv.adapter?.notifyDataSetChanged()
            }
        })

        rollVm.girls.observe(this) {
            it.firstOrNull()?.let { url ->
//                Glide.with(this@MainAc).load(url.imageUrl).into(girlIv)
            }
        }
    }

    private fun initView() {
        val titleTv : TextView= findViewById(R.id.tv)
        titleTv.text = HiyHelper.tag
        mRv = findViewById(R.id.rv)
        girlIv = findViewById(R.id.girl_iv)
        mRv.adapter = object : RecyclerView.Adapter<GoodsViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoodsViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(GoodsViewHolder.getLayoutId(), parent, false)
                return GoodsViewHolder(view)
            }

            override fun onBindViewHolder(holder: GoodsViewHolder, position: Int) {
                holder.fillData(mGoodsList[position])
            }

            override fun getItemCount(): Int {
                return mGoodsList.size
            }

        }

        mRootView = findViewById(R.id.root_view)
        mRootView.post {

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                1 -> {
                    CameraScan.parseScanResult(data)?.let {
                        Log.d("lsd11", it)
                        rollVm.getBarcodeGoodsDetails(it)
                    }
                }
            }
        }
    }


}

class CountViewModel : ViewModel() {
    val count: LiveData<Int> = MutableLiveData<Int>()
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