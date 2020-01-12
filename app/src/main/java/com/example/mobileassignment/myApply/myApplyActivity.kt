package com.example.mobileassignment.myApply

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileassignment.R
import com.example.mobileassignment.jobDetail.jobDetail
import com.example.mobileassignment.jobList.Job
import com.example.mobileassignment.jobList.jobListAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class myApplyActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var ref: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: myApplyAdapter
    private lateinit var loManage: RecyclerView.LayoutManager
    var maxid: Long = 0
    val jobList: ArrayList<Job>  = ArrayList()
    var mAuth = FirebaseAuth.getInstance().currentUser!!.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myapply)

        title = "My Application"

        val tv: TextView = findViewById(R.id.textView)
        val btnPending: Button = findViewById(R.id.btnPending)
        val btnApprove: Button = findViewById(R.id.btnApprove)
        val btnReject: Button = findViewById(R.id.btnReject)

        tv.visibility = View.INVISIBLE

        btnPending.background.alpha = 100
        btnApprove.background.alpha = 10
        btnReject.background.alpha = 10
        selStatus("Pending", tv)

        btnPending.setOnClickListener{
            selStatus("Pending", tv)
            btnPending.background.alpha = 100
            btnApprove.background.alpha = 10
            btnReject.background.alpha = 10
        }

        btnApprove.setOnClickListener{
            selStatus("Approved", tv)
            btnPending.background.alpha = 10
            btnApprove.background.alpha = 100
            btnReject.background.alpha = 10
        }

        btnReject.setOnClickListener{
            selStatus("Rejected", tv)
            btnPending.background.alpha = 10
            btnApprove.background.alpha = 10
            btnReject.background.alpha = 100
        }

    }

    fun listAdd(sta: String, tv: TextView): ArrayList<String>{
        val jobL: ArrayList<String>  = ArrayList()
        ref = FirebaseDatabase.getInstance().reference.child("Application")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists())
                    maxid = dataSnapshot.childrenCount
                for (i in 1..maxid){
                    if(dataSnapshot.child((i).toString()).child("userId").value.toString().equals(mAuth) && dataSnapshot.child((i).toString()).child("status").value.toString().equals(sta)){
                        jobL.add(dataSnapshot.child(i.toString()).child("jobId").value.toString())
                        tv.text = jobL.size.toString() + mAuth
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // handle error
            }
        })
        return jobL
    }

    fun selStatus(sta: String, tv: TextView){
        val jobL: ArrayList<String> = listAdd(sta, tv)
        ref = FirebaseDatabase.getInstance().reference.child("Job")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists())
                    maxid = dataSnapshot.childrenCount
                for (i in 1..maxid){
                    for(q in 1..jobL.size) {
                        if (i.toString().equals(jobL.get(q-1))) {
                            var comName: String = ""
                            var position: String = ""
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
                                            comName =
                                                ds.child(q.toString()).child("user_name").value.toString()
                                            position =
                                                ds.child(q.toString()).child("user_address").value.toString()
                                            jobList.add(
                                                Job(
                                                    dataSnapshot.child((i).toString()).child("position").value.toString(),
                                                    comName,
                                                    position,
                                                    dataSnapshot.child((i).toString()).child("requirement").value.toString(),
                                                    dataSnapshot.child((i).toString()).child("id").value.toString()
                                                )
                                            )
                                            tv.text = dataSnapshot.child((i).toString()).child("id")
                                                .value.toString()
                                        }
                                    }
                                }

                                override fun onCancelled(databaseError: DatabaseError) {}
                            })

                        }
                    }
                }
                tv.visibility = View.INVISIBLE
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // handle error
            }
        })
        recyclerView = findViewById(R.id.recycleView)
        recyclerView.setHasFixedSize(true)
        loManage = LinearLayoutManager(this);
        adapter = myApplyAdapter(jobList)
        recyclerView.layoutManager = loManage
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener(myApplyAdapter.OnItemClickListener(
            fun(it: Int) {
                val intent = Intent(this, jobDetail::class.java)
                intent.putExtra("jobId", jobList.get(it).jobId)
                intent.putExtra("disp", "no")
                startActivity(intent)
            }))
        jobL.clear()
        jobList.clear()
    }
}
