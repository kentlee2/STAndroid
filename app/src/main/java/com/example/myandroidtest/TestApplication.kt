package com.example.myandroidtest

import android.app.Application
import com.drake.brv.BR
import com.drake.brv.utils.BRV
import com.drake.net.NetConfig
import com.drake.net.cookie.PersistentCookieJar
import com.drake.net.interceptor.LogRecordInterceptor
import com.drake.net.okhttp.setDebug
import okhttp3.Cache
import java.util.concurrent.TimeUnit

class TestApplication :Application(){
    override fun onCreate() {
        super.onCreate()
        NetConfig.initialize(Api.HOST, this) {

            // 超时设置
            connectTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)

            // 本框架支持Http缓存协议和强制缓存模式
            cache(Cache(cacheDir, 1024 * 1024 * 128)) // 缓存设置, 当超过maxSize最大值会根据最近最少使用算法清除缓存来限制缓存大小

            // LogCat是否输出异常日志, 异常日志可以快速定位网络请求错误
            setDebug(BuildConfig.DEBUG)

            // AndroidStudio OkHttp Profiler 插件输出网络日志
            addInterceptor(LogRecordInterceptor(BuildConfig.DEBUG))

            // 添加持久化Cookie管理
            cookieJar(PersistentCookieJar(this@TestApplication))

            // 仅开发模式启用通知栏监听网络日志, 该框架存在下载大文件时内存溢出崩溃请等待官方修复 https://github.com/ChuckerTeam/chucker/issues/1068
//            if (BuildConfig.DEBUG) {
//                addInterceptor(
//                    ChuckerInterceptor.Builder(this@TestApplication)
//                        .collector(ChuckerCollector(this@TestApplication))
//                        .maxContentLength(250000L)
//                        .redactHeaders(emptySet())
//                        .alwaysReadResponseBody(false)
//                        .build()
//                )
//            }

            // 添加请求拦截器, 可配置全局/动态参数
//            setRequestInterceptor(GlobalHeaderInterceptor())
//
//            // 数据转换器
//            setConverter(SerializationConverter())
//
//            // 自定义全局加载对话框
//            setDialogFactory {
//                BubbleDialog(it, "加载中....")
//            }
        }
//        BRV.modelId = BR
    }
}