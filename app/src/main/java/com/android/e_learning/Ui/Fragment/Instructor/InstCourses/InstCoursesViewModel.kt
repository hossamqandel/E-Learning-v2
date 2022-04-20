package com.android.e_learning.Ui.Fragment.Instructor.InstCourses

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.e_learning.Adapter.InstCoursesAdapter
import com.android.e_learning.Model.CourseModel
import com.android.e_learning.Utils.Const
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InstCoursesViewModel : ViewModel() {

    val mCourseMutable = MutableLiveData<ArrayList<CourseModel>>()
    val mProgressBar = MutableLiveData<Int>()
    var list = ArrayList<CourseModel>()

    fun fetchCourses() {
        mProgressBar.postValue(View.VISIBLE)
        viewModelScope.launch(Dispatchers.IO) {
            Const.mDatabaseref.child(Const.COURSES)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        list.clear()
                        var courseData = snapshot.children
                        courseData.forEach {
                            var userModel = it.getValue(CourseModel::class.java)
                            list.add(userModel!!)
                        }
                        mCourseMutable.postValue(list)
                        mProgressBar.postValue(View.GONE)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        mProgressBar.postValue(View.GONE)
                    }

                })
        }
    }
}