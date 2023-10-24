package com.example.myandroidtest.activity

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.covy.common.base.viewmodel.BaseViewModel
import com.example.myandroidtest.R
import com.example.myandroidtest.base.BaseActivity
import com.example.myandroidtest.databinding.ActivityTestBinding
import com.example.myandroidtest.ext.init
import com.example.myandroidtest.fragment.TestFragment


class TestActivity : BaseActivity<BaseViewModel, ActivityTestBinding>() {
    override fun initView(savedInstanceState: Bundle?) {

        mDatabind.mainPager.init(
            this,
            arrayListOf(
                TestFragment.newInstance(0),
                TestFragment.newInstance(1),
                TestFragment.newInstance(2),
            ),
        )
        mDatabind.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> {
                    mDatabind.mainPager.currentItem = 0
                    true
                }

                R.id.menu_search -> {
                    mDatabind.mainPager.currentItem = 1
                    true
                }

                R.id.menu_profile -> {
                    mDatabind.mainPager.currentItem = 2
                    true
                }

                else -> false
            }
        }
        mDatabind.mainPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> {
                        mDatabind.bottomNav.selectedItemId = R.id.menu_home
                    }

                    1 -> {
                        mDatabind.bottomNav.selectedItemId = R.id.menu_search
                    }

                    else -> {
                        mDatabind.bottomNav.selectedItemId = R.id.menu_profile
                    }
                }

            }
        })
    }


}