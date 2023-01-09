package com.hiy.soda.ui.fg

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hiy.soda.R
import com.hiy.soda.helper.SodaConstant

/**
 * auther: liusaideng
 * created on :  2023/1/6 10:26 上午
 * desc:
 */
class LifeCycleFg : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(container?.context).inflate(R.layout.list_item_option, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(SodaConstant.TAG, "onViewCreated")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(SodaConstant.TAG, "onAttach")
    }
}