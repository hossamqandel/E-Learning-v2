package com.android.e_learning.Ui.Fragment.Instructor.CreateQuiz

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.e_learning.Model.QuizModel
import com.android.e_learning.Utils.Const
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateQuizViewModel : ViewModel() {

    val toastMessage = MutableLiveData<String?>()


    fun pushQuistionsOnFirebase(currentCourseId: String?, listOfQuistions: ArrayList<QuizModel>) {
        viewModelScope.launch(Dispatchers.IO) {
            if (listOfQuistions.size != 0) {
                Const.mDatabaseref.child(Const.COURSES_QUIZ).child(currentCourseId!!).push()
                    .setValue(listOfQuistions);
                toastMessage.postValue("Assignment has been uploaded Successfully")
            } else {
                toastMessage.postValue("Failed to upload The assignment because List is empty")
            }
        }

    }


    fun removeLastQuistion(listOfQuistions: ArrayList<QuizModel>) {
        viewModelScope.launch(Dispatchers.IO) {
            if (listOfQuistions.size != 0) {
                toastMessage.postValue("Last quistion you added has been removed " + listOfQuistions.size.toString())
                listOfQuistions.removeAt(listOfQuistions.size - 1)
            } else {
                toastMessage.postValue("List is empty, You didn't add any quistions Yet")
            }
        }

    }
}