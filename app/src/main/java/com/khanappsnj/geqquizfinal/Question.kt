package com.khanappsnj.geqquizfinal

import androidx.annotation.StringRes

data class Question(
    @StringRes val textResId: Int,
    val answer: Boolean,
    var answered: Boolean,
    var cheated : Boolean
)
