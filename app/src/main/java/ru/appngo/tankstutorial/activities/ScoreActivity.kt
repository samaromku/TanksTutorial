package ru.appngo.tankstutorial.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import ru.appngo.tankstutorial.R

const val SCORE_REQUEST_CODE = 100

class ScoreActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_SCORE = "extra_score"

        fun createIntent(context: Context, score: Int): Intent {
            return Intent(context, ScoreActivity::class.java)
                .apply {
                    putExtra(EXTRA_SCORE, score)
                }
        }
    }

    var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)
        score = intent.getIntExtra(EXTRA_SCORE, 0)
    }

    override fun onBackPressed() {
        setResult(Activity.RESULT_OK)
        finish()
    }
}
