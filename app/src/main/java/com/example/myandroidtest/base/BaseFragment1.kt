package com.example.myandroidtest.base

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.covy.common.base.fragment.BaseVmVbFragment
import com.covy.common.base.viewmodel.BaseViewModel
import com.example.myandroidtest.ext.dismissLoadingExt
import com.example.myandroidtest.ext.showLoadingExt


/**
 * 时间　: 2019/12/21
 * 描述　: 项目中的Fragment基类，在这里实现显示弹窗，吐司，还有自己的需求操作 使用ViewBinding请继承
 * abstract class BaseFragment<VM : BaseViewModel> : BaseVmFragment<VM>() {
 */
abstract class BaseFragment1<VM : BaseViewModel, VB : ViewBinding> : BaseVmVbFragment<VM, VB>() {

    abstract override fun initView(savedInstanceState: Bundle?)

    /**
     * 懒加载 只有当前fragment视图显示时才会触发该方法
     */
    override fun lazyLoadData() {}

    /**
     * 创建LiveData观察者 Fragment执行onViewCreated后触发
     */
    override fun createObserver() {}

    /**
     * Fragment执行onViewCreated后触发
     */
    override fun initData() {

    }

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

}