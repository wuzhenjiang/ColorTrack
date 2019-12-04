package com.okay.test.demo03

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 *@author wzj
 *@description:
 *@date:2019-12-01 20:22
 */
class QQExerciseView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    View(context, attrs, defStyleAttr) {
    private val mDefaultWidth = 50

    private var mOutColor = Color.RED
    private var mInnerColor = Color.BLUE
    private var mBorderWidth = 12
    private var mExerciseTextSize = 20
    private var mExerciseTextColor = Color.RED
    private var mOutPaint: Paint
    private var mInnerPaint: Paint
    private var mTextPaint: Paint
    private var mMaxSize = 500
    private var mCurrentSize = 100
    private var mText: String

    public fun setMaxSize(maxSize: Int) {
        mMaxSize = maxSize
    }

    public fun setCurrentSize(currentSize: Int) {
        mCurrentSize = currentSize
        invalidate()
    }

    init {
        var obtainStyledAttributes =
            context.obtainStyledAttributes(attrs, R.styleable.QQExerciseView)
        mOutColor = obtainStyledAttributes.getColor(R.styleable.QQExerciseView_outColor, Color.RED)
        mInnerColor =
            obtainStyledAttributes.getColor(R.styleable.QQExerciseView_innerColor, Color.BLUE)
        mBorderWidth = obtainStyledAttributes.getDimension(
            R.styleable.QQExerciseView_borderWidth,
            mBorderWidth.toFloat()
        ).toInt()
        mExerciseTextSize = obtainStyledAttributes.getDimensionPixelSize(
            R.styleable.QQExerciseView_exerciseTextSize,
            mExerciseTextSize
        )
        mExerciseTextColor = obtainStyledAttributes.getColor(
            R.styleable.QQExerciseView_exerciseTextColor,
            mExerciseTextColor
        )

        mText = obtainStyledAttributes.getString(R.styleable.QQExerciseView_exerciseText)
        obtainStyledAttributes.recycle()

        mOutPaint = Paint()
        mOutPaint.color = mOutColor
        mOutPaint.strokeCap = Paint.Cap.ROUND
        mOutPaint.strokeWidth = mBorderWidth.toFloat()
        mOutPaint.style = Paint.Style.STROKE
        mOutPaint.isAntiAlias = true

        mInnerPaint = Paint()
        mInnerPaint.color = mInnerColor
        mInnerPaint.strokeCap = Paint.Cap.ROUND
        mInnerPaint.strokeWidth = mBorderWidth.toFloat()
        mInnerPaint.style = Paint.Style.STROKE
        mInnerPaint.isAntiAlias = true

        mTextPaint = Paint()
        mTextPaint.color = mExerciseTextColor
        mTextPaint.textSize = mExerciseTextSize.toFloat()
        mTextPaint.isAntiAlias = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var widthMode = MeasureSpec.getMode(widthMeasureSpec)
        var heightMode = MeasureSpec.getMode(heightMeasureSpec)

        var widthSize = MeasureSpec.getSize(widthMeasureSpec)
        var heightSize = MeasureSpec.getSize(heightMeasureSpec)

        when (widthMode) {
            MeasureSpec.AT_MOST -> {
                widthSize = mDefaultWidth
            }
        }

        when (heightMode) {
            MeasureSpec.AT_MOST -> {
                heightSize = mDefaultWidth
            }
        }

        setMeasuredDimension(
            if (widthSize > heightSize) heightSize else widthSize,
            if (widthSize > heightSize) heightSize else widthSize
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        var rectf = RectF(
            mBorderWidth / 2f,
            mBorderWidth / 2f,
            width - mBorderWidth / 2f,
            height - mBorderWidth / 2f
        )
        canvas.drawArc(rectf, 135f, 270f, false, mOutPaint)


        canvas.drawArc(rectf, 135f, mCurrentSize * 1f / mMaxSize * 270, false, mInnerPaint)

        var rect = Rect()
        mTextPaint.getTextBounds(mText, 0, mText.length, rect)
        // 基线 baseLine
        val fontMetrics = mTextPaint.fontMetricsInt
        val dy = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom
        canvas.drawText(mText, (width) / 2f - rect.width() / 2f, height / 2f + dy, mTextPaint)
    }
}