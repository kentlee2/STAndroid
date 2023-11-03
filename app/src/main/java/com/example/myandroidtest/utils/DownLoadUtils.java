package com.example.myandroidtest.utils;


import android.content.Context;
import android.net.Uri;

import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.common.AbsEntity;
import com.arialyy.aria.core.common.HttpOption;
import com.arialyy.aria.core.download.m3u8.M3U8VodOption;
import com.arialyy.aria.core.processor.IBandWidthUrlConverter;
import com.arialyy.aria.core.processor.IHttpFileLenAdapter;
import com.arialyy.aria.core.processor.IKeyUrlConverter;
import com.arialyy.aria.core.processor.IVodTsUrlConverter;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.LogUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class DownLoadUtils {
    private static final String TAG = DownLoadUtils.class.getName();

    /**
     * 创建下载任务，开始下载
     * @param title
     * @param url
     * @param context
     * @return
     */
    public static long downLoadMv(String title, String url, Context context) {
        String parentPath = FileUtil.getSystemDownloadPath() + "/" + context.getPackageName();
        FileUtils.createOrExistsDir(parentPath);
        String videoPath = parentPath + "/" + title + ".mp4";
        LogUtils.d("本地地址：" + videoPath);
        long downloadId = Aria.download(context)
                .load(url) // 设置点播文件下载地址
                .setFilePath(videoPath) // 设置点播文件保存路径
                .ignoreFilePathOccupy()
                .ignoreCheckPermissions()
                .option(getHttpOption())
                .m3u8VodOption(getM3u8Option())   // 调整下载模式为m3u8点播
                .create();
        return downloadId;
    }


    public static AbsEntity getDownloadEntity(String url, Context context) {
        List<AbsEntity> temps = Aria.download(context).getTotalTaskList();
        AtomicReference<AbsEntity> downloadEntity = new AtomicReference<>();
        if (temps != null && !temps.isEmpty()) {
            for (AbsEntity entity : temps) {
                if (entity.getKey().equals(url)) {
                    downloadEntity.set(entity);
                    break;
                }
            }
        } else {
            downloadEntity.set((AbsEntity) Aria.download(context).getDownloadEntity(url));
        }
        return downloadEntity.get();
    }




    private static HttpOption getHttpOption() {
        HttpOption httpOption = new HttpOption();
        httpOption.setFileLenAdapter(new FileLenAdapter());
        httpOption.addHeader("Connection", "keep-alive");
//        Map<String, String> map = getCommonHeadMap();
//        Set<Map.Entry<String, String>> entrySet = map.entrySet();
//        for (Map.Entry<String, String> me : entrySet) {
//            httpOption.addHeader(me.getKey(), me.getValue());
//        }

        return httpOption;
    }

    private static M3U8VodOption getM3u8Option() {
        M3U8VodOption option = new M3U8VodOption();

        option.merge(true);
        option.ignoreFailureTs();
        option.setKeyUrlConverter(new KeyUrlConverter());
        option.setMergeHandler(new TsMergeHandler());
        option.setUseDefConvert(true);
//        option.setBandWidthUrlConverter(new BandWidthUrlConverter());
        option.setVodTsUrlConvert(new VodTsUrlConverter());
        return option;
    }
    public static void resumeTask(AbsEntity entity, Context context) {
        LogUtils.e(TAG, "恢复下载");
        Aria.download(context).load(entity.getId()).ignoreCheckPermissions().m3u8VodOption(getM3u8Option()).resume(false);
    }
    public static void stopTask(AbsEntity entity, Context context) {
        LogUtils.e(TAG, "暂停下载");
        Aria.download(context).load(entity.getId()).ignoreCheckPermissions().m3u8VodOption(getM3u8Option()).stop();
    }

    public static void deleteTask(AbsEntity entity, Context context) {
        LogUtils.e(TAG, "删除任务");
        if (entity != null) {
            Aria.download(context).load(entity.getId()).ignoreCheckPermissions().cancel(true);
        }
    }

    static class FileLenAdapter implements IHttpFileLenAdapter {
        @Override
        public long handleFileLen(Map<String, List<String>> headers) {

            List<String> sLength = headers.get("Content-Length");
            if (sLength == null || sLength.isEmpty()) {
                return -1;
            }
            String temp = sLength.get(0);

            return Long.parseLong(temp);
        }
    }

    static class KeyUrlConverter implements IKeyUrlConverter {

        @Override
        public String convert(String m3u8Url, String tsListUrl, String keyUrl) {
            LogUtils.e(TAG, m3u8Url + "-----" + keyUrl);
            Uri uri = Uri.parse(m3u8Url);
            String mKeyUrl;
            if (keyUrl.startsWith("http")) {
                mKeyUrl = keyUrl;
            } else {
                mKeyUrl = m3u8Url.substring(0, m3u8Url.indexOf("index.m3u8"));
                if (mKeyUrl.endsWith("/")) {//如果前缀地址是 https://www.baidu.com/
                    if (keyUrl.startsWith("/")) {//如果keyUrl的值是：/key0.key
                        keyUrl = keyUrl.substring(1);//截取为 key0.key
                    }
                } else {//如果前缀地址是 https://www.baidu.com
                    if (!keyUrl.startsWith("/")) {//如果keyUrl的值是：key0.key
                        keyUrl = "/" + keyUrl;//拼接为 /key0.key
                    }
                }
                mKeyUrl += keyUrl;//拼接为 https://www.baidu.com/key0.key
            }
            return mKeyUrl;
        }
    }

    static class VodTsUrlConverter implements IVodTsUrlConverter {
        @Override
        public List<String> convert(String m3u8Url, List<String> tsUrls) {
            // 转换ts文件的url地址
            Uri uri = Uri.parse(m3u8Url);
            List<String> newUrls = new ArrayList<>();
            for (String url : tsUrls) {
                if (url.startsWith("http")) {
                    newUrls.add(url);
                } else {
                    //拿到m3u8前缀链接
//                    String lastPathSegment = uri.getLastPathSegment();
//                    String str = m3u8Url.substring(0, m3u8Url.indexOf(lastPathSegment));
                    String str = m3u8Url.substring(0, m3u8Url.indexOf("index.m3u8"));
                    if (str.endsWith("/")) {//如果前缀地址是 https://www.baidu.com/10000/hls/
                        if (url.startsWith("/")) {//url：/ts0.ts
                            url = url.substring(1);//截取为 ts0.ts
                        }
                    } else {//如果前缀地址是 https://www.baidu.com
                        if (!url.startsWith("/")) {//如果keyUrl的值是：ts0.ts
                            url = "/" + url;//拼接为 /ts0.ts
                        }
                    }
                    str += url;//拼接为 https://www.baidu.com/ts0.ts
                    newUrls.add(str);
                }
            }

            return newUrls; // 返回有效的ts文件url集合
        }
    }

    static class BandWidthUrlConverter implements IBandWidthUrlConverter {

        @Override
        public String convert(String m3u8Url, String bandWidthUrl) {
            Uri uri = Uri.parse(m3u8Url);
            String headUrl = uri.getScheme() + "://" + uri.getAuthority();
            if (!headUrl.endsWith("/") && !bandWidthUrl.startsWith("/")) {
                bandWidthUrl = "/" + bandWidthUrl;
            }
            return headUrl + bandWidthUrl;
        }
    }
}