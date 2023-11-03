package com.example.myandroidtest.view.widget

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.Scroller
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.ImageViewState
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView.OnImageEventListener
import com.example.myandroidtest.R
import java.util.Random


/**
 * @author mac-fl-037
 * @date 2023/10/17
 */
class EasyTVLongView : SubsamplingScaleImageView, OnImageEventListener {
    private var mScroller: Scroller? = null
    private val scrollHeight = 200
    private var scrollY = 0
    private val defaultScale = 1.0f
    private var isFocus = false
    private val paint = Paint()
    private val vPoint = PointF()
    private var sPoint: PointF? = null
    private var bitmap: Bitmap? = null

    constructor(context: Context, attr: AttributeSet?) : super(context, attr) {
        //  initBitmap();
        initialise(context)
    }

    constructor(context: Context) : super(context) {
        //initBitmap();
        initialise(context)
    }

    private fun initialise(context: Context) {
        mScroller = Scroller(context)
        maxScale =5f
        this.onFocusChangeListener = OnFocusChangeListener { v: View?, hasFocus: Boolean ->
            isFocus = hasFocus
        }
        setOnKeyListener { v: View?, keyCode: Int, event: KeyEvent ->
            setDispatchKeyEvent(event)
            false
        }
    }

    fun setDispatchKeyEvent(event: KeyEvent) {
        if (event.action == KeyEvent.ACTION_DOWN) {
            if (event.keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                if (isFocus) {
                    startSmoothScrollUp(0, 50)
                }
            }
            if (event.keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                if (isFocus) {
                    startSmoothScrollDown(0, 50)
                }
            }
        }
    }

    /**
     * 向下滑动
     * @param desX
     * @param ms
     */
    fun startSmoothScrollDown(desX: Int, ms: Int) {
        val startX = scrollX
        val startY = getScrollY()
        scrollY = getScrollY()
        val scrollDownHeight = sHeight - scrollY - scrollHeight
        //startScroll(x起始坐标，y起始坐标，x方向偏移值，y方向偏移值，滚动时长)
        if (scrollDownHeight + scrollHeight > ScreenUtils.getScreenHeight()) {
            mScroller!!.startScroll(startX, startY, desX - startX, scrollHeight, ms)
        }
        Log.d(
            TAG,
            "----向下滑动距离---$scrollDownHeight----滑动y坐标----$startY"
        )
        invalidate()
    }

    /**
     * 向上滑动
     *
     * @param desX
     * @param ms
     */
    fun startSmoothScrollUp(desX: Int, ms: Int) {
        val startX = scrollX
        val startY = getScrollY()
        scrollY = getScrollY()
        Log.d(
            TAG,
            "向上滑动滑动x坐标$startX----滑动y坐标----$startY"
        )
        if (scrollY > SizeUtils.dp2px(60f)) {
            mScroller!!.startScroll(0, startY, desX, -scrollHeight, ms)
        }
        invalidate()
    }

    /**
     * 加载图片数据
     *
     * @param url
     */
    fun setLongImages(url: String?) {
        try {
            Glide.with(this.context)
                .asBitmap()
                .load(url)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        this@EasyTVLongView.setImage(
                            ImageSource.cachedBitmap(resource),
                            ImageViewState(defaultScale, PointF(0f, 0f), 0)
                        )
                        Log.d(
                            TAG,
                            "---图片原始宽度为---- " + resource.width + "---图片原始高度为---" + resource.height
                        )
                        this@EasyTVLongView.onImageLoaded()
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        recycle()
                    }

                })
            this@EasyTVLongView.isFocusable = true
        } catch (e: Exception) {
            e.printStackTrace()
            onImageLoadError(e)
        }
    }

    fun play() {
        val random = Random()
        if (this.isReady) {
            val maxScale = maxScale
            val minScale = minScale
            val scale: Float = random.nextFloat() * (maxScale - minScale) + minScale
            val center = PointF(random.nextInt(sWidth).toFloat(), random.nextInt(sHeight).toFloat())
            setPin(center)
            val animationBuilder = animateScaleAndCenter(scale, center)
            if (sHeight == 0) {
                animationBuilder?.withDuration(2000)?.withEasing(EASE_OUT_QUAD)
                    ?.withInterruptible(false)?.start()
            } else {
                animationBuilder?.withDuration(500)?.start()
            }
        }
    }

    fun setPin(sPin: PointF?) {
        sPoint = sPin
        initBitmap()
        invalidate()
    }

    private fun initBitmap() {
        val density = resources.displayMetrics.densityDpi.toFloat()
        bitmap = BitmapFactory.decodeResource(this.resources, R.mipmap.test2)
        val w = density / 420f * bitmap!!.width
        val h = density / 420f * bitmap!!.height
        bitmap = Bitmap.createScaledBitmap(bitmap!!, w.toInt(), h.toInt(), true)
    }

    override fun computeScroll() {
        if (mScroller!!.computeScrollOffset()) {
            scrollTo(mScroller!!.currX, mScroller!!.currY)
            postInvalidate()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (!isReady) {
            return
        }
        paint.isAntiAlias = true
        if (sPoint != null && bitmap != null) {
            sourceToViewCoord(sPoint, vPoint)
            val vX = vPoint.x - bitmap!!.width / 2
            val vY = vPoint.y - bitmap!!.height
            canvas.drawBitmap(bitmap!!, vX, vY, paint)
        }
    }

    override fun onReady() {
        Log.d(TAG, "图片资源已准备" + this.isReady)
    }

    override fun onImageLoaded() {
        Log.d(TAG, "图片已加载" + this.isImageLoaded)
    }

    override fun onPreviewLoadError(e: Exception) {
        Log.e(TAG, "图片预览失败" + e.message)
    }

    override fun onImageLoadError(e: Exception) {
        Log.e(TAG, "图片加载失败" + e.message)
    }

    override fun onTileLoadError(e: Exception) {
        Log.e(TAG, "图片平铺加载失败" + e.message)
    }

    override fun onPreviewReleased() {
        if (this.drawingCache != null) {
            Log.d(TAG, "图片预加载资源已回收" + this.drawingCache.isRecycled)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        recycle()
    }

    companion object {
        private const val TAG = "EasyLongViewLog"
    }
}