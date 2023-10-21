package com.example.myandroidtest.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.myandroidtest.R
import com.example.myandroidtest.adapter.MainTabAdapter
import com.example.myandroidtest.base.BaseFragment1
import com.example.myandroidtest.bean.MainTabBean
import com.example.myandroidtest.databinding.FragmentDetailViewBinding
import com.example.myandroidtest.viewmodel.DetailViewModel


/**
 * @author andy
 * @date 2023/10/17
 */
class DetailViewFragment : BaseFragment1<DetailViewModel, FragmentDetailViewBinding>() {
    private var type = 0
    // 底部tab数据
    private val tabData = arrayListOf<MainTabBean>()
    // ViewPager对应显示的Fragment
    private val fragments = arrayListOf<Fragment>()
    override fun initView(savedInstanceState: Bundle?) {
        type = arguments?.getInt("type") ?: 0
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