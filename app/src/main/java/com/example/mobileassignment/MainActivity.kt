package com.example.mobileassignment

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.mobileassignment.signup.SignUpActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private var mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loginBtn = findViewById<Button>(R.id.loginBtn_mainPage)
        val registerBtn = findViewById<Button>(R.id.registerBtn_mainPage)

        val resetPasswordText = findViewById<TextView>(R.id.forgotPassword_mainPage)

        loginBtn.setOnClickListener {
            finish()
            startActivity(Intent(this, LoginActivity::class.java))

        }

        registerBtn.setOnClickListener {
            finish()
            startActivity(Intent(this, SignUpActivity::class.java))

        }

        resetPasswordText.setOnClickListener() {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Forget Password")
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


//    resetPasswordText.setOnClickListener(){
//            val builder = AlertDialog.Builder(this)
//            builder.setTitle("Forget Password")
//            val view = layoutInflater.inflate(R.layout.dialog_forget_password, null)
//            val username = view.findViewById<EditText>(R.id.username_resetPassword )
//
//            builder.setView(view)
//            builder.setPositiveButton("Reset", DialogInterface.OnClickListener{ _, _ ->
//                forgetPassword(username)
//            } )
//            builder.setNegativeButton("Close", DialogInterface.OnClickListener{ _, _ ->
//            } )
//             builder.show()
//        }
//    }
//
//    private fun forgetPassword(username: EditText) {
//
//        mAuth = FirebaseAuth.getInstance()
//
//        if (username.text.toString().trim().isEmpty()) {
//            return
//        }
//
//        if (!Patterns.EMAIL_ADDRESS.matcher(username.text.toString()).matches()) {
//            return
//        }
//
//        mAuth.sendPasswordResetEmail(username.text.toString())
//            .addOnCompleteListener {  task ->
//                if (task.isSuccessful) {
//                    Toast.makeText(this, "Email sent", Toast.LENGTH_SHORT).show()
//                }
//        }
//    }
