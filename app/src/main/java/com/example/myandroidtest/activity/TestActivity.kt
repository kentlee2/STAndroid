package com.example.myandroidtest.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.myandroidtest.R
import com.example.myandroidtest.TestViewModel
import com.example.myandroidtest.adapter.ScreenSlidePagerAdapter
import com.example.myandroidtest.base.BaseActivity
import com.example.myandroidtest.databinding.ActivityTestBinding


class TestActivity : BaseActivity<TestViewModel,ActivityTestBinding>() {
    val viewModel: TestViewModel by viewModels()
    override fun initView(savedInstanceState: Bundle?) {
        val mAdapter = ScreenSlidePagerAdapter(this)
        mDatabind.mainPager.apply {
            offscreenPageLimit = 1
            adapter = mAdapter
        }
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