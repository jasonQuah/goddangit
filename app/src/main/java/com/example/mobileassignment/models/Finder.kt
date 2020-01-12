package com.example.mobileassignment.models

import com.google.firebase.auth.FirebaseUser

data class Finder(
    val finder_id: String = "",
    val user_age: Int,
    val user_id: FirebaseUser?
)