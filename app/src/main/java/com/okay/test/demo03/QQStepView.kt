package com.okay.test.demo03

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

/**
 * Email 240336124@qq.com
 * Created by Darren on 2017/5/20.
 * Version 1.0
 * Description: 仿QQ运动步数
 */
class QQStepView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var mOuterColor = Color.RED
    private var mInnerColor = Color.BLUE
    private var mBorderWidth = 20// 20px
//    private val mStepTextSize: Int
//    private val mStepTextColor: Int

    private val mOutPaint: Paint
    private val mInnerPaint: Paint
    private val mTextPaint: Paint

    // 总共的，当前的步数
    private var mStepMax = 0
    private var mCurrentStep = 0

    init {

        // 1.分析效果；
        // 2.确定自定义属性，编写attrs.xml
        // 3.在布局中使用
        // 4.在自定义View中获取自定义属性
//        val array = context.obtainStyledAttributes(attrs, R.styleable.QQStepView)
//        mOuterColor = array.getColor(R.styleable.QQStepView_outerColor, mOuterColor)
//        mInnerColor = array.getColor(R.styleable.QQStepView_innerColor, mInnerColor)
//        mBorderWidth =
//            array.getDimension(R.styleable.QQStepView_borderWidth, mBorderWidth.toFloat()).toInt()
//        mStepTextSize =
//            array.getDimensionPixelSize(R.styleable.QQStepView_stepTextSize, mStepTextSize)
//        mStepTextColor = array.getColor(R.styleable.QQStepView_stepTextColor, mStepTextColor)
//        array.recycle()

        mOutPaint = Paint()
        mOutPaint.isAntiAlias = true
        mOutPaint.strokeWidth = mBorderWidth.toFloat()
        mOutPaint.color = mOuterColor
        mOutPaint.strokeCap = Paint.Cap.ROUND
        mOutPaint.style = Paint.Style.STROKE// 画笔空心

        mInnerPaint = Paint()
        mInnerPaint.isAntiAlias = true
        mInnerPaint.strokeWidth = mBorderWidth.toFloat()
        mInnerPaint.color = mInnerColor
        mInnerPaint.strokeCap = Paint.Cap.ROUND
        mInnerPaint.style = Paint.Style.STROKE// 画笔空心


        mTextPaint = Paint()
        mTextPaint.isAntiAlias = true
//        mTextPaint.color = mStepTextColor
//        mTextPaint.textSize = mStepTextSize.toFloat()
        // 5.onMeasure()
        // 6.画外圆弧 ，内圆弧 ，文字
        // 7.其他
    }


    // 5.onMeasure()
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // 调用者在布局文件中可能  wrap_content
        // 获取模式 AT_MOST  40DP

        // 宽度高度不一致 取最小值，确保是个正方形
        val width = View.MeasureSpec.getSize(widthMeasureSpec)
        val height = View.MeasureSpec.getSize(heightMeasureSpec)

        setMeasuredDimension(
            if (width > height) height else width,
            if (width > height) height else width
        )
    }

    // 6.画外圆弧 ，内圆弧 ，文字
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 6.1 画外圆弧    分析：圆弧闭合了  思考：边缘没显示完整  描边有宽度 mBorderWidth  圆弧

        // int center = getWidth()/2;
        // int radius = getWidth()/2 - mBorderWidth/2;
        // RectF rectF = new RectF(center-radius,center-radius
        // ,center+radius,center+radius);

        val rectF = RectF(
            (mBorderWidth / 2).toFloat(),
            (mBorderWidth / 2).toFloat(),
            (width - mBorderWidth / 2).toFloat(),
            (height - mBorderWidth / 2).toFloat()
        )
        // 研究研究

        canvas.drawArc(rectF, 135f, 270f, false, mOutPaint)

        if (mStepMax == 0) return
        // 6.2 画内圆弧  怎么画肯定不能写死  百分比  是使用者设置的从外面传
        val sweepAngle = mCurrentStep.toFloat() / mStepMax
        canvas.drawArc(rectF, 135f, sweepAngle * 270, false, mInnerPaint)

        // 6.3 画文字
        val stepText = mCurrentStep.toString() + ""
        val textBounds = Rect()
        mTextPaint.getTextBounds(stepText, 0, stepText.length, textBounds)
        val dx = width / 2 - textBounds.width() / 2
        // 基线 baseLine
        val fontMetrics = mTextPaint.fontMetricsInt
        val dy = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom
        val baseLine = height / 2 + dy
        canvas.drawText(stepText, dx.toFloat(), baseLine.toFloat(), mTextPaint)
    }

    // 7.其他 写几个方法动起来
    @Synchronized
    fun setStepMax(stepMax: Int) {
        this.mStepMax = stepMax
    }

    @Synchronized
    fun setCurrentStep(currentStep: Int) {
        this.mCurrentStep = currentStep
        // 不断绘制  onDraw()
        invalidate()
    }
}
