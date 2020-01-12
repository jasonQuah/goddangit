package com.example.mobileassignment.models

import com.google.firebase.auth.FirebaseUser

data class WorkingExperience (val experienceId:String = "", val companyName: String = "", val specification: String = "", val jobPosition: String = "", val joinedDate: String = "", val leftDate: String = "", val user_id: FirebaseUser?)