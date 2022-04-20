package com.android.e_learning.Data.Local.SharedPreferences

import android.app.Application
import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth

class SharedPref {

    val mAuth = FirebaseAuth.getInstance().currentUser
    private val application = Application()

    val sharedPref = application.getSharedPreferences("userLoginData", Application.MODE_PRIVATE)
    val editor = sharedPref.edit()

    fun cacheUserData(email: String, password: String, uId: String){
        val email = email.toString().trim().lowercase()
        val password = password.toString().trim().lowercase()

        editor.apply() {
            putString("email", email)
            putString("password", password)
            putString("id", uId)
            apply()
        }
    }

    fun getAppSharedPref(): SharedPreferences {

        return sharedPref
    }

}