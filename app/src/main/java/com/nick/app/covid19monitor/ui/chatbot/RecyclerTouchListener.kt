package com.nick.app.covid19monitor.ui.chatbot

import android.content.Context
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerTouchListener : RecyclerView.OnItemTouchListener {

    private var gestureDetector: GestureDetector? = null
    private var clickListener: ClickListener? = null

    fun RecyclerTouchListener(
        context: Context?,
        recyclerView: RecyclerView,
        clickListener: ClickListener?
    ) {
        this.clickListener = clickListener
        gestureDetector = GestureDetector(context, object : SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                return true
            }

            override fun onLongPress(e: MotionEvent) {
                val child = recyclerView.findChildViewUnder(e.x, e.y)
                if (child != null && clickListener != null) {
                    clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child))
                }
            }
        })
    }


    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
        TODO("Not yet implemented")
    }

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        var child: View? = rv.findChildViewUnder(e.getX(), e.getY())
        if (child != null && clickListener != null && gestureDetector!!.onTouchEvent(e)) {
            clickListener!!.onClick(child, rv.getChildAdapterPosition(child))
        }
        return false
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        TODO("Not yet implemented")
    }

}