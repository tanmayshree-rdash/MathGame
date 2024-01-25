package com.practice.mathgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    lateinit var addButton: Button
    lateinit var subtractButton: Button
    lateinit var multiplyButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addButton = findViewById(R.id.addButton)
        subtractButton = findViewById(R.id.subtractButton)
        multiplyButton = findViewById(R.id.multiplyButton)

        onClick(addButton, '+')
        onClick(subtractButton, '-')
        onClick(multiplyButton, '*')

    }

    private fun onClick(button: Button, operator: Char) {
        button.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("operator", operator)
            startActivity(intent)
        }
    }
}