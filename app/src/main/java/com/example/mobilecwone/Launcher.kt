package com.example.mobilecwone

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout


class Launcher : AppCompatActivity() {
    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        val newGameBtn = findViewById<Button>(R.id.newGameBtn)
        val aboutBtn = findViewById<Button>(R.id.aboutBtb)
        val iconImage = findViewById<ImageView>(R.id.iconImage)
        newGameBtn.setOnClickListener {
            Toast.makeText(applicationContext, "New game will be loaded", Toast.LENGTH_SHORT).show()

            Handler(Looper.getMainLooper()).postDelayed({

                val mainActivityIntent = Intent(this, MainActivity::class.java)
                startActivity(mainActivityIntent)

            }, 3000);

        }


        aboutBtn.setOnClickListener {
//            val window = PopupWindow(this)
//            val view = layoutInflater.inflate(R.layout.popup,null)
//            window.contentView = view
//            window.showAtLocation(iconImage, Gravity.CENTER, 0, 0);
            val view = layoutInflater.inflate(R.layout.popup, null)
            val focusable = true // lets taps outside the popup also dismiss it

            val popupWindow = PopupWindow(view, 1000, 1000, focusable)
            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        }
    }
}


