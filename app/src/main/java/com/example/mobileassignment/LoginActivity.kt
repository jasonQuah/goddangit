package com.example.mobileassignment

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mobileassignment.category.CategoryActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.login_activity.*


class LoginActivity : AppCompatActivity() {

    private var mAuth = FirebaseAuth.getInstance()
    private var userDatabase = FirebaseDatabase.getInstance().getReference("User")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        mAuth = FirebaseAuth.getInstance()

        val loginBtn = findViewById<Button>(R.id.loginBtn_login)
        val backBtn = findViewById<Button>(R.id.backBtn_login)

        loginBtn.setOnClickListener() {
            doLogin()
        }

        backBtn.setOnClickListener() {
            backFunction()
        }
    }

    private fun doLogin() {

        mAuth = FirebaseAuth.getInstance()

        if (emailText_login.text.toString().trim().isEmpty()) {
            emailText_login.error = "Please enter email"
            emailText_login.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailText_login.text.toString()).matches()) {
            emailText_login.error = "Please enter valid email"
            emailText_login.requestFocus()
            return
        }

        if (passwordText_login.text.toString().trim().isEmpty()) {
            passwordText_login.error = "Please enter password"
            passwordText_login.requestFocus()
            return
        }

        val loginEmail = emailText_login.text.toString()
        val loginPassword = passwordText_login.text.toString()

        mAuth.signInWithEmailAndPassword(
            loginEmail,
            loginPassword
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) { // Sign in success, update UI with the signed-in user's information
                    val user: FirebaseUser? = mAuth.currentUser
                    Toast.makeText(applicationContext, "Sign In successful", Toast.LENGTH_SHORT).show()
                    updateUI(user)
                } else { // If sign in fails, display a message to the user.
                    Toast.makeText(applicationContext, "Sign In failed", Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }


    public override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {

        if (currentUser != null) {
            if (currentUser.isEmailVerified) {
                startActivity(Intent(baseContext, midPoint::class.java))
                finish()
            } else {
                Toast.makeText(
                    baseContext, "Please verify your email address.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(
                baseContext, "Login failed.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun backFunction() {
        startActivity(Intent(this, MainActivity::class.java))
    }
}
