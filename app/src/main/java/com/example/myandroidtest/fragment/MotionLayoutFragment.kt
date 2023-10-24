package com.example.myandroidtest.fragment

import android.os.Bundle
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.covy.common.base.viewmodel.BaseViewModel
import com.example.myandroidtest.base.BaseFragment1
import com.example.myandroidtest.databinding.FragmentMotionLayoutBinding
import com.example.myandroidtest.ext.init

/**
 * @author andy
 * @date 2023/10/24
 */
class MotionLayoutFragment : BaseFragment1<BaseViewModel, FragmentMotionLayoutBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        mViewBind.viewpager.init(
            childFragmentManager, arrayListOf(
                TestNewFragment.newInstance(0),
                TestNewFragment.newInstance(1),
                TestNewFragment.newInstance(2),
                TestNewFragment.newInstance(3),
            ), arrayListOf(
                "title",
                "title2",
                "title3",
                "title4",
            )
        ).run {
            mViewBind.tabs.setupWithViewPager(this)
        }

        mViewBind.viewpager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int, positionOffset: Float, positionOffsetPixels: Int
            ) {
                mViewBind.motionLayout.progress =
                    (position + positionOffset) / (mViewBind.viewpager.adapter?.count!! - 1)
            }

            override fun onPageSelected(position: Int) {}
            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    override fun initData() {
        super.initData()

    }

}