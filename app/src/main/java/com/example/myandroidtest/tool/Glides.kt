package com.example.myandroidtest.tool

import android.content.Context
import android.text.TextUtils
import android.widget.ImageView
import com.blankj.utilcode.util.SizeUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class Glides {


    companion object {
        /**
         * 加载图片
         */
        @JvmStatic
        fun loadImage(coverImg: ImageView?, imageUrl: String?) {
            loadImage(coverImg, imageUrl, 8, 0)
        }

        /**
         * 加载图片，支持设置圆角角度
         */
        @JvmStatic
        fun loadImage(
            coverImg: ImageView?,
            imageUrl: String?,
            corners: Int = 0,
            errorRes: Int = 0
        ) {
            loadRoundBase64Image(
                coverImg,
                imageUrl,
                corners,
                errorRes
            )
        }

        /**
         * 加载圆角base64图片
         */
        private fun loadRoundBase64Image(
            view: ImageView?,
            base64Url: String?,
            corners: Int = 0,
            errorRes: Int = 0
        ) {
            if (TextUtils.isEmpty(base64Url)) {
                return
            }
            view?.let {
                realLoadImage(view, base64Url, corners, errorRes)
            }
        }

        /**
         * 真正加载图片
         */
        private fun realLoadImage(
            view: ImageView,
            base64Url: String?,
            corners: Int,
            errorRes: Int
        ) {
            val load = Glide.with(view).load(base64Url)
            // 圆角
            if (corners > 0) {
                val radius = SizeUtils.dp2px(corners.toFloat())
                val multi = MultiTransformation(
                    CenterCrop(),
                    RoundedCornersTransformation(radius, 0)
                )
                load.apply(RequestOptions.bitmapTransform(multi))
            }
            // 失败加载
            if (errorRes > 0) {
                load.error(errorRes)
            }
            // 根据宽高加载
            if (view.width > 0 && view.height > 0) {
                load.override(view.width, view.height)
            }
            // 加载图片
            load.into(view)
        }



        /**
         * 加载圆角图片
         *
         */
        fun loadRoundImage(
            view: ImageView?,
            imgUrl: String?,
            corners: Int,
            transformation: BitmapTransformation?
        ) {
            val multi = MultiTransformation(
                transformation,
                RoundedCornersTransformation(corners, 0)
            )
            view?.let {
                Glide.with(it).load(imgUrl).apply(RequestOptions.bitmapTransform(multi)).into(view)
            }
        }

        /**
         * 加载圆形图片
         */
        @JvmStatic
        fun loadCircleImage(base64File: String?, imageView: ImageView, placeHolder: Int) {
            if (!TextUtils.isEmpty(base64File)) {
                Glide.with(imageView.context).load(base64File)
                    .apply(RequestOptions.bitmapTransform(CircleCrop()))
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .placeholder(placeHolder)
                    .into(imageView)
            } else {
                Glide.with(imageView.context).load(placeHolder)
                    .apply(RequestOptions.bitmapTransform(CircleCrop())).placeholder(placeHolder)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(imageView)
            }
        }

        /**
         * 加载高斯模糊图片
         */
        @JvmStatic
        fun loadBlurImage(
            content: Context?,
            imageUrl: String?,
            imageView: ImageView,
            radius: Int,
            sample: Int
        ) {
            if (!TextUtils.isEmpty(imageUrl)) {
                content?.let {
                    Glide.with(it).asDrawable().load(imageUrl)
                        .apply(RequestOptions.bitmapTransform(BlurTransformation(radius, sample)))
                        .into(imageView)
                }
            }
        }

    }

}