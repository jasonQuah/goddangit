package com.example.mobileassignment.models

import com.google.firebase.auth.FirebaseUser


data class Education(val educationId:String = "", val schoolName:String = "", val fieldOfStudy:String = "", val qualification:String = "", val graduateDate: String ="", val user_id: FirebaseUser?)
