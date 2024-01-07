package com.example.myandroidtest.activity

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.ActivityNavigator
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.covy.common.base.dialog.BaseDialog
import com.example.myandroidtest.R
import com.example.myandroidtest.databinding.ActivityMainBinding
import com.example.myandroidtest.view.dialog.MessageDialog
import com.snail.antifake.deviceid.AndroidDeviceIMEIUtil
import com.snail.antifake.jni.EmulatorDetectUtil


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(false){
            MessageDialog.Builder(this)
                // 标题可以不用填写
                .setTitle("提示")
                // 内容必须要填写
                .setMessage("您当前使用的是模拟器，请使用真机")
                // 确定按钮文本
                .setConfirm(getString(R.string.common_confirm))
                // 设置 null 表示不显示取消按钮
//                .setCancel(getString(R.string.common_cancel))
                .setCancelable(false)
                // 设置点击按钮后不关闭对话框
                //.setAutoDismiss(false)
                .setListener(object : MessageDialog.OnListener {

                    override fun onConfirm(dialog: BaseDialog?) {
                        finish()
                    }

                    override fun onCancel(dialog: BaseDialog?) {
                        finish()
                    }
                })
                .show()
            return
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        //将App bar与NavController绑定
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.toolbar.setNavigationOnClickListener {
            navController.popBackStack()
        }
    }

    override fun finish() {
        super.finish()
        //将弹出动画应用于 Activity 过渡
        ActivityNavigator.applyPopAnimationsToPendingTransition(this)
    }
}