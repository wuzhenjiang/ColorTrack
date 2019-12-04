package test.okay.com.demo_view04

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.widget.TextView
import com.okay.test.demo_view04.R


/**
 * @author wzj
 * @date 2019/12/3
 */
class ColorTrackTextView :TextView{
    private var attrs:AttributeSet? =null
    constructor(context: Context, attrs: AttributeSet?=null):this(context, attrs,0)

    constructor(context: Context, attrs: AttributeSet?=null, defStyleAttr: Int=0) : super(context, attrs, defStyleAttr){
        this.attrs = attrs
    }

    private var mOriginColor = Color.BLACK
    private var mChangeColor = Color.RED

    private val mOriginPaint: Paint
    private val mChangePaint: Paint

    private var mCurrentDirection = DIRECTION.LEFT_TO_RIGHT
    private var mCurrentPercentage = 0f

    enum class DIRECTION {
        LEFT_TO_RIGHT, RIGHT_TO_LEFT
    }

    init {
        var array = context.obtainStyledAttributes(this.attrs, R.styleable.ColorTrackTextView)
        mOriginColor = array.getColor(R.styleable.ColorTrackTextView_originColor, mOriginColor)
        mChangeColor = array.getColor(R.styleable.ColorTrackTextView_changeColor, mChangeColor)
        array.recycle()

        mOriginPaint = getPaintByColor(mOriginColor)
        mChangePaint = getPaintByColor(mChangeColor)
    }

    private fun getPaintByColor(color: Int): Paint {
        var paint = Paint()
        paint.isAntiAlias = true
        paint.textSize = 50f
        paint.color = color
        return paint
    }

    override fun onDraw(canvas: Canvas) {

//        if (mCurrentDirection == DIRECTION.LEFT_TO_RIGHT) {
//            drawText(canvas, mOriginPaint,0,(width*mCurrentPercentage).toInt() )
//            drawText(canvas, mChangePaint,(width*mCurrentPercentage).toInt(),width )
//        } else {
//            drawText(canvas, mChangePaint,0,(width*(1-mCurrentPercentage)).toInt() )
//            drawText(canvas, mOriginPaint,(width*(1-mCurrentPercentage)).toInt(),width )
//        }

        val middle = (mCurrentPercentage * width).toInt()

        // 从左变到右
        if (mCurrentDirection === DIRECTION.LEFT_TO_RIGHT) {  // 左边是红色右边是黑色
            // 绘制变色
            drawText(canvas, mChangePaint, 0, middle)
            drawText(canvas, mOriginPaint, middle, width)
        } else {
            // 右边是红色左边是黑色
            drawText(canvas, mChangePaint, width - middle, width)
            // 绘制变色
            drawText(canvas, mOriginPaint, 0, width - middle)
        }
    }


    private fun drawText(canvas: Canvas, paint: Paint, start: Int, end: Int) {
        canvas.save()
        var clipRect = Rect(start, 0, end, height)
        canvas.clipRect(clipRect)
        var rect = Rect()
        paint.getTextBounds(text.toString(), 0, text.length, rect)
        var fontMetricsInt = mOriginPaint.fontMetricsInt
        var dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom
        var baseline = height / 2f + dy
        canvas.drawText(text.toString(), width / 2f - rect.width() / 2f, baseline, paint)
        canvas.restore()
    }

    fun setDirection(direction: DIRECTION) {
        mCurrentDirection = direction
    }

    fun setPercentage(percentage: Float) {
        mCurrentPercentage = percentage
        invalidate()
    }

    fun setChangeColor(changeColor: Int) {
        this.mChangePaint.color = changeColor
    }

    fun setOriginColor(originColor: Int) {
        this.mOriginPaint.color = originColor
    }
}