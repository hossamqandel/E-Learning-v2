package com.android.e_learning.Ui.Fragment.Instructor.Control

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.R
import androidx.navigation.fragment.findNavController
import com.android.e_learning.Utils.copyToClipboard
import com.android.e_learning.Utils.customeNavigator
import com.android.e_learning.databinding.FragmentControlBinding

class ControlFragment : Fragment() {

    private var _binding : FragmentControlBinding? = null
    private val binding get() = _binding!!
    private var currentCourseId: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        _binding = FragmentControlBinding.inflate(inflater, container, false)
        val view = binding.root
        currentCourseId = ControlFragmentArgs.fromBundle(requireArguments()).courseId
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.CurrentCourseIdTV.text = currentCourseId
    }

    override fun onResume() {
        super.onResume()
        binding.CurrentCourseIdTV.setOnLongClickListener{
            val text = binding.CurrentCourseIdTV.text.toString()
            copyToClipboard(context, text)
            true
        }

        onClicks()
    }

    private fun onClicks(){
        binding.apply {
            firstCardView.setOnClickListener{
            findNavController().navigate(ControlFragmentDirections.actionControlFragmentToAttendanceFragment(currentCourseId!!))
        }

            secondeCardView.setOnClickListener{
                findNavController().navigate(ControlFragmentDirections.actionControlFragmentToCreateQuizFragment(currentCourseId!!))
            }

            thirdCardView.setOnClickListener {
                findNavController().navigate(ControlFragmentDirections.actionControlFragmentToUploadMaterialFragment(currentCourseId!!))
            }

            fourthCardView.setOnClickListener {
                findNavController().navigate(ControlFragmentDirections.actionControlFragmentToShowGradesFragment(currentCourseId!!))
            }

            instChat.setOnClickListener {
                findNavController().navigate(ControlFragmentDirections.actionControlFragmentToChatFragment(currentCourseId!!))
            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}