package com.example.mobilecwone

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {


    private var sumOfOne = 0 // text view one sum
    private var sumOfTwo = 0 // text view two sum
    private var operators = mutableListOf("+", "-", "*", "/")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val textFieldOne = findViewById<TextView>(R.id.textViewOne)
//        val textFieldTwo = findViewById<TextView>(R.id.textViewTwo)

//        sumOfOne = textFieldInitialize(textFieldOne)
//        sumOfTwo = textFieldInitialize(textFieldTwo)
        Log.d("my", "sum1 = $sumOfOne")
        Log.d("my", "sum2 = $sumOfTwo")
    }

    /**
     *this method will initialize the text and it will set the text to the passed parameter
     * @param textView: TextView
     * sum should be less than 100 otherwise the method will call itself
     * @return returning the sum
     */
    private fun textFieldInitialize(textView: TextView): Int {
        var text: String = ""
        var sum = 0//sum
        var tempSum = 0 //temporary sum

        val textFieldItemsList = mutableListOf<String>()
        lateinit var operator: String
        val numberOfArguments = (1..4).random()
//        Log.d("Debug", "arguments: $numberOfArguments")

        var mainLoop = 1


        while (mainLoop <= numberOfArguments) {

            val randomNumber = (1..21).random()//random int to calculate
            var operatorNum = 0
            //operator will only be available if i is greater than 1 (2,....)
            if (mainLoop > 1) {
                operatorNum = (0..3).random()
                operator = operators[operatorNum]
                tempSum = calculation(sum, randomNumber, operator)
                sum = tempSum

            }

            when {
                mainLoop == 1 -> {
                    // number will be equaled to the sum and the num will be added to the array.
                    sum = randomNumber
                    textFieldItemsList.add(randomNumber.toString())

                }
                mainLoop == 2 -> {
                    textFieldItemsList.add(operator)
                    textFieldItemsList.add(randomNumber.toString())

                }
                mainLoop > 2 -> {
                    textFieldItemsList.add(0, "(")
                    textFieldItemsList.add(")")
                    textFieldItemsList.add(operator)
                    textFieldItemsList.add(randomNumber.toString())

                }
            }

            var textViewLoop = 0
            text = ""

            while (textViewLoop < textFieldItemsList.size) {
                text += textFieldItemsList[textViewLoop]
                textViewLoop++

            }
            textView.text = text
            mainLoop++


        }


        Log.d("my", "sum: $sum")
        Log.d("my", "final: $textFieldItemsList")
        return sum
    }

    /**
     * this method will calculate and this will return the sum
     * @param sum Int
     * @param number Int
     * @param operator String
     * @return this will return a int.
     */
    private fun calculation(sum: Int, number: Int, operator: String): Int {
        var a = 0
        when (operator) {
            "+" -> {
                a = sum + number
            }
            "-" -> {
                a = sum - number
            }
            "*" -> {
                a = sum * number
            }
            "/" -> {
                a = sum / number
            }
            else -> {
                Log.d("Debug", "Invalid operator call in calculation method.")
            }
        }
        return a
    }
}
