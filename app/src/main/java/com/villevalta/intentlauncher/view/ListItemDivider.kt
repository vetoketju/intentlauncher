package com.villevalta.intentlauncher.view

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by villevalta on 12/9/17.
 */
class ListItemDivider(private val divider: Drawable) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)

        if (parent?.getChildAdapterPosition(view) == 0) {
            return
        }
        outRect?.top = divider.intrinsicHeight
    }

    override fun onDrawOver(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
        if (parent != null) {
            val left = parent.paddingLeft
            val right = parent.width - parent.paddingRight
            for (i in 1 until parent.childCount) {
                val child = parent.getChildAt(i)
                val params = child.layoutParams as RecyclerView.LayoutParams
                val top = child.top + params.topMargin
                val bottom = top + divider.intrinsicHeight
                divider.setBounds(left, top, right, bottom)
                divider.draw(c)
            }
        }
        super.onDraw(c, parent, state)
    }

}
