package com.example.mobileassignment

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.mobileassignment.models.Education
import com.example.mobileassignment.signup.FinderSignUp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.sign_up_education.*

class SignUpEducation : AppCompatActivity() {

    private var mAuth = FirebaseAuth.getInstance()
    private var educationDatabase = FirebaseDatabase.getInstance().getReference("Education")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up_education)

        mAuth = FirebaseAuth.getInstance()

        val confirmButton = findViewById<Button>(R.id.eduConfirmButton)
        val backButton = findViewById<Button>(R.id.eduBackButton)

        confirmButton.setOnClickListener() {
            saveNewEducation()
        }

        backButton.setOnClickListener() {
            backFunction()
        }

    }

    private fun saveNewEducation() {

        val user = mAuth.currentUser
        mAuth = FirebaseAuth.getInstance()
        //val firebaseUser = mAuth.uid

        if (schoolNameText_edu.text.toString().trim().isEmpty()) {
            schoolNameText_edu.error = "Please enter your school name"
            schoolNameText_edu.requestFocus()
            return
        }

        if (fieldOfStudyText_edu.text.toString().trim().isEmpty()) {
            fieldOfStudyText_edu.error = "Please enter your field of study"
            fieldOfStudyText_edu.requestFocus()
            return
        }

        if (qualificationText_edu.text.toString().trim().isEmpty()) {
            qualificationText_edu.error = "Please enter your qualification"
            qualificationText_edu.requestFocus()
            return
        }

        if (graduateDateText_edu.text.toString().trim().isEmpty()) {
            graduateDateText_edu.error = "Please enter your graduate date"
            graduateDateText_edu.requestFocus()
            return
        }

        val educationId = educationDatabase.push().key.toString()
        val schoolName = schoolNameText_edu.text.toString()
        val fieldOfStudy = fieldOfStudyText_edu.text.toString()
        val qualification = qualificationText_edu.text.toString()
        val graduateDate = graduateDateText_edu.text.toString()
        val userId = mAuth.currentUser

        val educationObject = Education (
            educationId,
            schoolName,
            fieldOfStudy,
            qualification,
            graduateDate,
            userId
        )

        educationDatabase.child(educationId).setValue(educationObject).addOnCompleteListener {

            finish()
            startActivity(Intent(this, SignUpExperience::class.java))

        }
    }

    private fun backFunction() {
        startActivity(Intent(this, FinderSignUp::class.java))
    }
}