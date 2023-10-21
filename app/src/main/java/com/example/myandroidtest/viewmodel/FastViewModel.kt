package com.example.myandroidtest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.scopeLife
import com.covy.common.base.viewmodel.BaseViewModel
import com.drake.net.Get
import com.drake.net.utils.fastest
import com.example.myandroidtest.BuildConfig
import kotlinx.coroutines.Deferred

/**
 * 最快请求url
 */
class FastViewModel: BaseViewModel() {
    var urlResult = MutableLiveData<String>()
    fun requestTest(){
        scopeLife {
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
            urlResult.value = data
        }
    }
}