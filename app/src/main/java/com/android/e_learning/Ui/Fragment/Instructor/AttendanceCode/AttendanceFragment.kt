package com.android.e_learning.Ui.Fragment.Instructor.AttendanceCode

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.android.e_learning.R
import com.android.e_learning.Utils.Const
import com.android.e_learning.Utils.toastLong
import com.android.e_learning.Utils.toastShort
import com.android.e_learning.databinding.FragmentAttendanceBinding
import com.google.firebase.database.FirebaseDatabase
import java.util.concurrent.ThreadLocalRandom


class AttendanceFragment : Fragment() {

    var _binding: FragmentAttendanceBinding? = null
    val binding get() = _binding!!
    var currentCourseId: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        _binding = FragmentAttendanceBinding.inflate(inflater, container, false)
        val view = binding.root
        currentCourseId = AttendanceFragmentArgs.fromBundle(requireArguments()).courseId
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        generateCode()
        setCourseAttendanceCode()
        onBackPress()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun setCourseAttendanceCode() {
        binding.attendanceConfirmBTN.setOnClickListener {
            textAndButtonStatus()
        }
    }


    private fun generateCode(){
        binding.attendanceGenerateCodeBTN.setOnClickListener {
            var code = ThreadLocalRandom.current().nextInt(1000, 9998 + 1).toString()
            binding.attendanceCodeTV.text = code
        }
    }

    private fun textAndButtonStatus(){
        val codeTV = binding.attendanceCodeTV.text.toString()

        if (codeTV.isNotEmpty()){
            Const.mDatabaseref.child(Const.ATTENDANCE).child(currentCourseId!!).child(codeTV).setValue("")
                .addOnSuccessListener {
                    toastLong(context, "Success .. Code Submited")
                    binding.attendanceConfirmBTN.isEnabled = false
                    binding.attendanceConfirmBTN.setBackgroundColor(Color.parseColor("#8c8c8c"))
                    requireActivity().onBackPressed()
                }
        }
        else {
            toastShort(context, "Generate Attemdance code first.. !!")
        }
    }


    private fun onBackPress(){
        binding.attendanceBackPressBTN.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

}