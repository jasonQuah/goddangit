package com.example.mobileassignment

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class StaffHomePage : AppCompatActivity() {

    private var mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.staff_main_page)

        val addBtn = findViewById<Button>(R.id.addjobBtn)
        val viewBtn = findViewById<Button>(R.id.viewjobBtn)
        val logoutBtn = findViewById<Button>(R.id.logoutBtn)

        addBtn.setOnClickListener {
            finish()
            startActivity(Intent(this, addJobActivity::class.java))
        }
        viewBtn.setOnClickListener {
            finish()
            startActivity(Intent(this, ViewJobActivity::class.java))
        }
        logoutBtn.setOnClickListener {
            finish()
            mAuth.signOut()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}