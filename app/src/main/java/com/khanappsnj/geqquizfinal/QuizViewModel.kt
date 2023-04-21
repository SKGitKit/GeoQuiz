package com.khanappsnj.geqquizfinal

import android.util.Log
import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"
const val IS_CHEATER_KEY = "IS_CHEATER_KEY"
const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"
const val CHEAT_TOKENS_KEY ="CHEAT_TOKEN_KEY"
const val MAX_TOKENS = 3

class QuizViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    private var currentIndex
    get() = savedStateHandle.get(CURRENT_INDEX_KEY) ?: 0
    set(value) = savedStateHandle.set(CURRENT_INDEX_KEY,value)

    private var cheatCounter
        get() = savedStateHandle.get(CHEAT_TOKENS_KEY)?: MAX_TOKENS
        set(value) = savedStateHandle.set(CHEAT_TOKENS_KEY,value)

    private var score = 0.0
    private var answered = 0


    private val questionBank = listOf(
        Question(R.string.question_oceans, true,answered = false,cheated = false),
        Question(R.string.question_mideast, false,answered = false,cheated = false),
        Question(R.string.question_africa, false,answered = false,cheated = false),
        Question(R.string.question_americas, true, answered = false,cheated = false),
        Question(R.string.question_asia, true,answered = false,cheated = false))

    val currentQuestionAnswer: Boolean
        get() =  questionBank[currentIndex].answer

    val currentQuestionText : Int
        get() = questionBank[currentIndex].textResId

    val currentQuestionAnswered : Boolean
        get() = questionBank[currentIndex].answered

    val finalScoreTally : Boolean
        get() = answered == questionBank.size

    val getCurrentIndex : Int
        get() = currentIndex

    val getScore : Double
        get() = score

    val isCheater: Boolean
        get() = questionBank[currentIndex].cheated

    val getAnswered : Int
        get() = answered

    val getCheaterCounter : Int
        get() = cheatCounter

    fun moveToPrev () {
        when(currentIndex){
            0 -> currentIndex = questionBank.lastIndex
            else -> currentIndex -= 1
        }
    }

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun addScore() = score++
    fun addAnswered() = answered++
    fun setAnswered() {
        questionBank[currentIndex].answered = true
    }
    fun setCheated(value : Boolean){
        if(value) {
            cheatCounter--
            questionBank[currentIndex].cheated = value
        }
    }





}