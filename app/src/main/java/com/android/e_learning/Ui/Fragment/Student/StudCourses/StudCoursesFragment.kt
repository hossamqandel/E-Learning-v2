package com.android.e_learning.Ui.Fragment.Student.StudCourses

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.e_learning.Adapter.StudCoursesAdapter
import com.android.e_learning.R
import com.android.e_learning.Utils.toastLong
import com.android.e_learning.Utils.toastShort
import com.android.e_learning.databinding.FragmentStudCoursesBinding


class StudCoursesFragment : Fragment() {
    private var _binding : FragmentStudCoursesBinding? = null
    private val binding get() = _binding!!
    private lateinit var mAdapter: StudCoursesAdapter
    private var refreshCounter = 0 /** this variable is just for testing swiperRefresher*/
    private val viewModel: StudeCoursesViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        _binding = FragmentStudCoursesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchStudentSubscriptions()

        viewModel.mutableOfStudentSubscribedCourses.observe(viewLifecycleOwner){
            mAdapter.setStudentCoursesList(it)
            binding.studRecycler.adapter = mAdapter
        }

        mAdapter = StudCoursesAdapter {
            findNavController().navigate(
                StudCoursesFragmentDirections.actionStudCoursesFragmentToCourseDetailsFragment(it!!))
        }
    }

    override fun onStart() {
        super.onStart()

    }

    override fun onResume() {
        super.onResume()
        binding.studCourseADDBTN.setOnClickListener {
        var courseId = binding.studCourseIdET.text.toString().trim()
        if (courseId.isNotEmpty()){
            viewModel.fetchCourseNameByIdFromFirebase(courseId)
            binding.studCourseIdET.text.clear()
        }
            else {
            }
        }


        viewModel.toastMessage.observe(viewLifecycleOwner){
            toastLong(context, it)
        }

        binding.swiper.setOnRefreshListener {
            binding.swiper.isRefreshing = false
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}