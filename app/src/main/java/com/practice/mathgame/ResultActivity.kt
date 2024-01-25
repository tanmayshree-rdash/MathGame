package com.practice.mathgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ResultActivity : AppCompatActivity() {

    lateinit var scoreResult : TextView
    lateinit var buttonAgain : Button
    lateinit var buttonExit : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        scoreResult = findViewById(R.id.scoreResult)
        buttonAgain = findViewById(R.id.buttonAgain)
        buttonExit = findViewById(R.id.buttonExit)

        val score = intent.getIntExtra("score", 0)
        scoreResult.text = "Your score is: $score"

        buttonAgain.setOnClickListener {
            val intent = Intent(this@ResultActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        buttonExit.setOnClickListener {
            finishAffinity()
        }
    }
}