package com.android.e_learning.Model

data class UserModel(
    var userId: String? = null,
    var fullName: String? = null,
    var age: String? = null,
    var phoneNumber: String? = null,
    var email: String? = null,
    var password: String? = null,
    var isVerified: Boolean? = null,
    var userType: String? = null,
)