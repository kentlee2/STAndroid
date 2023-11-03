package com.example.myandroidtest.utils

import android.text.TextUtils
import com.arialyy.aria.core.download.M3U8Entity
import com.arialyy.aria.core.processor.ITsMergeHandler
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class TsMergeHandler : ITsMergeHandler {

    override fun merge(m3U8Entity: M3U8Entity, tsPath: List<String>): Boolean {
        LogUtils.d("TsMergeHandler", "合并TS....")
        var pos = 0;
        try {
            val filePath = m3U8Entity.filePath
            val bufferedOutputStream = BufferedOutputStream(FileOutputStream(File(filePath)))
            val keyPath = m3U8Entity.getKeyPath()
            var keyIv = m3U8Entity.getIv()
            LogUtils.d("TsMergeHandler", "keyPath：$keyPath")
            LogUtils.d("TsMergeHandler", "keyIv：$keyIv")
            if (!TextUtils.isEmpty(keyPath)) {
                //1.获得bytes数组
                val keyBytes = File(keyPath).readBytes()
                var skey: SecretKeySpec? = null
                var iv: IvParameterSpec? = null
                if (!keyBytes.isEmpty()) {
                    //2.key数组转为SecretKeySpec对象，iv数组转为IvParameterSpec
                    skey = SecretKeySpec(keyBytes, "AES")
                }
                if (TextUtils.isEmpty(keyIv)) {
                    keyIv = ""
                }
                var ivBytes = if (keyIv.startsWith("0x")) {
                    decodeHex(keyIv.substring(2))
                } else {
                    keyIv.toByteArray()
                }
                if (ivBytes.size != 16)
                    ivBytes = ByteArray(16)
                //如果m3u8有IV标签，那么IvParameterSpec构造函数就把IV标签后的内容转成字节数组传进去
                iv = IvParameterSpec(ivBytes)
                //3. 初始化cipher
                val transformation = "AES/CBC/PKCS5Padding"
                val cipher = Cipher.getInstance(transformation)

                //4. 解密，
                tsPath.forEachIndexed { _, it ->
                    pos++
                    val tsFile = File(it)
                    if (iv == null) {
                        cipher.init(Cipher.DECRYPT_MODE, skey)
                    } else {
                        cipher.init(Cipher.DECRYPT_MODE, skey, iv)
                    }
                    //写入文件
                    val result = cipher.update(tsFile.readBytes())
                    //写入文件
                    if (result != null) {
                        bufferedOutputStream.write(result)
                    }
                    if (pos + 1 == tsPath.size) {
                        LogUtils.d("TsMergeHandler", "ts文件合并成功")
                    }
                }
                bufferedOutputStream.flush()
                bufferedOutputStream.close()
            } else {
                tsPath.forEachIndexed { _, it ->
                    pos++
                    val tsFile = File(it)
                    //写入文件
                    val result = tsFile.readBytes()
                    //写入文件
                    if (result != null) {
                        bufferedOutputStream.write(result)
                    }
                    if (pos + 1 == tsPath.size) {
                        LogUtils.d("TsMergeHandler", "ts文件合并成功")
                    }
                }
                bufferedOutputStream.flush()
                bufferedOutputStream.close()
            }

        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            LogUtils.d("TsMergeHandler", "合并错误Ts:$pos")
            ToastUtils.showShort("合并文件失败！,请重新下载")
            return false
        }
//       mergeTs(lists,newFile)
        return true
    }

    private fun mergeTs(files: ArrayList<File>, filePath: String) {
        try {
            val file = File(filePath)
            System.gc()
            if (file.exists()) file.delete() else file.createNewFile()
            val fileOutputStream = FileOutputStream(file)
            val b = ByteArray(4096)
            for (f in files) {
                val fileInputStream = FileInputStream(f)
                var len: Int
                while (fileInputStream.read(b).also { len = it } != -1) {
                    fileOutputStream.write(b, 0, len)
                }
                fileInputStream.close()
                fileOutputStream.flush()
            }
            fileOutputStream.close()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 将字符串转为16进制并返回字节数组
     */
    private fun decodeHex(input: String): ByteArray {
        val data = input.toCharArray()
        val len = data.size
        if (len and 0x01 != 0) {
            try {
                throw Exception("Odd number of characters.")
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        val out = ByteArray(len shr 1)

        try {
            var i = 0
            var j = 0
            while (j < len) {
                var f = toDigit(data[j], j) shl 4
                j++
                f = f or toDigit(data[j], j)
                j++
                out[i] = (f and 0xFF).toByte()
                i++
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return out
    }

    @Throws(Exception::class)
    private fun toDigit(ch: Char, index: Int): Int {
        val digit = Character.digit(ch, 16)
        if (digit == -1) {
            throw Exception("Illegal hexadecimal character $ch at index $index")
        }
        return digit
    }
}