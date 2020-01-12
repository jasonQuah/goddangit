package com.example.mobileassignment.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.mobileassignment.MainActivity
import com.example.mobileassignment.R

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_activity)

        val registerPoster = findViewById<Button>(R.id.registerAdminButton)
        val registerUser = findViewById<Button>(R.id.registerUserButton)
        val backButton = findViewById<Button>(R.id.backButton_registerPosition)

        registerPoster.setOnClickListener() {
            finish()
            startActivity(Intent(this, CompanySignUp::class.java))
        }

        registerUser.setOnClickListener() {
            finish()
            startActivity(Intent(this, FinderSignUp::class.java))
        }

        backButton.setOnClickListener() {
            backFunction()
        }
    }

    private fun backFunction() {
        finish()
        startActivity(Intent(this, MainActivity::class.java))
    }
}