package com.toyourstore.ui

interface DrawableClickListener  {
    enum class DrawablePosition {
        TOP, BOTTOM, LEFT, RIGHT
    }

    fun onClick(target: DrawablePosition?)

}