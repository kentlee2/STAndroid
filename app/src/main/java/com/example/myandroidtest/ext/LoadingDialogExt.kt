package com.example.myandroidtest.ext

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.preference.PreferenceManager
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.example.myandroidtest.R


/**
 * @author : hgj
 * @date : 2020/6/28
 */

//loading框
private var loadingDialog: MaterialDialog? = null

/**
 * 打开等待框
 */
fun AppCompatActivity.showLoadingExt(message: String = "请求网络中") {
    if (!this.isFinishing) {
        if (loadingDialog == null) {
            loadingDialog = MaterialDialog(this)
                    .cancelable(true)
                    .cancelOnTouchOutside(false)
                    .cornerRadius(12f)
                    .customView(R.layout.progress_dialog_view)
                    .lifecycleOwner(this)
            loadingDialog?.getCustomView()?.run {
                this.findViewById<TextView>(R.id.loading_tips).text = message
                this.findViewById<ProgressBar>(R.id.progressBar).indeterminateTintList = getOneColorStateList(this@showLoadingExt)
            }
        }
        loadingDialog?.show()
    }
}

/**
 * 打开等待框
 */
fun Fragment.showLoadingExt(message: String = "请求网络中") {
    activity?.let {
        if (!it.isFinishing) {
            if (loadingDialog == null) {
                loadingDialog = MaterialDialog(it)
                    .cancelable(true)
                    .cancelOnTouchOutside(false)
                    .cornerRadius(12f)
                    .customView(R.layout.progress_dialog_view)
                    .lifecycleOwner(this)
                loadingDialog?.getCustomView()?.run {
                    this.findViewById<TextView>(R.id.loading_tips).text = message
                    this.findViewById<ProgressBar>(R.id.progressBar).indeterminateTintList = getOneColorStateList(it)
                }
            }
            loadingDialog?.show()
        }
    }
}
/**
 * 获取当前主题颜色
 */
fun getColor(context: Context): Int {
    val setting = PreferenceManager.getDefaultSharedPreferences(context)
    val defaultColor = ContextCompat.getColor(context, R.color.colorPrimary)
    val color = setting.getInt("color", defaultColor)
    return if (color != 0 && Color.alpha(color) != 255) {
        defaultColor
    } else {
        color
    }
}
fun getOneColorStateList(context: Context): ColorStateList {
    val colors = intArrayOf(getColor(context))
    val states = arrayOfNulls<IntArray>(1)
    states[0] = intArrayOf()
    return ColorStateList(states, colors)
}

/**
 * 关闭等待框
 */
fun Activity.dismissLoadingExt() {
    loadingDialog?.dismiss()
    loadingDialog = null
}

/**
 * 关闭等待框
 */
fun Fragment.dismissLoadingExt() {
    loadingDialog?.dismiss()
    loadingDialog = null
}
