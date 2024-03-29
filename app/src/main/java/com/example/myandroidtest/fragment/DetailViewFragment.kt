package com.example.myandroidtest.fragment


import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.fragment.app.Fragment
import com.covy.common.base.viewmodel.BaseViewModel
import com.example.myandroidtest.R
import com.example.myandroidtest.adapter.MainTabAdapter
import com.example.myandroidtest.base.BaseFragment1
import com.example.myandroidtest.bean.MainTabBean
import com.example.myandroidtest.databinding.FragmentDetailViewBinding


/**
 * @author andy
 * @date 2023/10/17
 */
class DetailViewFragment : BaseFragment1<BaseViewModel, FragmentDetailViewBinding>() {
    private var content = ""

    // 底部tab数据
    private val tabData = arrayListOf<MainTabBean>()

    // ViewPager对应显示的Fragment
    private val fragments = arrayListOf<Fragment>()
    override fun initView(savedInstanceState: Bundle?) {
        content = arguments?.getString("type") ?: ""
        if (TextUtils.isEmpty(content)) mViewBind.tv.visibility = View.GONE
        mViewBind.tv.text = "这是传过来的数据：".plus(content)
        // 底部Tab数据
        tabData.add(MainTabBean("首页", R.drawable.home, R.drawable.home_select))
        tabData.add(MainTabBean("导航", R.drawable.video, R.drawable.video_select))
        tabData.add(MainTabBean("项目", R.drawable.game, R.drawable.game_select))
        tabData.add(MainTabBean("我的", R.drawable.mine, R.drawable.mine_select))
        // ViewPager数据
        fragments.add(TestNewFragment())
        fragments.add(TestNewFragment())
        fragments.add(TestNewFragment())
        fragments.add(TestNewFragment())
        // 底部TAB
        val tabAdapter = MainTabAdapter(requireContext(), tabData, R.layout.item_tab_layout)
        // 与ViewPager关联
        mViewBind.tabLayout.setAdapter(tabAdapter)
    }

    override fun initData() {
        super.initData()

    }

}