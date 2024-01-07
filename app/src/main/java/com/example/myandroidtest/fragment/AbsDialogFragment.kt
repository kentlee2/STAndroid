package com.example.myandroidtest.fragment

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.covy.common.base.dialog.BaseDialog
import com.covy.common.base.viewmodel.BaseViewModel
import com.drake.net.utils.TipUtils.toast
import com.example.myandroidtest.R
import com.example.myandroidtest.base.BaseFragment1
import com.example.myandroidtest.databinding.FragmentDialogBinding
import com.example.myandroidtest.view.dialog.MessageDialog
//dialog测试
class AbsDialogFragment : BaseFragment1<BaseViewModel, FragmentDialogBinding>(),OnClickListener {
    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initData() {
        super.initData()
       mViewBind.btnDialogMessage.setOnClickListener(this)
       mViewBind.btnDialogInput.setOnClickListener(this)
       mViewBind.btnDialogBottomMenu.setOnClickListener(this)
       mViewBind.btnDialogCenterMenu.setOnClickListener(this)
       mViewBind.btnDialogSingleSelect.setOnClickListener(this)
       mViewBind.btnDialogMoreSelect.setOnClickListener(this)
       mViewBind.btnDialogSucceedToast.setOnClickListener(this)
       mViewBind.btnDialogFailToast.setOnClickListener(this)
       mViewBind.btnDialogWarnToast.setOnClickListener(this)
       mViewBind.btnDialogWait.setOnClickListener(this)
       mViewBind.btnDialogPay.setOnClickListener(this)
       mViewBind.btnDialogAddress.setOnClickListener(this)
       mViewBind.btnDialogDate.setOnClickListener(this)
       mViewBind.btnDialogTime.setOnClickListener(this)
       mViewBind.btnDialogUpdate.setOnClickListener(this)
       mViewBind.btnDialogShare.setOnClickListener(this)
       mViewBind.btnDialogSafe.setOnClickListener(this)
       mViewBind.btnDialogCustom.setOnClickListener(this)
       mViewBind.btnDialogMulti.setOnClickListener(this)
    }
    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_dialog_message -> {

                // 消息对话框
                MessageDialog.Builder(requireContext())
                    // 标题可以不用填写
                    .setTitle("我是标题")
                    // 内容必须要填写
                    .setMessage("我是内容")
                    // 确定按钮文本
                    .setConfirm(getString(R.string.common_confirm))
                    // 设置 null 表示不显示取消按钮
                    .setCancel(getString(R.string.common_cancel))
                    // 设置点击按钮后不关闭对话框
                    //.setAutoDismiss(false)
                    .setListener(object : MessageDialog.OnListener {

                        override fun onConfirm(dialog: BaseDialog?) {
                            toast("确定了")
                        }

                        override fun onCancel(dialog: BaseDialog?) {
                            toast("取消了")
                        }
                    })
                    .show()
            }
            R.id.btn_dialog_input -> {


            }
            R.id.btn_dialog_bottom_menu -> {



            }
            R.id.btn_dialog_center_menu -> {


            }
            R.id.btn_dialog_single_select -> {



            }
            R.id.btn_dialog_more_select -> {



            }
            R.id.btn_dialog_succeed_toast -> {


            }
            R.id.btn_dialog_fail_toast -> {



            }
            R.id.btn_dialog_warn_toast -> {



            }
            R.id.btn_dialog_wait -> {



            }
            R.id.btn_dialog_pay -> {


            }
            R.id.btn_dialog_address -> {


            }
            R.id.btn_dialog_date -> {



            }
            R.id.btn_dialog_time -> {



            }
            R.id.btn_dialog_share -> {


            }
            R.id.btn_dialog_update -> {


            }
            R.id.btn_dialog_safe -> {

                // 身份校验对话框

            }
            R.id.btn_dialog_custom -> {


            }
        }
    }

}