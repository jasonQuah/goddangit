package com.example.mobileassignment

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mobileassignment.models.Education
import com.example.mobileassignment.models.WorkingExperience
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.sign_up_education.*
import kotlinx.android.synthetic.main.sign_up_experience.*

class SignUpExperience : AppCompatActivity() {

    private var mAuth = FirebaseAuth.getInstance()
    private var experienceDatabase = FirebaseDatabase.getInstance().getReference("Experience")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up_experience)

        val submitButton = findViewById<Button>(R.id.expSubmitButton)
        val backButton = findViewById<Button>(R.id.expBackButton)

        submitButton.setOnClickListener() {
            saveNewExperience()
        }

        backButton.setOnClickListener() {
            backFunction()
        }
    }

    private fun saveNewExperience() {
        val user = mAuth.currentUser
        mAuth = FirebaseAuth.getInstance()

        val experienceId = experienceDatabase.push().key.toString()
        val companyName = companyNameText_exp.text.toString()
        val specialization = specializationText_exp.text.toString()
        val jobPosition = positionText_exp.text.toString()
        val joinedDate = joinDateText_exp.text.toString()
        val leftDate = dateLeftText_exp.text.toString()
        val userId = mAuth.currentUser

        val experienceObject = WorkingExperience (
            experienceId,
            companyName,
            specialization,
            jobPosition,
            joinedDate,
            leftDate,
            userId
        )

        user?.sendEmailVerification()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Email sent.
                finish()
                startActivity(Intent(this, LoginActivity::class.java))

            } else {
                Toast.makeText(
                    applicationContext,
                    "Sign Up failed. Please try again",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        experienceDatabase.child(experienceId).setValue(experienceObject).addOnCompleteListener {
            Toast.makeText(
                applicationContext,
                "Your register is successfully.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun backFunction() {
        startActivity(Intent(this, SignUpEducation::class.java))
    }
}