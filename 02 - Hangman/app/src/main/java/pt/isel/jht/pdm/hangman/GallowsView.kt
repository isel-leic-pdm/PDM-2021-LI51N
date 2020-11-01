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
    private val drawFunctions = arrayOf(
        ::drawBorder,
        ::drawGallows,
        ::drawHead,
        ::drawBody,
        ::drawLeftArm,
        ::drawRightArm,
        ::drawLeftLeg,
        ::drawRightLeg
    )

    var steps = 0
        set(st: Int) {
            field = st
            invalidate()
        }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        (0..steps).forEach { idx -> drawFunctions[idx](canvas) }
    }

    private fun Int.percent(pc: Int) = this.toFloat() / 100 * pc

    private fun ink(color: Int, style: Paint.Style): Paint {
        val paint = Paint()
        paint.color = color
        paint.style = style
        paint.strokeWidth = 5.0F
        return paint
    }

    private fun drawBorder(canvas: Canvas) =
        canvas.drawRect(0.0F, 0.0F, width.toFloat() - 1, height.toFloat() - 1, borderBlack)

    private fun drawGallows(canvas: Canvas) {
        canvas.drawRect(width.percent(25), height.percent(78), width.percent(75), height.percent(87), filledBlack)
        canvas.drawRect(width.percent(65), height.percent(15), width.percent(70), height.percent(78), filledBlack)
        canvas.drawRect(width.percent(40), height.percent(15), width.percent(65), height.percent(20), filledBlack)
        canvas.drawRect( width.percent(44), height.percent(20), width.percent(46), height.percent(30), filledBlack)
    }

    private fun drawHead(canvas: Canvas) =
        canvas.drawCircle(width.percent(45), height.percent(35), width.percent(5), borderBlack)

    private fun drawBody(canvas: Canvas) =
        canvas.drawLine(width.percent(45), height.percent(40), width.percent(45), height.percent(58), filledBlack)

    private fun drawLeftArm(canvas: Canvas) =
        canvas.drawLine(width.percent(35), height.percent(40), width.percent(45), height.percent(45), filledBlack)

    private fun drawRightArm(canvas: Canvas) =
        canvas.drawLine(width.percent(45), height.percent(45), width.percent(55), height.percent(40), filledBlack    )

    private fun drawLeftLeg(canvas: Canvas) =
        canvas.drawLine(width.percent(45), height.percent(58), width.percent(38), height.percent(72), filledBlack)

    private fun drawRightLeg(canvas: Canvas) =
        canvas.drawLine(width.percent(45), height.percent(58), width.percent(52), height.percent(72), filledBlack)
}
