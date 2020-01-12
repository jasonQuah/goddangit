package com.example.mobileassignment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileassignment.models.User
import com.example.mobileassignment.models.ViewJob
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.ArrayList

class ViewJobActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var mListadapter: ViewJobAdapter
    private val data = ArrayList<ViewJob>()
    private val data1 = ArrayList<User>()
    private lateinit var jobDatabase: DatabaseReference
    private lateinit var userDatabase: DatabaseReference
    private lateinit var loManage: RecyclerView.LayoutManager
    var maxid: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_job_list)

        var mAuth = FirebaseAuth.getInstance().currentUser!!.uid

        val tv: TextView = findViewById(R.id.textView)
        val bckBtn = findViewById<Button>(R.id.bckbutton)

        bckBtn.setOnClickListener{
            finish()
            startActivity(Intent(this, StaffHomePage::class.java))
        }

        jobDatabase = FirebaseDatabase.getInstance().reference.child("Job")

        jobDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists())
                    maxid = p0.childrenCount
                for (i in 1..maxid) {
                    if (p0.child((i).toString()).child("userId").value.toString().equals(mAuth)) {
                        var comName: String = ""
                        var companyId: String =
                            p0.child(i.toString()).child("userId").value.toString()

                        userDatabase = FirebaseDatabase.getInstance().reference.child("User")

                        userDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(ds: DataSnapshot) {
                                if (ds.exists())
                                    maxid = ds.childrenCount
                                for (i in 1..maxid) {
                                    if (ds.child((i).toString()).child("user_id").value.toString().equals(companyId)) {
                                        comName = ds.child((i).toString()).child("user_name").value.toString()
                                    }
                                }
                                data.add(
                                    ViewJob(
                                        p0.child((i).toString()).child("position").value.toString(),
                                        comName,
                                        p0.child((i).toString()).child("desc").value.toString(),
                                        p0.child((i).toString()).child("jobid").value.toString()
                                    )
                                )
                                tv.text = p0.child((i).toString()).child("jobid").value.toString()
                            }
                            override fun onCancelled(p0: DatabaseError) {
                            }
                        })
                    }
                }
                tv.visibility = View.INVISIBLE
            }

            override fun onCancelled(p0: DatabaseError) {
            }
        })

        buildRecyclerView()
    }

    open fun buildRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        loManage = LinearLayoutManager(this)
        mListadapter = ViewJobAdapter(data)
        recyclerView.layoutManager = loManage
        recyclerView.adapter = mListadapter
        mListadapter.setOnItemClickListener(
            ViewJobAdapter.OnItemClickListener(
                fun(it: Int) {
                    val i = Intent(this, JobDetailsActivity::class.java)
                    i.putExtra("jobId", data.get(it).jobId)
                    startActivity(i)
                })
        )

    }
}
