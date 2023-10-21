package com.example.myandroidtest.fragment

import android.os.Bundle
import android.text.Html
import androidx.lifecycle.Observer
import com.covy.common.base.fragment.BaseVmVbFragment
import com.example.myandroidtest.base.BaseFragment1
import com.example.myandroidtest.databinding.FragmentFastBinding
import com.example.myandroidtest.viewmodel.FastViewModel


class FastFragment : BaseFragment1<FastViewModel, FragmentFastBinding>() {


    override fun initView(savedInstanceState: Bundle?) {

    }


    override fun lazyLoadData() {
        showLoading()
        mViewModel.requestTest()
    }

    override fun createObserver() {
        mViewModel.urlResult.observe(this, Observer {
            dismissLoading()
            mViewBind.textviewFast.text = Html.fromHtml(it)
        })
    }


}