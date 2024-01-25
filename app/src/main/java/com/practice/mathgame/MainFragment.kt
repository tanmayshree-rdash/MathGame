package com.practice.mathgame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class MainFragment : Fragment() {

    private lateinit var addButton: Button
    private lateinit var subtractButton: Button
    private lateinit var multiplyButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initElements(view)
        clickEvents()

    }

    private fun clickEvents() {
        onClick(addButton, '+')
        onClick(subtractButton, '-')
        onClick(multiplyButton, '*')
    }

    private fun onClick(button: Button, operator: Char) {
        button.setOnClickListener {

            val gameFragment = GameFragment.newInstance(operator)
            (requireActivity() as MainActivity2).addFragment(gameFragment)

        }
    }

    private fun initElements(view: View) {
        addButton = view.findViewById(R.id.addButton)
        subtractButton = view.findViewById(R.id.subtractButton)
        multiplyButton = view.findViewById(R.id.multiplyButton)
    }

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

}