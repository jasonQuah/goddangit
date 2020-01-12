package com.example.mobileassignment

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class ResetPassword : AppCompatActivity() {

    private var mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()

        val resetPasswordText = findViewById<TextView>(R.id.forgotPassword_mainPage)

        resetPasswordText.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Reset Password")
            val view = layoutInflater.inflate(R.layout.dialog_forget_password, null)
            val username = view.findViewById<EditText>(R.id.username_resetPassword)

            builder.setView(view)
            builder.setPositiveButton("Reset", DialogInterface.OnClickListener { _, _ ->
                forgetPassword(username)
            })
            builder.setNegativeButton("Close", DialogInterface.OnClickListener { _, _ ->
            })
            builder.show()
        }
    }


    private fun forgetPassword(username: EditText) {

        mAuth = FirebaseAuth.getInstance()

        if (username.text.toString().trim().isEmpty()) {
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(username.text.toString()).matches()) {
            return
        }

        mAuth.sendPasswordResetEmail(username.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Email sent", Toast.LENGTH_SHORT).show()
                }
            }

    }
}