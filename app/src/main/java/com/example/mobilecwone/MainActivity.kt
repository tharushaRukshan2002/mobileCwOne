package com.example.mobilecwone

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var textFieldOne: TextView
    private var sumOfOne = 0 // text view one sum
    private var sumOfTwo = 0 // text view two sum
    private var operators = mutableListOf("+", "-", "*", "/")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textFieldOne = findViewById(R.id.textViewOne)
        textFieldInitialize(textFieldOne)


    }

    private fun textFieldInitialize(textView: TextView): Int {
        var sum = 0//sum
        val textFieldItemsList = mutableListOf<String>()

        val numberOfArguments = (0..3).random()
        var i = 0
        while (i < 2){

            val randomNumber = (1..21).random()
            val operatorNum = (0..3).random()

            if (i == 0){

                sum = randomNumber
                textFieldItemsList.add(randomNumber.toString())
                Log.d("Debug", randomNumber.toString())

            }else if ( i == 1){

                val operator = operators[operatorNum]
                textFieldItemsList.add(operator)
                textFieldItemsList.add(randomNumber.toString())

                sum = calculation(sum, randomNumber, operator)
                Log.d("Debug", textFieldItemsList.toString())
                Log.d("Debug", sum.toString())
            }
            i++
        }



        return sum
    }

    private fun calculation(sum:Int, number:Int, operator:String):Int{
        var a = 0
        when(operator){
            "+" -> { a = sum + number}
            "-" -> { a = sum - number}
            "*" -> { a = sum * number}
            "/" -> { a = sum / number}
            else -> {
                Log.d("Debug", "Invalid operator call in calculation method.")
            }
        }
        return a
    }
}
