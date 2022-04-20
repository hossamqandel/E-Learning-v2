package com.android.e_learning.Ui.Fragment.Instructor.ShowGrades

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.e_learning.Model.UserAnswerModel
import com.android.e_learning.Utils.Const
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope.coroutineContext
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext

class InstGradesViewModel() : ViewModel() {

    val mutableOfStudentsGrades = MutableLiveData<ArrayList<UserAnswerModel>>()
    private var ListOfStudentsGrades: MutableList<UserAnswerModel> = ArrayList()
    var mutableOfProgressBar = MutableLiveData<Int>()
    var mutableOfToastMessage = MutableLiveData<String>()

    fun fetchStudentGrades(courseId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            Const.mDatabaseref.child(Const.QUIZ_ANSWER).child(courseId)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        mutableOfProgressBar.postValue(View.VISIBLE)
                        if (snapshot.exists()) {
                            mutableOfProgressBar.postValue(View.GONE)
                            var dataSN = snapshot.children
                            dataSN.forEach {
                                it.children.forEach() {
                                    var useranswerModel = it.getValue(UserAnswerModel::class.java)
                                    try {
                                        if (useranswerModel != null) {
                                            ListOfStudentsGrades.add(useranswerModel)
                                        }
                                    }catch (e: Exception){
                                        mutableOfToastMessage.postValue(e.message.toString())
                                    }
                                }
                            }
                            mutableOfStudentsGrades.postValue(ListOfStudentsGrades as ArrayList<UserAnswerModel>?)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        mutableOfToastMessage.postValue(error.message)
                        mutableOfProgressBar.postValue(View.GONE)

                    }
                })
        }
    }
}