package com.android.e_learning.Ui.Fragment.Student.Quizzes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.e_learning.Utils.Const
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class QuizzesViewModel : ViewModel() {

    private var listOfQuizzes = ArrayList<String>()
    var MutableListOfQuizzes = MutableLiveData<ArrayList<String>>()

    fun getCurrentCourseQuizzesById(courseId: String){
        listOfQuizzes.clear()
        Const.mDatabaseref.child(Const.COURSES_QUIZ).child(courseId).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.value != null){

                    for (customeSnapshot in snapshot.children){
                        listOfQuizzes.add(customeSnapshot.key!!)
                    }
                    MutableListOfQuizzes.postValue(listOfQuizzes)

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}