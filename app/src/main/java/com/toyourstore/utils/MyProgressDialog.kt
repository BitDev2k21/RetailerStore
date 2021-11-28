package com.toyourstore.utils

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.animation.Animation
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams
import com.toyourstore.R


class MyProgressDialog(context: Context, drawableId: Int) : Dialog(context) {

    private val iv: ImageView

    init {
        // TODO Auto-generated constructor stub
        val wlmp = window!!.attributes
        wlmp.gravity = Gravity.CENTER
        window!!.attributes = wlmp
        window!!.setBackgroundDrawableResource(R.drawable.progress_bg)
        setTitle(null)
        val layout = LinearLayout(context)
        layout.orientation = LinearLayout.VERTICAL
        val params = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        iv = ImageView(context)
        iv.setImageResource(drawableId)
        layout.addView(iv, params)
        addContentView(layout, params)
        setCancelable(false)
    }

    override fun show() {
        super.show()
        val anim = RotateAnimation(
            0.0f,
            360.0f,
            Animation.RELATIVE_TO_SELF,
            .5f,
            Animation.RELATIVE_TO_SELF,
            .5f
        )
        anim.interpolator = LinearInterpolator() as Interpolator?
        anim.repeatCount = Animation.INFINITE
        anim.duration = 1500
        iv.animation = anim
        iv.startAnimation(anim)
    }

}