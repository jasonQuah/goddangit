package com.example.mobileassignment.signup

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mobileassignment.LoginActivity
import com.example.mobileassignment.MainActivity
import com.example.mobileassignment.R
import com.example.mobileassignment.models.Company
import com.example.mobileassignment.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.sign_up_company_activity.*


class CompanySignUp : AppCompatActivity() {

    private var mAuth = FirebaseAuth.getInstance()
    private var userDatabase = FirebaseDatabase.getInstance().getReference("User")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up_company_activity)

        mAuth = FirebaseAuth.getInstance()

        val registerBtn = findViewById<Button>(R.id.submitButton_signUpCompany)

        val backBtn = findViewById<Button>(R.id.backButton_signUpCompany)

        registerBtn.setOnClickListener {
            saveNewCompany()
        }
        backBtn.setOnClickListener {
            backFunction()
        }
    }

    private fun saveNewCompany() {

        mAuth = FirebaseAuth.getInstance()

        if (usernameText_signUpCompany.text.toString().trim().isEmpty()) {
            usernameText_signUpCompany.error = "Please enter your username"
            usernameText_signUpCompany.requestFocus()
            return
        }

        if (passwordText_signUpCompany.text.toString().trim().isEmpty()) {
            passwordText_signUpCompany.error = "Please enter your password"
            passwordText_signUpCompany.requestFocus()
            return
        }

        if (confirmPasswordText_signUpCompany.text.toString().trim().isEmpty()) {
            confirmPasswordText_signUpCompany.error = "Please enter your confirm password"
            confirmPasswordText_signUpCompany.requestFocus()
            return
        }

        if (passwordText_signUpCompany.text.toString().trim() != confirmPasswordText_signUpCompany.text.toString().trim()) {
            passwordText_signUpCompany.error = "Your password or confirm password is unmatch"
            passwordText_signUpCompany.requestFocus()
            return

        } else if (confirmPasswordText_signUpCompany.text.toString().trim() != passwordText_signUpCompany.text.toString().trim()) {
            confirmPasswordText_signUpCompany.error = "Your password or confirm password is unmatch"
            confirmPasswordText_signUpCompany.requestFocus()
            return
        }

        if (emailText_signUpCompany.text.toString().trim().isEmpty()) {
            emailText_signUpCompany.error = "Please enter your email"
            emailText_signUpCompany.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailText_signUpCompany.text.toString().trim()).matches()) {
            emailText_signUpCompany.error = "Please enter valid email"
            emailText_signUpCompany.requestFocus()
            return
        }

        if (contactNoText_signUpCompany.text.toString().trim().isEmpty()) {
            contactNoText_signUpCompany.error = "Please enter your contact no"
            contactNoText_signUpCompany.requestFocus()
            return
        }

        if (ageText_signUpCompany.text.toString().trim().isEmpty()) {
            ageText_signUpCompany.error = "Please enter founded year"
            ageText_signUpCompany.requestFocus()
            return
        }

        if (addressText_signUpCompany.text.toString().trim().isEmpty()) {
            addressText_signUpCompany.error = "Please enter company address"
            addressText_signUpCompany.requestFocus()
            return
        }

        val userName = usernameText_signUpCompany.text.toString()
        val userRole = "Company"
        val userPassword = passwordText_signUpCompany.text.toString()
        val userEmail = emailText_signUpCompany.text.toString()
        val userContactNo = contactNoText_signUpCompany.text.toString()
        val userAge = ageText_signUpCompany.text.toString().toInt()
        val userAddress = addressText_signUpCompany.text.toString()

        mAuth.createUserWithEmailAndPassword(
            userEmail,
            userPassword
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = mAuth.currentUser
                    val userId = mAuth!!.currentUser!!.uid
                    val userObject = User (
                        userId,
                        userRole,
                        userName,
                        userPassword,
                        userEmail,
                        userContactNo,
                        userAge,
                        userAddress
                    )

                    user?.sendEmailVerification()?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Email sent.
                            finish()
                            startActivity(Intent(this, LoginActivity::class.java))
                        }
                    }
                    updateUI(user)

                    var maxid: Long = 0
                    userDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if(dataSnapshot.exists())
                                maxid = dataSnapshot.childrenCount
                            else
                                maxid=0
                            userDatabase.child((maxid+1).toString()).setValue(userObject).addOnCompleteListener {
                                Toast.makeText(
                                    baseContext, "Sign In successful.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                        override fun onCancelled(databaseError: DatabaseError) {
                            // handle error
                        }
                    })



                } else {
                    Toast.makeText(
                        baseContext, "Sign Up failed. Please try again.",
                        Toast.LENGTH_SHORT
                    ).show()

                    updateUI(null)
                }
            }
    }


    private fun updateUI(user: FirebaseUser?) {

    }

    private fun backFunction() {
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


