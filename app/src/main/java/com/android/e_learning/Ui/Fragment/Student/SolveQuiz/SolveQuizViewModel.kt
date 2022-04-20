package com.android.e_learning.Ui.Fragment.Student.SolveQuiz

import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.e_learning.Model.QuizModel
import com.android.e_learning.Model.UserAnswerModel
import com.android.e_learning.Model.UserModel
import com.android.e_learning.Utils.Const
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SolveQuizViewModel : ViewModel() {

    private var listOfQuizzes = ArrayList<QuizModel>()

    val mutableToast = MutableLiveData<String>()
    val mutableError = MutableLiveData<String>()
    val mutableOfQuizzes = MutableLiveData<ArrayList<QuizModel>>()
    val mutableOfProgressBar = MutableLiveData<Int>()
    val mutableOfUserInterface = MutableLiveData<Int>()

    fun fetchQuizzesByParentId(courseId: String, quizId: String) {
        viewModelScope.launch(Dispatchers.IO) {

            mutableOfProgressBar.postValue(View.VISIBLE)
            mutableOfUserInterface.postValue(View.GONE)
            delay(500)

            Const.mDatabaseref.child(Const.COURSES_QUIZ).child(courseId).child(quizId)
                .addListenerForSingleValueEvent(object : ValueEventListener {

                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            listOfQuizzes.clear()
                            mutableOfProgressBar.postValue(View.GONE)

                            if (snapshot.value != null) {
                                for (customSnapshot in snapshot.children) {
                                    listOfQuizzes.add(customSnapshot.getValue(QuizModel::class.java)!!)
                                }

                                mutableOfUserInterface.postValue(View.VISIBLE)
                                    mutableOfQuizzes.postValue(listOfQuizzes)

                            } else {
                                mutableToast.postValue("No Quistions To Show")
                            }

                        } else {
                            mutableOfUserInterface.postValue(View.GONE)
                            mutableError.postValue("Error.. Please check your internet connection or maybe instructor didn't upload Quistions to this Quiz")

                        }
                    }



                    override fun onCancelled(error: DatabaseError) {
                        mutableToast.postValue(error.message)
                        mutableOfProgressBar.postValue(View.GONE)
                        mutableOfUserInterface.postValue(View.GONE)
                        mutableError.postValue("Error.. Please check your internet connection or maybe instructor didn't upload Quistions to this Quiz")
                    }
                })
        }
    }


    fun getCurrentUserData(courseId: String, quizId: String, score: String){

        Const.mDatabaseref.child(Const.USERS).child(Const.currentUserId).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    var userModel = snapshot.getValue(UserModel::class.java)
                    val userId = userModel?.userId
                    val userName = userModel?.fullName
                    val userEmail = userModel?.email
                    pushStudentScoreToFirebase(courseId, quizId, UserAnswerModel(userId!!, userName!!, userEmail!!, score))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }


    private fun pushStudentScoreToFirebase(courseId: String, quizId: String, userAnswerModel: UserAnswerModel){
        Const.mDatabaseref.child(Const.QUIZ_ANSWER).child(courseId).child(quizId).child(Const.currentUserId).setValue(userAnswerModel)
    }
}