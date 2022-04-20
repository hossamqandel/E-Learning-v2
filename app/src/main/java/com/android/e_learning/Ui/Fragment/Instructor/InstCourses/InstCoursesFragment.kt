package com.android.e_learning.Ui.Fragment.Instructor.InstCourses

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.e_learning.Adapter.InstCoursesAdapter
import com.android.e_learning.Model.CourseModel
import com.android.e_learning.R
import com.android.e_learning.databinding.FragmentInstCoursesBinding

class InstCoursesFragment : Fragment() {
    //TODO : handle progressBar visability in the viewModel

    private var _binding: FragmentInstCoursesBinding? = null
    private val binding get() = _binding!!
    val mViewModel: InstCoursesViewModel by viewModels()
    lateinit var mAdapter: InstCoursesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        _binding = FragmentInstCoursesBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel.fetchCourses()
        progressBarObserve()
        coursesObserve()

    }

    override fun onResume() {
        super.onResume()
        binding.instCoursesAdd.setOnClickListener{
            findNavController().navigate(R.id.action_instCoursesFragment_to_addCourseFragment)
        }
        onItemClick()
    }

    override fun onDestroyView() {
        super.onDestroyView()
         _binding = null
    }






    private fun onItemClick(){
        mAdapter = InstCoursesAdapter {
            findNavController()
                .navigate(InstCoursesFragmentDirections.actionInstCoursesFragmentToControlFragment(it!!))
        }
    }

    private fun coursesObserve(){
        mViewModel.mCourseMutable.observe(viewLifecycleOwner){
            mAdapter.setData(it)
            binding.instCoursesRecycler.adapter = mAdapter
        }
    }

    private fun progressBarObserve(){
        mViewModel.mProgressBar.observe(viewLifecycleOwner){
            binding.instCoursesProgress.visibility = it
        }
    }

}