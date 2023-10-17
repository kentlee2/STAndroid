package com.example.myandroidtest.utils

import java.math.BigDecimal

/**
 * @author mac-fl-037
 * @date 2023/10/17
 */
object Utils {
    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    fun getFormatSize(size: Double): String? {
        val kiloByte = size / 1024
        if (kiloByte < 1) {
//            return size + "Byte";
            return "0K"
        }
        val megaByte = kiloByte / 1024
        if (megaByte < 1) {
            val result1 = BigDecimal(kiloByte.toString())
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                .toPlainString() + "K"
        }
        val gigaByte = megaByte / 1024
        if (gigaByte < 1) {
            val result2 = BigDecimal(java.lang.Double.toString(megaByte))
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                .toPlainString() + "M"
        }
        val teraBytes = gigaByte / 1024
        if (teraBytes < 1) {
            val result3 = BigDecimal(java.lang.Double.toString(gigaByte))
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                .toPlainString() + "GB"
        }
        val result4 = BigDecimal(teraBytes)
        return (result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB")
    }
}