package com.example.myandroidtest.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.scopeLife
import com.blankj.utilcode.util.SPUtils
import com.covy.common.base.viewmodel.BaseViewModel
import com.drake.net.Get
import com.drake.net.utils.fastest
import com.example.myandroidtest.BuildConfig
import com.example.myandroidtest.net.CallHelper
import kotlinx.coroutines.Deferred
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

/**
 * 最快请求url
 */
class FastViewModel : BaseViewModel() {
    var urlResult = MutableLiveData<String>()

    val hostMap = mutableListOf<String>()
    fun requestTest() {
        val hostArr = BuildConfig.HOST_ARRAY
        scopeLife {
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

    fun requestTest2() {
        val hostArr = BuildConfig.HOST_ARRAY
        hostArr.forEach {
            hostMap.add(it)
        }
        requestUrl {
            Log.d("testUrl", "使用url:$it")
        }
    }

    fun requestUrl(successCallback: ((String) -> Unit)? = null) {
        if (hostMap.isEmpty()) {
            //全部请求失败,重新请求
            requestTest2()
            return
        }
        hostMap[0].let {
            CallHelper.call(it).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("testUrl", "请求失败：$it--${e.message}")
                    //下一个url
                    hostMap.removeAt(0)
                    requestUrl()
                }

                override fun onResponse(call: Call, response: Response) {
                    Log.d("testUrl", "请求成功:$it")
                    successCallback?.invoke(response.body?.string() ?: "")
                    SPUtils.getInstance().put("baseUrl", response.body?.string())
                }

            })
        }

    }
}