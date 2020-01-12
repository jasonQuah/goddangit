package com.example.mobileassignment.models


data class User(
    val user_id: String ="",
    val user_role: String = "",
    val user_name: String ="",
    val user_password: String ="",
    val user_email: String ="",
    val user_contactNo: String ="",
    val user_age: Int,
    val user_address: String =""
)

