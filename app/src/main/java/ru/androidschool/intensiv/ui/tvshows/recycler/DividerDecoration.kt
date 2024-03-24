package ru.androidschool.intensiv.ui.tvshows.recycler

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.recyclerview.widget.RecyclerView

class DividerDecoration: RecyclerView.ItemDecoration() {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.argb(0xA0, 0x00, 0x00, 0x00)
        style = Paint.Style.FILL
    }

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(canvas, parent, state)
        val adapter = parent.adapter
        val parentChildCount = parent.childCount
        for (i in 0 until parentChildCount) {
            val child = parent.getChildAt(i)
            if (parent.getChildAdapterPosition(child) == adapter?.itemCount?.minus(1)) {
                continue
            }
            canvas.drawLine(
                0f,
                child.bottom.toFloat(),
                child.width.toFloat(),
                child.bottom.toFloat(),
                paint
            )
        }
    }
}