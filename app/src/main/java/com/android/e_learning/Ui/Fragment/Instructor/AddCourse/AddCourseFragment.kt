package com.android.e_learning.Ui.Fragment.Instructor.AddCourse

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.android.e_learning.Model.CourseModel
import com.android.e_learning.R
import com.android.e_learning.Utils.Const
import com.android.e_learning.databinding.FragmentAddCourseBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AddCourseFragment : Fragment() {
    //TODO : Check on validation again.. app crash when click and on of feilds are empty
    var _binding : FragmentAddCourseBinding? = null
    val viewModel: AddCourseViewModel by viewModels()
    val binding get() = _binding!!
//    private val mDatabaseRef = FirebaseDatabase.getInstance().reference
    var uId : String? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        _binding = FragmentAddCourseBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addCourseConfirmBTN.setOnClickListener{
            binding.apply {
               var courseName: String = addCourseNameET.text.toString().trim()
               var quizGrade = addCourseQuizGradeET.text.toString().toDouble()
               var projectGrade = addCourseProjectGradeET.text.toString().toDouble()
               var attendanceGrade = addCourseAttendanceGradeET.text.toString().toDouble()
               val currentUserId = FirebaseAuth.getInstance().currentUser?.uid.toString()

                if (courseName.isEmpty()) { addCourseNameET.error = ("required") }
                else if (courseName.length < 2) { addCourseNameET.error = ("Length is too short.. at least 2 characters") }

                else if (quizGrade.toString().isEmpty()){addCourseQuizGradeET.error = ("required")}
                else if (quizGrade > 100 || quizGrade < 1){addCourseQuizGradeET.error = ("Assignment Grade Must be Betwen 1 : 100")}
                else if (quizGrade == null){addCourseQuizGradeET.error = ("required")}


                else if (projectGrade.toString().isEmpty()){addCourseProjectGradeET.error = ("required")}
                else if (projectGrade > 100 || projectGrade < 1){addCourseProjectGradeET.error = ("Project Grade Must be Betwen 1 : 100")}

                else if (attendanceGrade.toString().isEmpty()){addCourseAttendanceGradeET.error = ("required")}
                else if (attendanceGrade > 100 || attendanceGrade < 1){addCourseAttendanceGradeET.error = ("Attendance Grade Must be Betwen 1 : 100")}

                else {
                    uId = Const.mDatabaseref.push().key.toString()
                    pushCourseDataOnFirebase(CourseModel(currentUserId, uId!!, courseName, quizGrade, attendanceGrade, projectGrade))
                }
            }
        }

    }

    private fun pushCourseDataOnFirebase(courseModel: CourseModel){
        Const.mDatabaseref.child(Const.COURSES).child(uId!!).setValue(courseModel)
        Toast.makeText(context, "Upload Successed", Toast.LENGTH_LONG).show()
        clearInputs()
    }

    private fun clearInputs(){
        binding.apply {
            addCourseNameET.text.clear()
            addCourseQuizGradeET.text.clear()
            addCourseProjectGradeET.text.clear()
            addCourseAttendanceGradeET.text.clear()
        }
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}