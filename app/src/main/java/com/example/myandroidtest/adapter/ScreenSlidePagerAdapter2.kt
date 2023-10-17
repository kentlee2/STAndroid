package com.example.myandroidtest.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myandroidtest.fragment.TestFragment
import com.example.myandroidtest.fragment.TestNewFragment

class ScreenSlidePagerAdapter2(fa: FragmentActivity): FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 6

    override fun createFragment(position: Int): Fragment = TestNewFragment.newInstance(position)
}