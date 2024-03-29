package com.example.myandroidtest.base

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.covy.common.base.activity.BaseVmVbActivity
import com.covy.common.base.viewmodel.BaseViewModel
import com.example.myandroidtest.ext.dismissLoadingExt
import com.example.myandroidtest.ext.showLoadingExt
/**
 * 时间　: 2019/12/21
 * 作者　: hegaojian
 * 描述　: 你项目中的Activity基类，在这里实现显示弹窗，吐司，还有加入自己的需求操作 。使用ViewBinding请继承
 * BaseVmActivity例如
 * abstract class BaseActivity<VM : BaseViewModel> : BaseVmActivity<VM>() {
 */
abstract class BaseActivity1<VM : BaseViewModel, VB : ViewBinding> : BaseVmVbActivity<VM, VB>() {

    abstract override fun initView(savedInstanceState: Bundle?)

    /**
     * 创建liveData观察者
     */
    override fun createObserver() {}

    /**
     * 打开等待框
     */
    override fun showLoading(message: String) {
        showLoadingExt(message)
    }

    /**
     * 关闭等待框
     */
    override fun dismissLoading() {
        dismissLoadingExt()
    }

   /* *//**
     * 在任何情况下本来适配正常的布局突然出现适配失效，适配异常等问题，只要重写 Activity 的 getResources() 方法
     *//*
    override fun getResources(): Resources {
        AutoSizeCompat.autoConvertDensityOfGlobal(super.getResources())
        return super.getResources()
    }*/
}