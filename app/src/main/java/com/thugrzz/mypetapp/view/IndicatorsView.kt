package com.thugrzz.mypetapp.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.thugrzz.mypetapp.R
import kotlin.math.roundToInt

class IndicatorsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {

    private val colorCurrent: Int
    private val colorDefault: Int
    private val margin: Float
    private val radius: Float

    private val paint = Paint().apply {
        style = Paint.Style.FILL
        isAntiAlias = true
    }
    private val x = mutableListOf<Float>()

    var totalCount: Int = 0
        set(value) {
            field = value
            requestLayout()
            invalidate()
        }

    var currentPosition: Int = 0
        set(value) {
            field = value
            invalidate()
        }

    init {
        val typedArray = context.obtainStyledAttributes(
            attrs, R.styleable.IndicatorsView, defStyleAttr, 0
        )
        colorCurrent = typedArray.getColor(
            R.styleable.IndicatorsView_indicatorsColorCurrent, Color.GREEN
        )
        colorDefault = typedArray.getColor(
            R.styleable.IndicatorsView_indicatorsColorDefault, Color.DKGRAY
        )
        margin = typedArray.getDimension(
            R.styleable.IndicatorsView_indicatorsMargin, 0f
        )
        radius = typedArray.getDimension(
            R.styleable.IndicatorsView_indicatorsRadius, 0f
        )
        totalCount = typedArray.getInteger(
            R.styleable.IndicatorsView_indicatorsTotalCount, 0
        )
        currentPosition = typedArray.getInteger(
            R.styleable.IndicatorsView_indicatorsCurrentPosition, 0
        )
        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val count = totalCount
        val heightMeasure = (radius * 2).roundToInt()
        val widthMeasure = if (count > 0) {
            (radius * 2 + margin * (count - 1)).roundToInt()
        } else {
            getDefaultSize(suggestedMinimumWidth, widthMeasureSpec)
        }
        setMeasuredDimension(widthMeasure, heightMeasure)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        x.clear()
        x.add(radius)
        for (i in 1 until totalCount) {
            x.add(x[i - 1] + margin)
        }
    }

    override fun onDraw(canvas: Canvas) {
        for (i in 0 until x.size) {
            paint.color = if (i == currentPosition) colorCurrent else colorDefault
            canvas.drawCircle(x[i], radius, radius, paint)
        }
    }
}