package com.example.mobilecwone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.Toast

class Launcher : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        val newGameBtn = findViewById<Button>(R.id.newGameBtn)

        newGameBtn.setOnClickListener {
            Toast.makeText(applicationContext, "New game will be loaded", Toast.LENGTH_SHORT).show()

            Handler(Looper.getMainLooper()).postDelayed({

                val mainActivityIntent = Intent(this, MainActivity::class.java)
                startActivity(mainActivityIntent)

            }, 3000);

        }
    }
}