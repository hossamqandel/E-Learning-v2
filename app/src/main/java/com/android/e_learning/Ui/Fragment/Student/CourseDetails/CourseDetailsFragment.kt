package com.android.e_learning.Ui.Fragment.Student.CourseDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.e_learning.R
import com.android.e_learning.Utils.Const
import com.android.e_learning.Utils.toastShort
import com.android.e_learning.databinding.FragmentCourseDetailsBinding

class CourseDetailsFragment : Fragment() {

    private var _binding: FragmentCourseDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var courseId: String
    private val viewModel: CourseDetailsViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        _binding = FragmentCourseDetailsBinding.inflate(inflater, container, false)
        val view = binding.root
        courseId = CourseDetailsFragmentArgs.fromBundle(requireArguments()).courseId
        toastShort(context, courseId)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onStart() {
        super.onStart()
    }


    override fun onResume() {
        super.onResume()
        onClicks()

        binding.activeATTENDANCEBTN.setOnClickListener {
            var code = binding.studAttendanceCodeET.text.toString().trim()
            if (code.isNotEmpty())
            viewModel.studentAttendanceRequest(courseId, code)
            binding.studAttendanceCodeET.text.clear()
        }

        viewModel.toastMutable.observe(viewLifecycleOwner){
            toastShort(context, it)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    private fun onClicks(){
        binding.apply {
            firstCardview.setOnClickListener {

            }

            secondCardview.setOnClickListener {  }
            thirdCardview.setOnClickListener {
                findNavController().navigate(CourseDetailsFragmentDirections.actionCourseDetailsFragmentToChatFragment(courseId))
            }
            fourthCardview.setOnClickListener {
                findNavController().navigate(CourseDetailsFragmentDirections.actionCourseDetailsFragmentToQuizzesFragment(courseId))
            }
        }
    }
}