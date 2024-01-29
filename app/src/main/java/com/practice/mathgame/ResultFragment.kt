package com.practice.mathgame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat.finishAffinity

class ResultFragment : Fragment() {

    private lateinit var scoreResult: TextView
    private lateinit var buttonAgain: Button
    private lateinit var buttonExit: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initElements(view)
        endGame()

    }

    private fun endGame() {
        val score = arguments?.getInt("score")
        scoreResult.text = "Your score is: $score"

        buttonAgain.setOnClickListener {
            val mainFragment = MainFragment.newInstance()
            (requireActivity() as MainActivity2).addFragment(mainFragment)
        }

        buttonExit.setOnClickListener {
            finishAffinity(requireActivity() as MainActivity2)
        }
    }

    private fun initElements(view: View) {
        scoreResult = view.findViewById(R.id.scoreResult)
        buttonAgain = view.findViewById(R.id.buttonAgain)
        buttonExit = view.findViewById(R.id.buttonExit)
    }

    companion object {
        fun newInstance(score: Int): ResultFragment {
            val resultFragment = ResultFragment()
            val bundle = Bundle()
            bundle.putInt("score", score)
            resultFragment.arguments = bundle

            return resultFragment
        }
    }

}