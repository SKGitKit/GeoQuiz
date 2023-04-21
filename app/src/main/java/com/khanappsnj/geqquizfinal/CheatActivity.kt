package com.khanappsnj.geqquizfinal

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import com.khanappsnj.geqquizfinal.databinding.ActivityCheatBinding

private const val TAG = "CheatActivity"
private const val EXTRA_ANSWER_IS_TRUE =
    "answer_is_true"

const val EXTRA_ANSWER_SHOWN = "answer_shown"

class CheatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheatBinding
    private val cheatViewModel :  CheatViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cheatViewModel.setAnswer(intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE,false))

        binding.showAnswerButton.setOnClickListener {
            val answerText = when {
                cheatViewModel.getAnswer -> R.string.True
                else -> R.string.False
            }
            binding.answerTextView.setText(answerText)
            cheatViewModel.cheated()
            setAnswerShownResult(cheatViewModel.getCheater)
        }
        setAnswerShownResult(cheatViewModel.getCheater)
    }

    private fun setAnswerShownResult(isAnswerShown: Boolean){
        val data = Intent().apply{
            putExtra(EXTRA_ANSWER_SHOWN,isAnswerShown)
        }
        Log.d(TAG,"Setting Result to true")
        setResult(Activity.RESULT_OK,data)
    }

    companion object {
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }
}