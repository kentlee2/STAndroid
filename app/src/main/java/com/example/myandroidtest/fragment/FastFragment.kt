package com.example.myandroidtest.fragment

import android.os.Bundle
import android.os.Looper
import android.os.MessageQueue
import android.text.Html
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.ToastUtils
import com.covy.common.util.LogUtils
import com.example.myandroidtest.base.BaseFragment1
import com.example.myandroidtest.databinding.FragmentFastBinding
import com.example.myandroidtest.viewmodel.FastViewModel


class FastFragment : BaseFragment1<FastViewModel, FragmentFastBinding>() {


    override fun initView(savedInstanceState: Bundle?) {

    }


    override fun lazyLoadData() {
        showLoading()
        Looper.myQueue().addIdleHandler(MessageQueue.IdleHandler {
            ToastUtils.showShort("消息队列空闲的时候执行")
            //返回 false 表示执行后就将该回调移除掉，返回 true 表示该 IdleHandler 一直处于活跃状态，只要空闲就会被回调
            return@IdleHandler false
        })
        mViewModel.requestTest()
    }

    override fun createObserver() {
        mViewModel.urlResult.observe(this, Observer {
            dismissLoading()
            mViewBind.textviewFast.text = Html.fromHtml(it)
            LogUtils.debugInfo("TAGS", "宽度："+ mViewBind.textviewFast.width.toString())
            mViewBind.textviewFast.post {
                LogUtils.debugInfo("TAGS", "宽度2："+ mViewBind.textviewFast.width.toString())
            }
        })
    }




}