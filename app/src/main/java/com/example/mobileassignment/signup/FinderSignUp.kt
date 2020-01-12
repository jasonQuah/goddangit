package com.example.mobileassignment.signup

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mobileassignment.MainActivity
import com.example.mobileassignment.R
import com.example.mobileassignment.SignUpEducation
import com.example.mobileassignment.models.Finder
import com.example.mobileassignment.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.sign_up_finder_activity.*


class FinderSignUp : AppCompatActivity() {

    private var mAuth = FirebaseAuth.getInstance()
    private var userDatabase = FirebaseDatabase.getInstance().getReference("User")
//    private var finderDatabase = FirebaseDatabase.getInstance().getReference("Finder")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up_finder_activity)

        mAuth = FirebaseAuth.getInstance()

        val registerBtn = findViewById<Button>(R.id.continueButton_signUp)
        val backBtn = findViewById<Button>(R.id.backButton_signUp)

        registerBtn.setOnClickListener {
            saveNewUser()
        }

        backBtn.setOnClickListener {
            backFunction()
        }
    }

    private fun saveNewUser() {

        mAuth = FirebaseAuth.getInstance()

        if (usernameText_signUp.text.toString().trim().isEmpty()) {
            usernameText_signUp.error = "Please enter your username"
            usernameText_signUp.requestFocus()
            return
        }

        if (passwordText_signUp.text.toString().trim().isEmpty()) {
            passwordText_signUp.error = "Please enter your password"
            passwordText_signUp.requestFocus()
            return
        }

        if (confirmPasswordText_signUp.text.toString().trim().isEmpty()) {
            confirmPasswordText_signUp.error = "Please enter your confirm password"
            confirmPasswordText_signUp.requestFocus()
            return
        }

        if (passwordText_signUp.text.toString().trim() != confirmPasswordText_signUp.text.toString().trim()) {
            passwordText_signUp.error = "Your password or confirm password is unmatch"
            passwordText_signUp.requestFocus()
            return

        } else if (confirmPasswordText_signUp.text.toString().trim() != passwordText_signUp.text.toString().trim()) {
            confirmPasswordText_signUp.error = "Your password or confirm password is unmatch"
            confirmPasswordText_signUp.requestFocus()
            return
        }

        if (emailText_signUp.text.toString().trim().isEmpty()) {
            emailText_signUp.error = "Please enter your email"
            emailText_signUp.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailText_signUp.text.toString().trim()).matches()) {
            emailText_signUp.error = "Please enter valid email"
            emailText_signUp.requestFocus()
            return
        }

        if (contactNoText_signUp.text.toString().trim().isEmpty()) {
            contactNoText_signUp.error = "Please enter your contact no"
            contactNoText_signUp.requestFocus()
            return
        }

        if (ageText_signUp.text.toString().trim().isEmpty()) {
            ageText_signUp.error = "Please enter your age"
            ageText_signUp.requestFocus()
            return
        }

        if (addressText_signUp.text.toString().trim().isEmpty()) {
            addressText_signUp.error = "Please enter your address"
            addressText_signUp.requestFocus()
            return
        }

            val userName = usernameText_signUp.text.toString()
            val userRole = "Finder"
            val userPassword = passwordText_signUp.text.toString()
            val userEmail = emailText_signUp.text.toString()
            val userContactNo = contactNoText_signUp.text.toString()
            val userAge = ageText_signUp.text.toString().toInt()
            val userAddress = addressText_signUp.text.toString()

//            val finderId = finderDatabase.push().key.toString()

            mAuth.createUserWithEmailAndPassword(
                userEmail,
                userPassword
            )
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = mAuth.currentUser
                        val userId = mAuth!!.currentUser!!.uid
                        val userObject = User(
                            userId,
                            userRole,
                            userName,
                            userPassword,
                            userEmail,
                            userContactNo,
                            userAge,
                            userAddress
                        )

//                        val userId2 = mAuth.currentUser
//                        val finderObject = Finder (
//                            finderId,
//                            userAge,
//                            userId2
//                        )

                       updateUI(user)

                        var maxid: Long = 0
                        userDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                if(dataSnapshot.exists())
                                    maxid = dataSnapshot.childrenCount
                                else
                                    maxid=0
                                userDatabase.child(userId).setValue(userObject).addOnCompleteListener {
                                    Toast.makeText(
                                        applicationContext,
                                        "Your register is successfully.",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    finish()
                                    startActivity(Intent(baseContext, SignUpEducation::class.java))
                                }
                            }
                            override fun onCancelled(databaseError: DatabaseError) {
                                // handle error
                            }
                        })

//                        finderDatabase.child(finderId).setValue(finderObject).addOnCompleteListener {
//                            Toast.makeText(
//                                applicationContext,
//                                "Your register is successfully.",
//                                Toast.LENGTH_SHORT
//                            ).show()
//
//                            finish()
//                            startActivity(Intent(this, SignUpEducation::class.java))
//                        }
                    } else {
                        Toast.makeText(
                            baseContext, "Sign Up failed. Please try again.",
                            Toast.LENGTH_SHORT
                        ).show()

                        updateUI(null)
                    }
                }
        }

//        val userRole = "staff"
//        val userPassword = passwordText_signUp.text.toString()
//        val userEmail = emailText_signUp.text.toString()
//        val userContactNo = contactNoText_signUp.text.toString()
//        c
//        val userAddress = addressText_signUp.text.toString()

//        mAuth.createUserWithEmailAndPassword(
//            userEmail,
//            userPassword
//        )
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    val user = mAuth.currentUser
//                    val userId = mAuth!!.currentUser!!.uid
//                    val userObject = User(
//                        userRole,
//                        userName,
//                        userPassword,
//                        userEmail,
//                        userContactNo,
//                        userAddress
//                    )
//
//                    updateUI(user)
//
//                    userDatabase.child(userId).setValue(userObject).addOnCompleteListener {
//                        Toast.makeText(
//                            applicationContext,
//                            "Your register is successfully.",
//                            Toast.LENGTH_SHORT
//                        ).show()
//
//                        finish()
//                        startActivity(Intent(this, SignUpEducation::class.java))
//
//                    }
//
//                } else {
//                    Toast.makeText(
//                        baseContext, "Sign Up failed. Please try again.",
//                        Toast.LENGTH_SHORT
//                    ).show()
//
//                    updateUI(null)
//                }
//            }

    private fun updateUI(user: FirebaseUser?) {
    }

    private fun backFunction() {
        finish()
        startActivity(Intent(this, MainActivity::class.java))
    }
}


//        DatePicker
//        val schoolName = schoolNameText_edu.text.toString().trim()
//        val qualification  = qualification_edu.onItemSelectedListener.toString()
//
//        graduationDate_edu.setOnClickListener() {
//            val c: Calendar = Calendar.getInstance()
//            val currentDay = c.get(Calendar.DAY_OF_MONTH)
//            val currentMonth = c.get(Calendar.MONTH)
//            val currentYear = c.get(Calendar.YEAR)
//
//            val dpd = DatePickerDialog(this,
//                DatePickerDialog.OnDateSetListener
//                { view, year, month, day ->
//                    graduationDate_edu(day.toString() + "/" + (month + 1).toString() + "/" + year.toString())
//
//
//                }, currentYear, currentMonth, currentDay
//            )
//            dpd.show()
//        }


