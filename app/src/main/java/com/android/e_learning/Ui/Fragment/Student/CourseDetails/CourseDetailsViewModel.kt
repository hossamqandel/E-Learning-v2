package com.android.e_learning.Ui.Fragment.Student.CourseDetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.e_learning.Utils.Const
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch

class CourseDetailsViewModel : ViewModel(){

    val toastMutable = MutableLiveData<String>()
    private val currentUserId = FirebaseAuth.getInstance().currentUser?.uid.toString()

    fun studentAttendanceRequest(currentCourseId: String, attendanceCode: String){
        viewModelScope.launch {
            Const.mDatabaseref.child(Const.ATTENDANCE).child(currentCourseId).addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.hasChild(attendanceCode)){

                        if (snapshot.child(attendanceCode).hasChild(currentUserId)){
                            toastMutable.postValue("Already Attended")
                        }

                        else {
                            submitStudentAttendance(currentCourseId, attendanceCode)
                        }

                    }

                    else {
                        toastMutable.postValue("Wrong Code")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    toastMutable.postValue(error.message)
                }

            })
        }
    }

    private fun submitStudentAttendance(courseId: String, attendanceCode: String) {
        Const.mDatabaseref.child(Const.ATTENDANCE)
            .child(courseId)
            .child(attendanceCode)
            .child(Const.currentUserId)
            .setValue(Const.currentUserId)
        toastMutable.postValue("Attendance Submited Successfully")
    }
}