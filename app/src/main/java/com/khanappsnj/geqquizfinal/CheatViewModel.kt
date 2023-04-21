package com.khanappsnj.geqquizfinal

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel


class CheatViewModel(private val savedStateHandle :SavedStateHandle): ViewModel() {

    private var answerIsTrue :  Boolean = false

    private var isCheater
    get() = savedStateHandle.get(IS_CHEATER_KEY) ?: false
    set(value) = savedStateHandle.set(IS_CHEATER_KEY,value)


    val getAnswer : Boolean
    get() = answerIsTrue

    val getCheater : Boolean
        get() = isCheater


    fun setAnswer(value : Boolean) { answerIsTrue = value}

    fun cheated(){isCheater = true}
}