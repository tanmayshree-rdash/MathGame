package com.practice.mathgame

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.time.Duration
import kotlin.math.abs
import kotlin.random.Random

class GameActivity : AppCompatActivity() {

    lateinit var scoreValue: TextView
    lateinit var lifeValue: TextView
    lateinit var timeValue: TextView

    lateinit var question: TextView
    lateinit var answer: EditText

    lateinit var buttonOk: Button
    lateinit var buttonNext: Button

    private var correctAnswer: Int = 0
    private var userScore: Int = 0
    var userLife: Int = 3

    private lateinit var timer: CountDownTimer
    private val timePerQuestion: Long = 20000
    var userTimeLeft: Long = timePerQuestion

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val operator = intent.getCharExtra("operator", '+')
        supportActionBar!!.title = when(operator) {
            '+' -> "Addition"
            '-' -> "Subtraction"
            '*' -> "Multiplication"
            else -> "No Operation"
        }

        scoreValue = findViewById(R.id.scoreValue)
        lifeValue = findViewById(R.id.lifeValue)
        timeValue = findViewById(R.id.timeValue)

        question = findViewById(R.id.question)
        answer = findViewById(R.id.answer)

        buttonOk = findViewById(R.id.buttonOk)
        buttonNext = findViewById(R.id.buttonNext)

        continueGame(operator)

        buttonOk.setOnClickListener {

            val input = answer.text.toString()
            if (input.isNotEmpty()) {

                val userAnswer = input.toInt()
                if (userAnswer == correctAnswer) {
                    question.text = "Right Answer"
                    userScore += 10
                    scoreValue.text = userScore.toString()
                } else {
                    question.text = "Wrong Answer; $correctAnswer"
                    userLife--
                    lifeValue.text = userLife.toString()
                }
                pauseTimer()

            } else {
                Toast.makeText(applicationContext, "Answer cannot be empty.", Toast.LENGTH_LONG)
                    .show()
            }

        }

        buttonNext.setOnClickListener {

            pauseTimer()
            resetTimer()
            continueGame(operator)
            answer.setText("")

        }

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun continueGame(operator: Char) {

        if(userLife==0) {
            val intent = Intent(this@GameActivity, ResultActivity::class.java)
            intent.putExtra("score", userScore)
            startActivity(intent)
            finish()
        }

        buttonOk.isEnabled = true
        buttonOk.backgroundTintList = null

        val num1: Int = Random.nextInt(0, 100)
        val num2: Int = Random.nextInt(0, 100)

        correctAnswer = when(operator) {
            '+' -> num1 + num2
            '-' -> abs(num1 - num2)
            '*' -> num1 * num2
            else -> 0
        }

        question.text = "$num1 $operator $num2"
        startTimer()

    }

    private fun startTimer() {

        timer = object : CountDownTimer(userTimeLeft, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                userTimeLeft = millisUntilFinished
                updateText()
            }

            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onFinish() {
                pauseTimer()
                resetTimer()
                updateText()

                userLife--
                lifeValue.text = userLife.toString()
                question.text = "Time Up!!"
            }

        }.start()
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun pauseTimer() {
        timer.cancel()
        buttonOk.isEnabled = false
        buttonOk.backgroundTintList = ColorStateList.valueOf(Color.GRAY)
    }

    private fun resetTimer() {
        userTimeLeft = timePerQuestion
        updateText()
    }

    private fun updateText() {
        val remainingTime: Int = (userTimeLeft / 1000).toInt()
        timeValue.text = String.format("%2d", remainingTime)
    }
}