package com.android.e_learning.Ui.Fragment.Instructor.AddCourse

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.e_learning.Model.CourseModel
import com.android.e_learning.Utils.Const
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch

class AddCourseViewModel : ViewModel() {

    val courseNameMutable = MutableLiveData<String>()
    val quizGradeMutable = MutableLiveData<String>()
    val projectGradeMutable = MutableLiveData<String>()
    val attendanceGradeMutable = MutableLiveData<String>()
    val toastMessage = MutableLiveData<String>()

    private val mDatabaseRef = FirebaseDatabase.getInstance().reference
    private val uId = mDatabaseRef.push().key

}