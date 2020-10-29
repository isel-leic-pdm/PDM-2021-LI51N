package pt.isel.jht.pdm.hangman

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class GallowsView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val filledBlack = ink(Color.BLACK, Paint.Style.FILL_AND_STROKE)
    private val borderBlack = ink(Color.BLACK, Paint.Style.STROKE)

    var steps = 0
        set(st: Int) {
            field = st
            invalidate()
        }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawRect(0.0F, 0.0F, width.toFloat() - 1, height.toFloat() - 1, borderBlack)
        if (steps <= 0) return

        canvas.drawRect(width.percent(25), height.percent(78), width.percent(75), height.percent(87), filledBlack)
        canvas.drawRect(width.percent(65), height.percent(15), width.percent(70), height.percent(78), filledBlack)
        canvas.drawRect(width.percent(40), height.percent(15), width.percent(65), height.percent(20), filledBlack)
        canvas.drawRect(width.percent(44), height.percent(20), width.percent(46), height.percent(30), filledBlack)
        if (steps == 1) return

        canvas.drawCircle(width.percent(45), height.percent(35), width.percent(5), borderBlack)
        if (steps == 2) return

        canvas.drawLine(width.percent(45), height.percent(40), width.percent(45), height.percent(58), filledBlack)
        if (steps == 3) return

        canvas.drawLine(width.percent(35), height.percent(40), width.percent(45), height.percent(45), filledBlack)
        if (steps == 4) return

        canvas.drawLine(width.percent(45), height.percent(45), width.percent(55), height.percent(40), filledBlack)
        if (steps == 5) return

        canvas.drawLine(width.percent(45), height.percent(58), width.percent(38), height.percent(72), filledBlack)
        if (steps == 6) return

        canvas.drawLine(width.percent(45), height.percent(58), width.percent(52), height.percent(72), filledBlack)
    }

    private fun Int.percent(pc: Int) = this.toFloat()/100*pc

    private fun ink(color: Int, style: Paint.Style) : Paint {
        val paint = Paint()
        paint.color = color
        paint.style = style
        paint.strokeWidth = 5.0F
        return paint
    }
}
