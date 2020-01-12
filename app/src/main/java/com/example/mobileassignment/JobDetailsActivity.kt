package com.example.mobileassignment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileassignment.models.cardViewApplication
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.job_details.*
import org.w3c.dom.Text
import java.util.ArrayList

class JobDetailsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var mListadapter1: ViewApplyAdapter
    private lateinit var jobDatabase: DatabaseReference
    private lateinit var jobDatabase1: DatabaseReference
    private lateinit var applyDatabase: DatabaseReference
    private lateinit var userDatabase: DatabaseReference
    private val data = ArrayList<cardViewApplication>()
    private lateinit var loManage: RecyclerView.LayoutManager
    var maxid: Long = 0
    var mAuth = FirebaseAuth.getInstance().currentUser!!.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.job_details)
        var jobId: String = intent.getStringExtra("jobId")

        val bckBtn = findViewById<Button>(R.id.bckBtn)

        bckBtn.setOnClickListener{
            finish()
            startActivity(Intent(this, ViewJobActivity::class.java))
        }

        jobDatabase = FirebaseDatabase.getInstance().reference.child("Job")

        jobDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists())
                    maxid = p0.childrenCount
                for (i in 1..maxid){
                    if(p0.child((i).toString()).child("jobid").value.toString().equals(jobId)){
                        profiletxt.setText(p0.child((i).toString()).child("position").value.toString())
                        salarytxt.setText("RM" + p0.child((i).toString()).child("salary").value.toString())
                        requirementtxt.text =  p0.child((i).toString()).child("requirement").value.toString()
                        descriptiontxt.text =  p0.child((i).toString()).child("desc").value.toString()
                        categorytxt.text =  p0.child((i).toString()).child("category").value.toString()
                        var jobb: String = p0.child(i.toString()).key.toString()
                    }
                }
            }
            override fun onCancelled(p0: DatabaseError) {
            }
        })

        //start RecyclerView
        jobDatabase1 = FirebaseDatabase.getInstance().reference.child("Job")

        val tv: TextView = findViewById(R.id.textViewid)

        jobDatabase1.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists())
                    maxid = p0.childrenCount
                for (a in 1..maxid){
                    if(p0.child((a).toString()).child("jobid").value.toString().equals(jobId)){
                        var username: String = ""
                        var userAddress: String = ""
                        var userfake: String = ""
                        var useridd: String = ""
                        var jobb: String = p0.child(a.toString()).key.toString()
                        //here
                        applyDatabase = FirebaseDatabase.getInstance().getReference("Application")

                        applyDatabase.addListenerForSingleValueEvent(object: ValueEventListener{
                            override fun onDataChange(p1: DataSnapshot) {
                                if (p1.exists())
                                    maxid = p1.childrenCount
                                for (b in 1..maxid) {
                                    if (p1.child((b).toString()).child("jobId").value.toString().equals(jobb)) {
                                        var userId: String = p1.child(b.toString()).child("userId").value.toString()

                                        userDatabase = FirebaseDatabase.getInstance().reference.child("User")

                                        var maxid2: Long = 0
                                        userDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
                                            override fun onDataChange(ds: DataSnapshot) {
                                                if (ds.exists())
                                                    maxid2 = ds.childrenCount
                                                else
                                                    bckBtn.setText("hello")
                                                for (q in 1..maxid2) {
                                                    if (ds.child((q).toString()).child("user_id").value.toString().equals(userId)) {
                                                        useridd = ds.child(q.toString()).child("user_id").value.toString()
                                                        username = ds.child(q.toString()).child("user_name").value.toString()
                                                        userAddress = ds.child(q.toString()).child("user_address").value.toString()
                                                        userfake = ds.child(q.toString()).child("user_age").value.toString()

                                                        data.add(
                                                            cardViewApplication(
                                                                username,
                                                                userAddress,
                                                                userfake,
                                                                userId
                                                            )
                                                        )
                                                    }
                                                }
                                                        tv.text = userId
                                            }
                                            override fun onCancelled(ds: DatabaseError) {
                                            }
                                        })
                                    }
                                }
                                tv.visibility = View.INVISIBLE
                            }
                            override fun onCancelled(p1: DatabaseError) {
                            }
                        })
                        //here
                    }
                }
            }
            override fun onCancelled(p0: DatabaseError) {
            }
        })

        /*applyDatabase.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists())
                    maxid = p0.childrenCount
                for (i in 1..maxid) {
                    if (p0.child((i).toString()).child("jobId").value.toString().equals(jobId)) {
                        var username: String = ""
                        var userAddress: String = ""
                        var userfake: String = ""
                        var userId: String =
                            p0.child(i.toString()).child("userId").value.toString()

                        userDatabase = FirebaseDatabase.getInstance().reference.child("User")

                        userDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(ds: DataSnapshot) {
                                if (ds.exists())
                                    maxid = ds.childrenCount
                                else
                                    maxid = 0
                                for (q in 1..maxid) {
                                    if (ds.child((q).toString()).child("user_id").value.toString().equals(userId)) {
                                        username =
                                            ds.child((q).toString()).child("user_name")
                                                .value.toString()
                                        userAddress =
                                            ds.child((q).toString()).child("user_address")
                                                .value.toString()
                                        userfake =
                                            ds.child((q).toString()).child("user_age")
                                                .value.toString()
                                        data.add(cardViewApplication(
                                            username,
                                            userAddress,
                                            userfake,
                                            userId
                                        )
                                        )
                                        tv.text = userId
                                    }
                                }


                            }
                            override fun onCancelled(ds1: DatabaseError) {
                            }
                        })
                    }
                }
                tv.visibility = View.INVISIBLE
            }
            override fun onCancelled(p0: DatabaseError) {
            }
        })*/

        buildRecyclerView()
    }

    open fun buildRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView2)
        recyclerView.setHasFixedSize(true)
        loManage = LinearLayoutManager(this)
        mListadapter1 = ViewApplyAdapter(data)
        recyclerView.layoutManager = loManage
        recyclerView.adapter = mListadapter1
        mListadapter1.setOnItemClickListener(
            ViewApplyAdapter.OnItemClickListener(
                fun(it: Int) {
                    val i = Intent(this, ViewApplicationActivity::class.java)
                    i.putExtra("userId", data.get(it).userId)
                    i.putExtra("userage", data.get(it).userAge)
                    i.putExtra("userName", data.get(it).userName)
                    i.putExtra("userAddress", data.get(it).userAddress)
                    startActivity(i)
                })
        )
    }
}