package com.practice.mathgame

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlin.math.abs
import kotlin.random.Random

class GameFragment : Fragment() {

    private lateinit var scoreValue: TextView
    lateinit var lifeValue: TextView
    private lateinit var timeValue: TextView

    lateinit var question: TextView
    private lateinit var answer: EditText

    private lateinit var buttonOk: Button
    private lateinit var buttonNext: Button

    private var correctAnswer: Int = 0
    private var userScore: Int = 0
    var userLife: Int = 3

    private lateinit var timer: CountDownTimer
    private val timePerQuestion: Long = 20000
    var userTimeLeft: Long = timePerQuestion

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initElements(view)
        startGame()

    }

    private fun startGame() {
        val operator = arguments?.getChar("operator")
        operator?.let { continueGame(it) }

        pressOkButton()
        operator?.let { pressNextButton(it) }
    }

    private fun pressOkButton() {
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
                Toast.makeText(
                    requireActivity() as MainActivity2,
                    "Answer cannot be empty.",
                    Toast.LENGTH_LONG
                ).show()
            }

        }
    }

    private fun pressNextButton(operator: Char) {
        buttonNext.setOnClickListener {

            pauseTimer()
            resetTimer()
            continueGame(operator)
            answer.setText("")

        }
    }

    private fun continueGame(operator: Char) {

        if (userLife == 0) {
            val resultFragment = ResultFragment.newInstance(userScore)
            (requireActivity() as MainActivity2).addFragment(resultFragment)
        }

        buttonOk.isEnabled = true

        val num1: Int = Random.nextInt(0, 100)
        val num2: Int = Random.nextInt(0, 100)

        correctAnswer = when (operator) {
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

    private fun pauseTimer() {
        timer.cancel()
        buttonOk.isEnabled = false
    }

    private fun resetTimer() {
        userTimeLeft = timePerQuestion
        updateText()
    }

    private fun updateText() {
        val remainingTime: Int = (userTimeLeft / 1000).toInt()
        timeValue.text = String.format("%2d", remainingTime)
    }

    private fun initElements(view: View) {
        scoreValue = view.findViewById(R.id.scoreValue)
        lifeValue = view.findViewById(R.id.lifeValue)
        timeValue = view.findViewById(R.id.timeValue)

        question = view.findViewById(R.id.question)
        answer = view.findViewById(R.id.answer)

        buttonOk = view.findViewById(R.id.buttonOk)
        buttonNext = view.findViewById(R.id.buttonNext)
    }

    companion object {
        fun newInstance(operator: Char): GameFragment {
            val gameFragment = GameFragment()
            val bundle = Bundle()
            bundle.putChar("operator", operator)
            gameFragment.arguments = bundle
            return gameFragment
        }
    }

}