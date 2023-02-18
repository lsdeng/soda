package com.hiy.soda.ui

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.hiy.monbie.core.BaseBusinessAc
import com.hiy.monbie.core.PageViewModel
import com.hiy.soda.R
import com.hiy.soda.bean.dto.Goods
import com.hiy.soda.bean.dto.isValid
import com.hiy.soda.component.SodaEditLayout
import com.hiy.soda.component.SodaGalleryLayout
import com.hiy.soda.component.SodaTextLayout
import com.hiy.soda.database.DBHelper
import com.hiy.soda.helper.PathHelper
import com.hiy.soda.helper.logger
import com.hiy.soda.helper.startup.GsonHelper
import com.kunminx.architecture.domain.message.MutableResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class AddGoodsAc : BaseBusinessAc<AddGoodsVM>() {


    lateinit var mNameLayout: SodaEditLayout
    lateinit var mTimeLayout: SodaTextLayout
    lateinit var mImageLayout: SodaGalleryLayout

    companion object {
        fun navAddGoods(ac: Activity) {
            Intent(ac, AddGoodsAc::class.java).apply {
                ac.startActivity(this)
            }
        }
    }

    override fun getContentLayoutId(): Int {
        return R.layout.ac_add_goods
    }

    override fun initObserve() {
//        viewModel.observeState<Goods>(AddGoodsVM.KEY_GOODS, this@AddGoodsAc, Observer {
//            viewModel.goods.value?.apply {
//                this.name = it.toString()
//                viewModel.dispatchState(AddGoodsVM.KEY_GOODS, this)
//            }
//        })
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun initListener() {
        mTimeLayout.getContentView().apply {
            setOnClickListener {
                val ca: Calendar = Calendar.getInstance()
                val mYear = ca.get(Calendar.YEAR);
                val mMonth = ca.get(Calendar.MONTH);
                val mDay = ca.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog(this@AddGoodsAc, { view, year, month, dayOfMonth ->
                    ca.set(Calendar.YEAR, year)
                    ca.set(Calendar.MONTH, month)
                    ca.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    mTimeLayout.getContentView().text = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(ca.time)
                    viewModel.goods.value?.apply {
                        this.validPeriod = ca.time
                        viewModel.dispatchState(AddGoodsVM.KEY_GOODS, this)
                    }
                }, mYear, mMonth, mDay).show()
            }
        }

        mImageLayout.getContentView().setOnClickListener {
            Intent(Intent.ACTION_PICK).apply {
                this.type = "image/*"
                this@AddGoodsAc.startActivityForResult(this, 100)
            }
        }
    }

    override fun onViewCreated(decorView: View) {
        mNameLayout = decorView.findViewById(R.id.name_etl)
        mNameLayout.getNameView().apply {
            text = "商品名称"
        }

        mNameLayout.getContentView().addTextChangedListener {
            viewModel.goods.value?.apply {
                this.name = it.toString()
                viewModel.dispatchState(AddGoodsVM.KEY_GOODS, this)
            }
        }

        mTimeLayout = decorView.findViewById(R.id.time_tvl)
        mTimeLayout.getNameView().apply {
            text = "有效期"
        }

        mImageLayout = decorView.findViewById(R.id.image_gl)
    }

    override fun initToolbar(view: View) {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100) {
            data?.data?.let {
                Glide.with(this@AddGoodsAc).load(it).into(mImageLayout.getContentView())
                viewModel.goods.value?.apply {
                    this.path = PathHelper.getPath(this@AddGoodsAc, it)
                    viewModel.dispatchState(AddGoodsVM.KEY_GOODS, this)
                }
            }
        }
    }
}

class AddGoodsVM : PageViewModel() {
    companion object {
        val KEY_GOODS = "goods"
    }

    val goods: MutableResult<Goods> = MutableResult(Goods())

    override fun getExternalStates(): Map<String, MutableResult<*>> {
        return mapOf<String, MutableResult<*>>(KEY_GOODS to goods)
    }

    override fun loadData() {
    }
}