package com.example.myandroidtest.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import com.arialyy.annotations.Download
import com.arialyy.aria.core.Aria
import com.arialyy.aria.core.common.AbsEntity
import com.arialyy.aria.core.task.DownloadTask
import com.arialyy.aria.util.ALog
import com.arialyy.aria.util.CommonUtil
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.example.myandroidtest.base.BaseFragment
import com.example.myandroidtest.databinding.FragmentDownloadBinding
import com.example.myandroidtest.utils.DownLoadUtils
import com.example.myandroidtest.view.widget.ProgressLayout.OnProgressLayoutBtListener
import com.example.myandroidtest.viewmodel.DownloadViewModel
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import java.io.File

/**
 * 单任务下载（dataBinding)
 * @author mac-fl-037
 * @date 2023/11/2
 */
class DownloadFragment : BaseFragment<DownloadViewModel, FragmentDownloadBinding>() {

    var TAG = "Download"
    var mTaskId = 0L

    //    var downLoadUrl = "http://playertest.longtailvideo.com/adaptive/bipbop/gear4/prog_index.m3u8"
    var downLoadUrl = "https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8"
    override fun initView(savedInstanceState: Bundle?) {
        Aria.download(this).register()
        mViewModel.getHttpDownloadInfo(requireContext())
        XXPermissions.with(this)
            .permission(Permission.MANAGE_EXTERNAL_STORAGE)
            .request(object : OnPermissionCallback {
                override fun onGranted(
                    permissions: MutableList<String>,
                    allGranted: Boolean
                ) {
                   onPermissionGranted()
                }

                override fun onDenied(
                    permissions: MutableList<String>,
                    doNotAskAgain: Boolean
                ) {
                    super.onDenied(permissions, doNotAskAgain)
                    if (doNotAskAgain) {
                        ToastUtils.showShort("被永久拒绝授权，请手动授予文件权限")
                        // 如果是被永久拒绝就跳转到应用权限系统设置页面
                        XXPermissions.startPermissionActivity(requireContext(), permissions)
                    } else {
                        ToastUtils.showShort("获取权限失败，无法下载")
                    }
                }

            })

    }

    private fun onPermissionGranted() {
        mDatabind.pl.setBtListener(object : OnProgressLayoutBtListener {
            override fun create(v: View?, entity: AbsEntity?) {
                mTaskId = DownLoadUtils.downLoadMv("测试文件", downLoadUrl, context)
            }

            override fun stop(v: View?, entity: AbsEntity?) {
                DownLoadUtils.stopTask(entity, requireContext())
            }

            override fun resume(v: View?, entity: AbsEntity?) {
                DownLoadUtils.resumeTask(entity, requireContext())
            }

            override fun cancel(v: View?, entity: AbsEntity?) {
                DownLoadUtils.deleteTask(entity, requireContext())
            }

        })
    }

    override fun createObserver() {
        super.createObserver()
        mViewModel.liveData.observe(this) {
            mDatabind.pl.setInfo(it[0])
        }
    }

    @Download.onWait
    fun onWait(task: DownloadTask) {
        if (task.key == downLoadUrl) {
            LogUtils.d(TAG, "wait ==> " + task.downloadEntity.fileName)
            mDatabind.pl.setInfo(task.entity)
        }
    }

    @Download.onPre
    fun onPre(task: DownloadTask) {
        if (task.key == downLoadUrl) {
            mDatabind.pl.setInfo(task.entity)
        }
    }

    //@Override public void onTaskPre(DownloadTask task) {
    //
    //}

    //@Override public void onTaskPre(DownloadTask task) {
    //
    //}
    @Download.onTaskStart
    fun onTaskStart(task: DownloadTask) {
        if (task.key == downLoadUrl) {
            mDatabind.pl.setInfo(task.entity)
            ALog.d(TAG, "isComplete = " + task.isComplete + ", state = " + task.state)
        }
    }

    @Download.onTaskRunning
    fun onTaskRunning(task: DownloadTask) {
        if (task.key == downLoadUrl) {
            //ALog.d(TAG, "isRunning" + "; state = " + task.getEntity().getState());
            mDatabind.pl.setInfo(task.entity)
        }
    }

    //@Override public void onNoSupportBreakPoint(DownloadTask task) {
    //
    //}

    //@Override public void onNoSupportBreakPoint(DownloadTask task) {
    //
    //}
    @Download.onTaskResume
    fun onTaskResume(task: DownloadTask) {
        if (task.key == downLoadUrl) {
            ALog.d(TAG, "resume")
            mDatabind.pl.setInfo(task.entity)
        }
    }

    @Download.onTaskStop
    fun onTaskStop(task: DownloadTask) {
        if (task.key == downLoadUrl) {
            ALog.d(TAG, "stop")
            mDatabind.pl.setInfo(task.entity)
        }
    }

    @Download.onTaskCancel
    fun onTaskCancel(task: DownloadTask) {
        if (task.key == downLoadUrl) {
            mTaskId = -1
            Log.d(TAG, "cancel")
            mDatabind.pl.setInfo(task.entity)
        }
    }

    @Download.onTaskFail
    fun onTaskFail(task: DownloadTask?, e: Exception?) {
        ALog.d(TAG, "下载失败")
        ToastUtils.showShort("下载失败"+e?.message)
        if (task != null && task.key == downLoadUrl) {
            mDatabind.pl.setInfo(task.entity)
        }
    }

    @Download.onTaskComplete
    fun onTaskComplete(task: DownloadTask) {
        if (task.key == downLoadUrl) {
            ToastUtils.showShort("下载成功")
            ALog.d(TAG, "文件md5: e088677570afe2e9f847cc8159b932dd")
            ALog.d(TAG, "下载完成的文件md5: " + CommonUtil.getFileMD5(File(task.filePath)))
            mDatabind.pl.setInfo(task.entity)
            mDatabind.pl.setProgress(100)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Aria.download(this).unRegister()
    }
}