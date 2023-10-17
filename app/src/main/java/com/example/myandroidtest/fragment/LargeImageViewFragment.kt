package com.example.myandroidtest.fragment

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.davemorrissey.labs.subscaleview.ImageSource
import com.example.myandroidtest.R
import com.example.myandroidtest.databinding.FragmentLargeViewBinding
import com.example.myandroidtest.utils.Utils


/**
 * @author andy
 * @date 2023/10/16
 */
class LargeImageViewFragment : Fragment() {

    private var _binding: FragmentLargeViewBinding? = null
    var url = "http://img-blog.csdnimg.cn/98fde8ba229541dc85a9f0f1be8f9b6c.jpeg#pic_center"
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLargeViewBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        Glide.with(this).asBitmap().load(R.mipmap.test3).format(DecodeFormat.PREFER_RGB_565).listener(object : RequestListener<Bitmap> {
//            override fun onLoadFailed(
//                e: GlideException?, model: Any?, target: Target<Bitmap>, isFirstResource: Boolean
//            ): Boolean {
//                return false
//            }
//
//            override fun onResourceReady(
//                resource: Bitmap,
//                model: Any,
//                target: Target<Bitmap>?,
//                dataSource: DataSource,
//                isFirstResource: Boolean
//            ): Boolean {
//                val width: Int = resource.width
//                val height: Int = resource.height
//                Log.d("LargeImageViewFragment", "图片显示宽: $width")
//                Log.d("LargeImageViewFragment", "图片显示高: $height")
//                val textSize = "图片大小：" + Utils.getFormatSize(resource.byteCount.toDouble())
//                binding.tv.text = textSize
//                return false
//            }
//
//        }).into(binding.imageView)
        binding.imageView.setLongImages(url)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}