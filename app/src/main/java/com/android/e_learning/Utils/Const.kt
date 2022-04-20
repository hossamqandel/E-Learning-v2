package com.android.e_learning.Utils

import android.app.Application
import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

object Const {

    val INSTRUCTOR = "instructor"
    val STUDENT = "student"
    val CHATS = "chats"
    val ATTENDANCE = "attendance"
    val COURSES = "courses"
    val INSTRUCTOR_ID = "instructorId"
    val COURSES_QUIZ = "courses quiz"
    val REQUIRED = "required"
    val USERS = "users"
    val LOGGED_OUT = "Logged Out"
    val STUDENTS_SUBSCRIPTIONS = " students subscriptions"
    val QUIZ_ANSWER = "quiz answer"

    /** Firebase Constants */
    val mDatabaseref = FirebaseDatabase.getInstance().reference
    val currentUserId = FirebaseAuth.getInstance().currentUser?.uid.toString()

    /** Intent Constants */
//    val mIntent = Intent()
    val INTENT_IMG_TYPE = "image/*"
    val INTENT_IMG_REQUEST_CODE = 1



    val applicationContext = Application()


}