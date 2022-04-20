package com.android.e_learning.Ui.Fragment.Common.Profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.e_learning.Model.UserModel
import com.android.e_learning.Utils.Const
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private val mDatabaseRef = FirebaseDatabase.getInstance().reference
    private val mAuth = FirebaseAuth.getInstance().currentUser
    private val currentUserId = mAuth?.uid
    var userModel = MutableLiveData<UserModel>()

    fun readUserProfileData() {
        viewModelScope.launch(Dispatchers.IO) {
            mDatabaseRef.child(Const.USERS).child(currentUserId!!)
                .addListenerForSingleValueEvent(object : ValueEventListener {

                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            var user = snapshot.getValue(UserModel::class.java)
                            userModel.postValue(user!!)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                    }
                })
        }

    }

}