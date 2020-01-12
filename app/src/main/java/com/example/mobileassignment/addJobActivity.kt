package com.example.mobileassignment

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mobileassignment.models.addJob
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.add_job.*
import com.google.firebase.database.*
import com.google.firebase.database.DataSnapshot


class addJobActivity : AppCompatActivity() {

    private var mAuth = FirebaseAuth.getInstance()
    private lateinit var jobDatabase: DatabaseReference
    private lateinit var jobDatabase1: DatabaseReference
    private lateinit var categoryDatabase: DatabaseReference
    private lateinit var spinner: Spinner
    private lateinit var listener: ValueEventListener
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var spinnerDataList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_job)

        spinner = findViewById<Spinner>(R.id.categorySpinner)

        categoryDatabase = FirebaseDatabase.getInstance().getReference("Category");

        spinnerDataList = ArrayList()
        adapter = ArrayAdapter(
            this@addJobActivity,
            android.R.layout.simple_spinner_dropdown_item,
            spinnerDataList
        )

        spinner.adapter = adapter
        retrieveData()

        mAuth = FirebaseAuth.getInstance()

        val addBtn = findViewById<Button>(R.id.addjobbtn)
        val backBtn = findViewById<Button>(R.id.backbtn)

        addBtn.setOnClickListener {
            saveNewJobFunction()
        }

        backBtn.setOnClickListener {
            backFunction()
        }
    }

    private fun saveNewJobFunction() {
        mAuth = FirebaseAuth.getInstance()

        val jobPosition = addJobsPosition.text.toString().trim()
        val jobDescription = addJobsDescription.text.toString().trim()
        val jobSalary = addJobsSalary.text.toString().trim()
        val jobRequirement = addJobsRequirement.text.toString().trim()
        val jobCategory = categorySpinner.selectedItem.toString()


        if (TextUtils.isEmpty(jobPosition)) {
            Toast.makeText(
                this, "Please enter the position", Toast.LENGTH_SHORT
            ).show()
        }

        if (TextUtils.isEmpty(jobDescription)) {
            Toast.makeText(
                this, "Please enter the description", Toast.LENGTH_SHORT
            ).show()
        }

        if (TextUtils.isEmpty(jobSalary)) {
            Toast.makeText(
                this, "Please enter the salary", Toast.LENGTH_SHORT
            ).show()
        }

        if (TextUtils.isEmpty(jobRequirement)) {
            Toast.makeText(
                this, "Please enter the requirement", Toast.LENGTH_SHORT
            ).show()
        }

        val userID: String = mAuth.currentUser!!.uid

        jobDatabase = FirebaseDatabase.getInstance().getReference()

        val newJobid = jobDatabase.push().key

        if (newJobid != null && !TextUtils.isEmpty(jobPosition) && !TextUtils.isEmpty(jobDescription) && !TextUtils.isEmpty(
                jobSalary
            ) && !TextUtils.isEmpty(jobRequirement)
        ) {
            val jobb = addJob(
                newJobid,
                jobPosition,
                jobDescription,
                jobSalary,
                jobRequirement,
                jobCategory,
                userID
            )

            jobDatabase1 = FirebaseDatabase.getInstance().getReference().child("Job")

            var maxid1: Long = 0
            jobDatabase1.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists())
                        maxid1 = dataSnapshot.childrenCount
                    jobDatabase1.child((maxid1 + 1).toString()).setValue(jobb)
                        .addOnCompleteListener {
                            Toast.makeText(
                                applicationContext,
                                "Job Saved Successfully",
                                Toast.LENGTH_SHORT
                            ).show()

                            addJobsPosition.setText("")
                            addJobsDescription.setText("")
                            addJobsSalary.setText("")
                            addJobsRequirement.setText("")
                        }
                }

                override fun onCancelled(databaseError: DatabaseError) {

                }
            })

        } else {
            Toast.makeText(
                applicationContext,
                "Job Saved failed",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    private fun backFunction() {
        startActivity(Intent(this, StaffHomePage::class.java))
    }

    public fun retrieveData() {
        var maxid: Long = 0
        listener = categoryDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    maxid = p0.childrenCount
                }
                for (i in 1..maxid) {
                    spinnerDataList.add(p0.child((i).toString()).child("name").value.toString())
                }
                adapter.notifyDataSetChanged()

            }

            override fun onCancelled(p0: DatabaseError) {
            }
        })
    }
}