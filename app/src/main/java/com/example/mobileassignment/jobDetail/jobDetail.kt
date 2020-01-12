package com.example.mobileassignment.jobDetail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.mobileassignment.R
import com.example.mobileassignment.category.CategoryActivity
import com.example.mobileassignment.databinding.ActivityJobdetailBinding
import com.example.mobileassignment.models.Application
import com.example.mobileassignment.myApply.myApplyActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class jobDetail : AppCompatActivity() {

    private lateinit var binding: ActivityJobdetailBinding
    private lateinit var ref: DatabaseReference
    private lateinit var database: DatabaseReference
    var maxid: Long = 0
    var mAuth = FirebaseAuth.getInstance().currentUser!!.uid


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_jobdetail)
        var jobId: String = intent.getStringExtra("jobId")
        var disp: String = intent.getStringExtra("disp")

        if(disp.equals("no")){
            binding.button.visibility = View.INVISIBLE
        }
        else{
            binding.button.visibility = View.VISIBLE
        }
        ref = FirebaseDatabase.getInstance().reference.child("Job")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists())
                    maxid = dataSnapshot.childrenCount
                for (i in 1..maxid){
                    if((i).toString().equals(jobId)){
                        binding.pos.text =  "Position: "+ dataSnapshot.child((i).toString()).child("position").value.toString()
                        binding.salary.text =  dataSnapshot.child((i).toString()).child("salary").value.toString()
                        binding.condition.text =  dataSnapshot.child((i).toString()).child("requirement").value.toString()
                        binding.desc.text =  dataSnapshot.child((i).toString()).child("desc").value.toString()

                        var companyId: String = dataSnapshot.child(i.toString()).child("userId").value.toString()
                        database = FirebaseDatabase.getInstance().reference.child("User")
                        var maxid2: Long = 0
                        database.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(ds: DataSnapshot) {
                                if(ds.exists())
                                    maxid2 = ds.childrenCount
                                else
                                    maxid2 = 0
                                for(q in 1..maxid){
                                    if(ds.child(q.toString()).child("user_id").value.toString().equals(companyId)){
                                        binding.comName.text = "Company Name: " + ds.child(q.toString()).child("user_name").value.toString()
                                        binding.location.text = ds.child(q.toString()).child("user_address").value.toString()
                                    }
                                }
                            }
                            override fun onCancelled(databaseError: DatabaseError) {}
                        })
                    }
                }

            }
            override fun onCancelled(databaseError: DatabaseError) {
                // handle error
            }
        })

        binding.button.setOnClickListener{
            maxid=0
            var msg: String = ""
            database = FirebaseDatabase.getInstance().reference
            ref = FirebaseDatabase.getInstance().reference.child("Application")
            ref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists())
                        maxid = dataSnapshot.childrenCount
                    for(i in 1..maxid){
                        if(dataSnapshot.child(i.toString()).child("userId").value.toString().equals(mAuth) && dataSnapshot.child(i.toString()).child("jobId").value.toString().equals(jobId) && (dataSnapshot.child(i.toString()).child("status").value.toString().equals("Pending") || dataSnapshot.child(i.toString()).child("status").value.toString().equals("Approved"))){
                            msg = "You have already apply this job"
                            Toast.makeText(applicationContext, msg, Toast.LENGTH_LONG).show()

                        }
                    }
                    if(!(msg.equals("You have already apply this job"))){
                        val appli: Application = Application("Pending", mAuth, jobId)
                        database.child("Application").child((maxid+1).toString()).setValue(appli)
                        msg = "Job Successful Apply"
                        Toast.makeText(applicationContext, msg, Toast.LENGTH_LONG).show()
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {}
            })
            val intent = Intent(this, CategoryActivity::class.java)
            startActivity(intent)
        }

    }

}
