package com.example.mobileassignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mobileassignment.R
import com.example.mobileassignment.category.CategoryActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class midPoint : AppCompatActivity() {
    private var userDatabase = FirebaseDatabase.getInstance().getReference("User")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mid_point)

        var maxid: Long = 0
        var mAuthid = FirebaseAuth.getInstance().currentUser!!.uid

                userDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if(dataSnapshot.exists())
                            maxid = dataSnapshot.childrenCount
                        for(i in 1..maxid){
                            if(dataSnapshot.child(i.toString()).child("user_id").value.toString().equals(mAuthid)){
                                if(dataSnapshot.child(i.toString()).child("user_role").value.toString().equals("Finder")){
                                    startActivity(Intent(baseContext, CategoryActivity::class.java))
                                }
                                else if(dataSnapshot.child(i.toString()).child("user_role").value.toString().equals("Company")){
                                    startActivity(Intent(baseContext, StaffHomePage::class.java))
                                }

                            }
                        }
                    }
                    override fun onCancelled(databaseError: DatabaseError) {}
                })

            }
}
