package com.example.myandroidtest.fragment

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.covy.common.base.viewmodel.BaseViewModel
import com.example.myandroidtest.R
import com.example.myandroidtest.base.BaseFragment1
import com.example.myandroidtest.databinding.FragmentCustomViewBinding

/**
 * @author andy
 * @date 2023/10/17
 */
class CustomViewFragment : BaseFragment1<BaseViewModel, FragmentCustomViewBinding>() {

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initData() {
        super.initData()
        mViewBind.dragBtn.setOnClickListener {
            findNavController().navigate(R.id.action_CustomViewFragment_to_DetailViewFragment)
        }
        mViewBind.tabLayoutBtn.setOnClickListener {
            findNavController().navigate(
                R.id.action_CustomViewFragment_to_DetailViewFragment,
                bundleOf("type" to "数据")
            )
        }
        mViewBind.motionLayout.setOnClickListener {
            findNavController().navigate(R.id.action_CustomViewFragment_to_motionLayoutFragment)
        }
        mViewBind.dialog.setOnClickListener {
            findNavController().navigate(R.id.action_CustomViewFragment_to_absDialogFragment)
        }
    }

}