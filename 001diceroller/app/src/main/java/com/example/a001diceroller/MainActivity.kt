package com.example.a001diceroller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val luckyNumber = 4
        val rollButton: Button = findViewById(R.id.button0)
        val dice0 = Dice(6)

        rollButton.setOnClickListener {
            //val toast = Toast.makeText(this, "Dice Rolled!" , Toast.LENGTH_SHORT).show()
            rollDice(luckyNumber,dice0)
        }

    }

    private fun rollDice(luckyNumber: Int , dice : Dice) {


        val resultTextView: TextView = findViewById(R.id.textView0)

        val diceImage: ImageView = findViewById(R.id.dice)

        val diceRoll = dice.roll()

        resultTextView.text = diceRoll.toString()

        /*if (diceRoll == luckyNumber){
            val toast = Toast.makeText(this, "Number hit" , Toast.LENGTH_SHORT).show()
        }
        else {
            val toast = Toast.makeText(this, "Number not hit , you suck" , Toast.LENGTH_SHORT).show()
        }*/
        val drawableResource = when (diceRoll) {
            //luckyNumber -> Toast.makeText(this, "Number hit" , Toast.LENGTH_SHORT).show()
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            luckyNumber -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> {
                R.drawable.dice_6
            }
        }
        diceImage.setImageResource(drawableResource)
        diceImage.contentDescription = diceRoll.toString()


    }
}


class Dice(private val sides: Int) {
    fun roll(): Int {
        return (1..sides).random()
    }
}