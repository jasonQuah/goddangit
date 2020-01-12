package com.example.mobileassignment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mobileassignment.models.cardViewApplication
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.user_apply_details.*
import java.util.ArrayList

class ViewApplicationActivity : AppCompatActivity() {

    private lateinit var userDatabase: DatabaseReference
    private val data = ArrayList<cardViewApplication>()
    var maxid: Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_apply_details)
        var userId: String = intent.getStringExtra("userId")
        var userAge: String = intent.getStringExtra("userage")
        var userName: String = intent.getStringExtra("userName")
        var userAddress: String = intent.getStringExtra("userAddress")

        userDatabase = FirebaseDatabase.getInstance().reference.child("User")
        userDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists())
                    maxid = p0.childrenCount
                for (i in 1..maxid) {
                    if ((i).toString().equals(userId)) {
                        usernametxt.text =
                            userName
                        agetxt.text =
                            userAge
                        addresstxt.text =
                            userAddress
                        phonetxt.text =
                            p0.child((i).toString()).child("user_contactNo").value.toString()
                        emailtxt.text =
                            p0.child((i).toString()).child("user_email").value.toString()
                    }
                }
            }

            override fun onCancelled(p0: DatabaseError) {
            }
        })
    }
}