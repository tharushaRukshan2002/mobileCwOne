package com.example.mobilecwone

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    //textViews
    private lateinit var textFieldOne: TextView //textField to set equation
    private lateinit var textFieldTwo: TextView //textField to set equation
    private lateinit var validityTxt: TextView //correct wrong text
    private lateinit var timerTxt: TextView //timer textview
    private lateinit var wrongTxt: TextView //wrong answers

    //Buttons
    private lateinit var greaterBtn: Button // 1 > 2 btn
    private lateinit var equalsBtn: Button // 1 == 2
    private lateinit var lessBtn: Button // 1 < 2
    private var buttons = mutableListOf<Button>()

    //ImageViews
    private lateinit var validityImage: ImageView // image text
    private lateinit var wrongImage: ImageView //cross mark

    //activity
    private var correctAnswers = 0 //num of correct answers
    private var wrongAnswers = 0 //num of wrong answers
    private var screenInitializeBool = true
    private var sumOfOne = 0 // text view one sum
    private var sumOfTwo = 0 // text view two sum
    private var operators = mutableListOf("+", "-", "*", "/")


    private var targetTimeToRun: Int = 20000 // starting time of the timer 50S
    private val correctList = mutableListOf<Int>() //to keep the correct iterations

    @SuppressLint("SetTextI18n")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i("my", "sumOne: $sumOfOne")
        Log.i("my", "sumTwo: $sumOfTwo")

        textFieldOne = findViewById(R.id.equationOneTxt)
        textFieldTwo = findViewById(R.id.equationTwoTxt)
        greaterBtn = findViewById(R.id.greaterBtn)
        validityTxt = findViewById(R.id.answerValidityTxt)
        validityImage = findViewById(R.id.answerValidityImg)
        equalsBtn = findViewById(R.id.equalBtn)
        lessBtn = findViewById(R.id.lessBtn)
        timerTxt = findViewById(R.id.timerTxt)
        wrongTxt = findViewById(R.id.answerValidityTxt1)
        wrongImage = findViewById(R.id.answerValidityImg1)

        if (savedInstanceState != null) {
            wrongAnswers = savedInstanceState.getInt("wrong")
            correctAnswers = savedInstanceState.getInt("correct")
            targetTimeToRun = savedInstanceState.getInt("timerTime")
            textFieldOne.text = savedInstanceState.getString("textViewOne")
            textFieldTwo.text = savedInstanceState.getString("textVieTwo")
            screenInitializeBool = savedInstanceState.getBoolean("screenInitializeBool")
            textFieldOne.text = savedInstanceState.getString("textViewOne")
            textFieldTwo.text = savedInstanceState.getString("textVieTwo")
            sumOfOne = savedInstanceState.getInt("sumOfOne")
            sumOfTwo = savedInstanceState.getInt("sumOfTwo")
            timer()
        } else {
            timerTxt.text ="Start on 5S"
            Handler(Looper.getMainLooper()).postDelayed({
                buttonEnableFun()
                screenInitialize()
                timer()
            }, 5000)

        }

        buttons.add(greaterBtn)
        buttons.add(equalsBtn)
        buttons.add(lessBtn)

        greaterBtn.setOnClickListener {
            greaterBtnFunction()
        }

        equalsBtn.setOnClickListener {
            equalsButtonFunction()
        }

        lessBtn.setOnClickListener {
            lessButtonFunction()
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("sumOfOne", sumOfOne)
        outState.putInt("sumOfTwo", sumOfTwo)
        outState.putInt("timerTime", targetTimeToRun)
        outState.putInt("correct", correctAnswers)
        outState.putInt("wrong", wrongAnswers)
        outState.putString("textViewOne", textFieldOne.text.toString())
        outState.putString("textVieTwo", textFieldTwo.text.toString())
        outState.putBoolean("screenInitializeBool", screenInitializeBool)

    }

    /**
     * This method will completely kill the activity otherwise the timer will run
     * although you call the function.
     */
    override fun onBackPressed() {
        super.onBackPressed()
        this.finish()
    }

    /**
     * timer using recursion
     * https://stackoverflow.com/questions/37150291/recursive-countdown-timer
     */

    @SuppressLint("SetTextI18n")
    private fun timer() {
        targetTimeToRun -= 1000
        val seconds = (targetTimeToRun / 1000)//calculating seconds remaining
        timerTxt.text = "seconds left: $seconds 's"
        //if number of correct answers is divisible by 5 the 10s will be added
        if (correctAnswers !in correctList && correctAnswers % 5 == 0 && correctAnswers / 5 != 0) {
            targetTimeToRun += 10000
            correctList.add(correctAnswers)
        }
        //timer text color will be controlled.
        screenControl()
        //this will call timer method recursively
        if (targetTimeToRun > 0) {
            Handler(Looper.getMainLooper()).postDelayed({
                timer()
            }, 1000)
        }
        //this will end the game
        if (targetTimeToRun <= 0) {
            timeEnd()
        }
    }

    /**
     * This method will have all the actions this does when the timer stops
     */
    @SuppressLint("SetTextI18n")
    private fun timeEnd() {
        screenInitializeBool = false
        validityTxt.text = "Correct: $correctAnswers"//correct Answer
        validityTxt.setTextColor(Color.GREEN)
        validityTxt.textSize = 33f
        validityImage.setImageResource(R.drawable.done)
        wrongTxt.text = "Wrong: $wrongAnswers"
        wrongImage.setImageResource(R.drawable.cross_mark_48)
        timerTxt.text = "TIME OVER"
        textFieldOne.text = null
        textFieldTwo.text = null
        buttonDisableFun()
    }

    /**
     *this will do changes in the screen according to the timers time.
     */
    private fun screenControl() {
        if (targetTimeToRun < 10000) {
            timerTxt.setTextColor(Color.RED)
            timerTxt.textSize = 35f
        } else {
            timerTxt.setTextColor(Color.WHITE)
            timerTxt.textSize = 32f
        }
    }


    /**
     * method will set values to the screen and this will set values to int variables
     * to compare later. this method will call text field initialize method
     */
    @SuppressLint("SetTextI18n")
    private fun screenInitialize() {
        if (screenInitializeBool) {
            validityTxt.text = null
            validityImage.setImageResource(0)
            Log.i("my", "correct = $correctAnswers")
            Log.i("my", "wrong = $wrongAnswers")
            sumOfOne = textFieldInitialize(textFieldOne)
            sumOfTwo = textFieldInitialize(textFieldTwo)
            buttonEnableFun()
//            Log.d(TAG, "sum1 = $sumOfOne")
//            Log.d(TAG, "sum2 = $sumOfTwo")
        }
    }

    /**
     *this method will initialize the text and it will set the text to the passed parameter
     * @param textView: TextView
     * @return returning the sum
     */
    private fun textFieldInitialize(textView: TextView): Int {
        var sum = 0//sum
        var tempSum: Int //temporary sum
        var randomNumber: Int//random num
        val textFieldItemsList = mutableListOf<String>()
        //items to be added to the text field
        lateinit var operator: String
        val numberOfArguments = (1..4).random()
        var mainLoop = 1

        while (mainLoop <= numberOfArguments) {
            randomNumber = (1..21).random()//random int to calculate
            var operatorNum: Int
            //operator will only be available if i is greater than 1 (2,....)
            if (mainLoop > 1) {
                operatorNum = (0..3).random()
                operator = operators[operatorNum]

                if (operator == "/") {
                    while (sum % randomNumber != 0) {
                        randomNumber = (1..21).random()
                    }
                }
                tempSum = calculation(sum, randomNumber, operator) //calculating the sum
                if (tempSum >= 100) {
                    /* if the sum is greater than or equal to 100 the method execution will stop inside this function
                     */
                    setTextToField(textView, textFieldItemsList)
                    return sum
                } else {
                    sum = tempSum
                }
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

            mainLoop++
        }

        setTextToField(textView, textFieldItemsList)
        Log.i("my", "sum of $textView: $sum")
        Log.i("my", "arg: $numberOfArguments")
        Log.i("my", "final: $textFieldItemsList")
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

    /**
     * greater btn function this will check whether the sum of one is greater than two
     * 1 > 2
     */
    @SuppressLint("SetTextI18n")
    private fun greaterBtnFunction() {
        buttonDisableFun()

        if (sumOfOne > sumOfTwo) {
            validityTxt.text = "Correct" //setting text
            validityTxt.setTextColor(Color.GREEN) //colour GREEN
            validityImage.setImageResource(R.drawable.done)
            correctAnswers++
            Handler(Looper.getMainLooper()).postDelayed({

                screenInitialize()

            }, 1000)


        } else {
            validityTxt.text = "Wrong"
            validityTxt.setTextColor(Color.RED)
            validityImage.setImageResource(R.drawable.cross_mark_48)
            wrongAnswers++
            Handler(Looper.getMainLooper()).postDelayed({

                screenInitialize()

            }, 1000)

        }
    }


    /**
     * equals btn function this will check whether the sum of one is equal to two
     * 1 == 2
     */
    @SuppressLint("SetTextI18n")
    private fun equalsButtonFunction() {

        buttonDisableFun()

        if (sumOfOne == sumOfTwo) {
            validityTxt.text = "Correct" //setting text
            validityTxt.setTextColor(Color.GREEN) //colour GREEN
            validityImage.setImageResource(R.drawable.done)
            correctAnswers++
            Handler(Looper.getMainLooper()).postDelayed({

                screenInitialize()

            }, 1000)


        } else {
            validityTxt.text = "Wrong"
            validityTxt.setTextColor(Color.RED)
            validityImage.setImageResource(R.drawable.cross_mark_48)
            wrongAnswers++
            Handler(Looper.getMainLooper()).postDelayed({

                screenInitialize()

            }, 1000)

        }

    }

    /**
     * less btn function this will check whether the sum of one less than the sum of two
     * 1<2
     */
    @SuppressLint("SetTextI18n")
    private fun lessButtonFunction() {

        buttonDisableFun()

        if (sumOfOne < sumOfTwo) {
            validityTxt.text = "Correct" //setting text
            validityTxt.setTextColor(Color.GREEN) //colour GREEN
            validityImage.setImageResource(R.drawable.done)
            correctAnswers++
            Handler(Looper.getMainLooper()).postDelayed({
                screenInitialize()
            }, 1000)

        } else {
            validityTxt.text = "Wrong"
            validityTxt.setTextColor(Color.RED)//red
            validityImage.setImageResource(R.drawable.cross_mark_48)
            wrongAnswers++
            Handler(Looper.getMainLooper()).postDelayed({
                screenInitialize()
            }, 1000)

        }

    }

    /**
     *method will set the text to the passed field from the
     * @param textView TextView
     * items will be taken  from the list that passed from the textFieldItemList
     * @param textFieldItemsList mutableList
     */
    private fun setTextToField(textView: TextView, textFieldItemsList: List<String>) {
        var textViewLoop = 0
        var text = ""

        while (textViewLoop < textFieldItemsList.size) {
            text += textFieldItemsList[textViewLoop]
            textViewLoop++

        }
        textView.text = text

    }

    /**
     * this function will disable the buttons
     */
    private fun buttonDisableFun() {
        var i = 0
        while (i < buttons.size) {
            buttons[i].isEnabled = false
            i++
        }
    }

    /**
     * this function will enable the buttons
     */
    private fun buttonEnableFun() {
        var i = 0
        while (i < buttons.size) {
            buttons[i].isEnabled = true
            i++
        }
    }
}




