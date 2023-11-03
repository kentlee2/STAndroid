package com.example.myandroidtest.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.arialyy.aria.core.download.DownloadEntity
import com.covy.common.base.viewmodel.BaseViewModel


class DownloadViewModel: BaseViewModel() {
    var liveData = MutableLiveData<List<DownloadEntity>>()
    /**
     * 单任务下载的信息
     */
    fun getHttpDownloadInfo(context: Context) {
        val data = DownloadEntity.findAllData(DownloadEntity::class.java)
        if(!data.isNullOrEmpty()) {
            liveData.postValue(data)
        }
    }
}