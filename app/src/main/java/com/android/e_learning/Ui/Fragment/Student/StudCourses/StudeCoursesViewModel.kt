package com.android.e_learning.Ui.Fragment.Student.StudCourses

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.e_learning.Model.SubscribedCoursesModel
import com.android.e_learning.Utils.Const
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.ArrayList

class StudeCoursesViewModel : ViewModel() {

    private val mDatabaseRef = FirebaseDatabase.getInstance().reference
    private val currentStudentId = FirebaseAuth.getInstance().currentUser?.uid
    private var listOfStudentSubscribedCourses = ArrayList<SubscribedCoursesModel>()
    var mutableOfStudentSubscribedCourses = MutableLiveData<ArrayList<SubscribedCoursesModel>>()
    val toastMessage = MutableLiveData<String>()

    fun fetchCourseNameByIdFromFirebase(courseId: String?){
        viewModelScope.launch(Dispatchers.IO){

        mDatabaseRef.child(Const.COURSES)
            .child(courseId!!)
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        var subModel = snapshot.getValue(SubscribedCoursesModel::class.java)
                        StudentCourseSubscriptionRequest(subModel!!)
                    }

                    else {
                        toastMessage.postValue("There is no course with this Id.. Make sure you wrote the course Id right")
                    }

                }
                override fun onCancelled(error: DatabaseError) {
                    toastMessage.postValue(error.message)
                }
            })
        }

    }

    private fun StudentCourseSubscriptionRequest(SubscribedCoursesModel: SubscribedCoursesModel){
        viewModelScope.launch {
        mDatabaseRef.child(Const.STUDENTS_SUBSCRIPTIONS)
            .child(currentStudentId!!)
            .push()
            .setValue(SubscribedCoursesModel)
            toastMessage.postValue("Course Added Successfully.. Refresh to view the course")
        }
    }


    fun fetchStudentSubscriptions(){
        listOfStudentSubscribedCourses.clear()
        viewModelScope.launch(Dispatchers.IO) {
        mDatabaseRef.child(Const.STUDENTS_SUBSCRIPTIONS).child(currentStudentId!!).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var model = snapshot.children
                model.forEach {
                    var courses = it.getValue(SubscribedCoursesModel::class.java)
                    listOfStudentSubscribedCourses.add(courses!!)
                }
                mutableOfStudentSubscribedCourses.postValue(listOfStudentSubscribedCourses)
            }

            override fun onCancelled(error: DatabaseError) {
                toastMessage.postValue(error.message)
            }
        })
        }
    }


}