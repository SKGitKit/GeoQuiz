package com.khanappsnj.geqquizfinal

import android.app.Activity
import android.app.Instrumentation.ActivityResult
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import com.khanappsnj.geqquizfinal.databinding.ActivityMainBinding

/*
Need to add:
1. Score on top
2. Image for when correct and incorrect
3. Better landscape layout
4. Restart button
5. Better Overall design and UI
6. Gather questions from online source rather than hardcoded
 */

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var linearLayout: LinearLayout
    private lateinit var binding: ActivityMainBinding

    private val quizViewModel: QuizViewModel by viewModels()

    private val cheatLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
            quizViewModel.setCheated(result.data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false)
            binding.tokenTextView.text = quizViewModel.getCheaterCounter.toString()
            if(quizViewModel.getCheaterCounter == 0) binding.cheatButton.isEnabled = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Current Index is : ${quizViewModel.getCurrentIndex}")
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        trueButton = binding.trueButton
        falseButton = binding.falseButton
        linearLayout = binding.quizLayout

        if(quizViewModel.getCheaterCounter == 0) binding.cheatButton.isEnabled = false
        binding.tokenTextView.setText(quizViewModel.getCheaterCounter.toString())

        if (quizViewModel.currentQuestionAnswered) {
            Log.d(TAG, "Current Answered is : ${quizViewModel.currentQuestionAnswered}")
            buttonsClickable(falseButton, trueButton)
        }

        binding.questionTextView.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }

        trueButton.setOnClickListener {
            checkAnswer(true)
            buttonsClickable(falseButton, trueButton)
//            Toast.makeText(
//                this,
//                R.string.correct_toast,
//                Toast.LENGTH_SHORT
//            ).show()
        }

        falseButton.setOnClickListener {
            checkAnswer(false)
            buttonsClickable(falseButton, trueButton)
//            Toast.makeText(
//                this,
//                R.string.incorrect_toast,
//                Toast.LENGTH_SHORT
//            ).show()
        }

        binding.nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
            buttonsClickable(
                falseButton,
                trueButton
            )
            Log.d(TAG, "Current Index is : ${quizViewModel.getCurrentIndex}")
        }

        binding.prevButton.setOnClickListener {
            quizViewModel.moveToPrev()
            updateQuestion()
            buttonsClickable(falseButton, trueButton)
            Log.d(TAG, "Current Index is : ${quizViewModel.getCurrentIndex}")

        }

        binding.cheatButton.setOnClickListener {
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this, answerIsTrue)
            cheatLauncher.launch(intent)

        }

        updateQuestion()
    }



    private fun buttonsClickable(b1: Button, b2: Button) {
        when (quizViewModel.currentQuestionAnswered) {
            true -> {
                b1.isEnabled = false; b2.isEnabled = false
            }
            false -> {
                b1.isEnabled = true; b2.isEnabled = true
            }
        }
    }

    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        binding.questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val messageResId: Int
        if (quizViewModel.isCheater) {
            messageResId = R.string.judgment_toast
            quizViewModel.addScore()
        } else if (quizViewModel.currentQuestionAnswer == userAnswer) {
            messageResId = R.string.correct_toast
            quizViewModel.addScore()
        } else {
            messageResId = R.string.incorrect_toast
        }
        Snackbar.make(
            linearLayout,
            messageResId,
            Snackbar.LENGTH_SHORT
        ).show()
        quizViewModel.addAnswered()
        quizViewModel.setAnswered()

        if (quizViewModel.finalScoreTally) {
            Toast.makeText(
                this,
                "Your Score is : ${quizViewModel.getScore / quizViewModel.getAnswered * 100}",
                Toast.LENGTH_LONG
            ).show()
        }
    }


    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause Called")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart Called")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart Called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop Called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume Called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy Called")
    }

}
