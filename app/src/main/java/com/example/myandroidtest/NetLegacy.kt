package com.example.myandroidtest

import android.text.Html
import android.util.Base64
import android.util.Log
import com.drake.net.Get
import com.drake.net.utils.fastest
import com.drake.net.utils.scope
import com.example.myandroidtest.databinding.ActivityMainBinding
import com.example.myandroidtest.databinding.ActivitySplashBinding
import com.example.myandroidtest.databinding.FragmentFastBinding
import com.example.myandroidtest.databinding.FragmentFirstBinding
import kotlinx.coroutines.Deferred

object NetLegacy {
    fun net(mainBinding: FragmentFastBinding?) {
        scope {
            val hostArr = BuildConfig.HOST_ARRAY
            val list = mutableListOf<Deferred<String>>()
            hostArr.forEach {
                val task = Get<String>(it) { setGroup("fast") }
                list.add(task)
            }
            //获取到最快的结果后自动取消请求
            val data = try {
                fastest(list, "fast") // 当 task/task1/task2 全部异常之后再并发执行 backupTask/backupTask1
            } catch (e: Exception) {
                val backupTask = Get<String>("/api2")
                val backupTask1 = Get<String>("/api")
                fastest(listOf(backupTask, backupTask1))
            }

            mainBinding?.textviewFast?.text = Html.fromHtml(data)
        }
    }
}