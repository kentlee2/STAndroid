package com.example.myandroidtest.utils

import android.content.Context
import android.os.Build

import android.telephony.TelephonyManager




object SystemUtil {
    /**
     * 获取SIM卡运营商
     *
     * @param context
     * @return
     */
    fun getOperators(context: Context): String? {
        val tm = context
            .getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        var operator: String? = null
        try {
            val IMSI = tm.subscriberId
            if (IMSI.isNullOrEmpty()) {
                return operator
            }
            if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
                operator = "中国移动"
            } else if (IMSI.startsWith("46001")) {
                operator = "中国联通"
            } else if (IMSI.startsWith("46003")) {
                operator = "中国电信"
            }
            return operator
        }catch (e :Exception){
            e.printStackTrace()
        }
      return ""
    }

    /**
     * 手机型号
     *
     * @return
     */
    fun getPhoneModel(): String? {
        return Build.MODEL
    }

    /**
     * 系统版本
     *
     * @return
     */
    fun getSystemVersion(): String? {
        return Build.VERSION.RELEASE
    }

}