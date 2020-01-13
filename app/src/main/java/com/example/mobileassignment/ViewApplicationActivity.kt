package com.example.mobileassignment

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mobileassignment.models.Application
import com.example.mobileassignment.models.cardViewApplication
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.user_apply_details.*
import java.util.ArrayList

class ViewApplicationActivity : AppCompatActivity() {

    private lateinit var userDatabase: DatabaseReference
    private lateinit var approveDatabase: DatabaseReference
    private lateinit var rejectDatabase: DatabaseReference
    var maxid: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_apply_details)

        var userId: String = intent.getStringExtra("userId")
        var userAge: String = intent.getStringExtra("userage")
        var userName: String = intent.getStringExtra("userName")
        var userAddress: String = intent.getStringExtra("userAddress")
        var jobid1: String = intent.getStringExtra("jobIdd")


        userDatabase = FirebaseDatabase.getInstance().reference.child("User")

        userDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists())
                    maxid = p0.childrenCount
                for (i in 1..maxid) {
                    if (p0.child((i).toString()).child("user_id").value.toString().equals(userId)) {
                        usernametxt.setText(userName)
                        agetxt.text = userAge
                        addresstxt.text = userAddress
                        phonetxt.text = p0.child((i).toString()).child("user_contactNo").value.toString()
                        emailtxt.text = p0.child((i).toString()).child("user_email").value.toString()
                    }
                }
            }
            override fun onCancelled(p0: DatabaseError) {
            }
        })



        val approveButton1 = findViewById<Button>(R.id.approvedBtn)
        val rejectButton1 = findViewById<Button>(R.id.rejectedBtn)

        approveButton1.setOnClickListener {
            approveDatabase = FirebaseDatabase.getInstance().reference.child("Application")

            var maxidd: Long = 0
            approveDatabase.addListenerForSingleValueEvent(object: ValueEventListener{
                override fun onDataChange(p1: DataSnapshot) {
                    if (p1.exists())
                        maxidd = p1.childrenCount
                    for (a in 1..maxidd) {
                        if(p1.child((a).toString()).child("jobId").value.toString().equals(jobid1) && p1.child((a).toString()).child("userId").value.toString().equals(userId)){
                            var key: String = p1.child(a.toString()).key.toString()
                            val updateApp = Application("Approved", userId, jobid1)

                            approveDatabase.child((key).toString()).setValue(updateApp)
                                .addOnCompleteListener {
                                    Toast.makeText(
                                        applicationContext,
                                        "Approve User Successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        }
                    }
                }
                override fun onCancelled(p1: DatabaseError) {
                }
            })
            val intent = Intent(this, ViewJobActivity::class.java)
            startActivity(intent)
        }

        rejectButton1.setOnClickListener {
            rejectDatabase = FirebaseDatabase.getInstance().reference.child("Application")

            var maxidd1: Long = 0
            rejectDatabase.addListenerForSingleValueEvent(object: ValueEventListener{
                override fun onDataChange(p2: DataSnapshot) {
                    if (p2.exists())
                        maxidd1 = p2.childrenCount
                    for (b in 1..maxidd1) {
                        if(p2.child((b).toString()).child("jobId").value.toString().equals(jobid1) && p2.child((b).toString()).child("userId").value.toString().equals(userId)){
                            var key1: String = p2.child(b.toString()).key.toString()
                            val updateApp1 = Application("Rejected", userId, jobid1)

                            rejectDatabase.child((key1).toString()).setValue(updateApp1)
                                .addOnCompleteListener {
                                    Toast.makeText(
                                        applicationContext,
                                        "Reject User Successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        }
                    }
                }
                override fun onCancelled(p2: DatabaseError) {
                }
            })
            val intent = Intent(this, ViewJobActivity::class.java)
            startActivity(intent)
        }
    }

    /*private fun approveFunction(){
        approveDatabase = FirebaseDatabase.getInstance().reference.child("Application")

        var maxidd: Long = 0
        approveDatabase.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(p1: DataSnapshot) {
                if (p1.exists())
                    maxidd = p1.childrenCount
                for (a in 1..maxidd) {
                    if(p1.child((a).toString()).child("jobId").value.toString().equals(jobid1) && p1.child((a).toString()).child("userId").value.toString().equals(userId)){
                        val updateApp = Application("Approved", userId, jobid1)

                        approveDatabase.child((maxidd).toString()).setValue(updateApp)
                            .addOnCompleteListener {
                                Toast.makeText(
                                    applicationContext,
                                    "Approve User Successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    }
                }
            }
            override fun onCancelled(p1: DatabaseError) {
            }
        })
    }*/

    /*private fun rejectFunction(){
        rejectDatabase = FirebaseDatabase.getInstance().reference.child("Application")

        var maxidd1: Long = 0
        rejectDatabase.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(p2: DataSnapshot) {
                if (p2.exists())
                    maxidd1 = p2.childrenCount
                for (b in 1..maxidd1) {
                    if(p2.child((b).toString()).child("jobId").value.toString().equals(jobid1) && p2.child((b).toString()).child("userId").value.toString().equals(userId)){
                        val updateApp1 = Application("Rejected", userId, jobid1)

                        rejectDatabase.child((maxidd1).toString()).setValue(updateApp1)
                            .addOnCompleteListener {
                                Toast.makeText(
                                    applicationContext,
                                    "Reject User Successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    }
                }
            }
            override fun onCancelled(p2: DatabaseError) {
            }
        })
    }*/
}