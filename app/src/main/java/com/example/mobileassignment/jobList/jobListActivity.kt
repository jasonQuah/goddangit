package com.example.mobileassignment.jobList

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.mobileassignment.jobDetail.jobDetail
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileassignment.R
import com.google.firebase.database.*

class jobListActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var ref: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: jobListAdapter
    private lateinit var loManage: RecyclerView.LayoutManager
    var maxid: Long = 0
    val jobList: ArrayList<Job>  = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_joblist)

        var cateName: String = intent.getStringExtra("Name")
        title = cateName

        val tv: TextView = findViewById(R.id.textView)

        ref = FirebaseDatabase.getInstance().reference.child("Job")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists())
                    maxid = dataSnapshot.childrenCount
                else
                    maxid = 0
                for (i in 1..maxid){
                    if(dataSnapshot.child((i).toString()).child("category").value.toString().equals(cateName)){
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
                                        comName = "Company: " + ds.child(q.toString()).child("user_name").value.toString()
                                        position = ds.child(q.toString()).child("user_address").value.toString()
                                        jobList.add(Job("Position: " + dataSnapshot.child((i).toString()).child("position").value.toString(), comName, position, dataSnapshot.child((i).toString()).child("requirement").value.toString(), i.toString()))
                                        tv.text = dataSnapshot.child((i).toString()).child("id").value.toString()
                                    }
                                }
                            }
                            override fun onCancelled(databaseError: DatabaseError) {}
                        })

                    }
                }
                tv.visibility = View.INVISIBLE
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // handle error
            }
        })

        buildRecyclerView()
    }

    open fun buildRecyclerView(){
        val tv: TextView = findViewById(R.id.textView)
        recyclerView = findViewById(R.id.recycleView)
        recyclerView.setHasFixedSize(true)
        loManage = LinearLayoutManager(this);
        adapter = jobListAdapter(jobList)
        recyclerView.layoutManager = loManage
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener(jobListAdapter.OnItemClickListener(
            fun(it: Int) {
                val intent = Intent(this, jobDetail::class.java)
                intent.putExtra("disp", "yes")
                intent.putExtra("jobId", jobList.get(it).jobId)
                startActivity(intent)
            }))
    }
}
