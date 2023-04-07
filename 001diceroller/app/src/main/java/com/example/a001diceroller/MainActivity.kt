package com.example.a001diceroller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rollButton: Button = findViewById(R.id.button0)
        val resultTextView: TextView = findViewById(R.id.textView0)
        Dice(420)

        rollButton.setOnClickListener {
            //val toast = Toast.makeText(this, "Dice Rolled!" , Toast.LENGTH_SHORT).show()
            rollDice()
        }

    }

    private fun rollDice() {
        val dice0 = Dice(6)
        val resultTextView: TextView = findViewById(R.id.textView0)

        val diceRoll = dice0.roll()
        resultTextView.text = diceRoll.toString()

    }
}


class Dice(private val sides: Int) {
    fun roll(): Int {
        return (1..sides).random()
    }
}