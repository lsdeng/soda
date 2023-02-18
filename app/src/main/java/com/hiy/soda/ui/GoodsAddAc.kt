package com.hiy.soda.ui

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.hiy.monbie.core.BaseBusinessAc
import com.hiy.monbie.core.PageViewModel
import com.hiy.monbie.core.ext.setToolbarRightText
import com.hiy.monbie.core.ext.setToolbarTitle
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
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.GlideEngine
import com.zhihu.matisse.internal.entity.CaptureStrategy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*


class GoodsAddAc : BaseBusinessAc<AddGoodsVM>() {


    lateinit var mNameLayout: SodaEditLayout
    lateinit var mTimeLayout: SodaTextLayout
    lateinit var mImageLayout: SodaGalleryLayout

    companion object {
        fun navAddGoods(ac: Activity) {
            Intent(ac, GoodsAddAc::class.java).apply {
                ac.startActivity(this)
            }
        }
    }

    override fun getContentLayoutId(): Int {
        return R.layout.ac_add_goods
    }

    override fun initObserve() {
//        viewModel.observeState<Goods>(AddGoodsVM.KEY_GOODS, this@GoodsAddAc, Observer {
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
                DatePickerDialog(this@GoodsAddAc, { view, year, month, dayOfMonth ->
                    ca.set(Calendar.YEAR, year)
                    ca.set(Calendar.MONTH, month)
                    ca.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    mTimeLayout.getContentView().text = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(ca.time)
                    viewModel.goods.value?.apply {
                        this.validPeriod = ca.time
                        viewModel.dispatchState(AddGoodsVM.KEY_GOODS, this)
                    }
                }, mYear, mMonth, mDay).show()
            }
        }

        mImageLayout.getContentView().setOnClickListener {
            Matisse.from(this@GoodsAddAc)
                .choose(MimeType.ofImage())
                .capture(true)
                .captureStrategy(CaptureStrategy(true, "com.hiy.soda.fileprovider"))
                .countable(true)
                .maxSelectable(1)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(GlideEngine())
                .showPreview(false) // Default is `true`
                .forResult(100)
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
        view.setToolbarTitle("添加商品")
        view.setToolbarRightText("保存") {
            if (viewModel.goods.value?.isValid() == true) {
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        DBHelper.database.goodsDao().insertAll(viewModel.goods.value!!)
                        DBHelper.database.goodsDao().getAll().onEach {
                            GsonHelper.get().toJson(it).logger()
                        }
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@GoodsAddAc, "保存成功", Toast.LENGTH_SHORT).show()
                            finish()

                        }
                    }
                }
            } else {
                Toast.makeText(this@GoodsAddAc, "保存失败", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                100 -> {
                    Matisse.obtainResult(data).firstOrNull()?.let {
                        Glide.with(this@GoodsAddAc).load(it).into(mImageLayout.getContentView())
                        // 数据
                        viewModel.goods.value?.apply {
                            this.path = PathHelper.getPath(this@GoodsAddAc, it)
                            viewModel.dispatchState(AddGoodsVM.KEY_GOODS, this)
                        }

                    }
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