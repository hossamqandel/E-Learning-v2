package com.android.e_learning.Ui.Fragment.Instructor.ShowGrades

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.android.e_learning.Adapter.InstGradesAdapter
import com.android.e_learning.databinding.FragmentShowGradesBinding

class ShowGradesFragment : Fragment() {

    //TODO: Add The ProgressBar to XML
    //TODO: Observe on progressBar and ToastMessage

    private var _binding: FragmentShowGradesBinding? = null
    private val binding get() = _binding!!
    private var courseId: String? = null
    private val viewModel: InstGradesViewModel by viewModels()
    private val mAdapter = InstGradesAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        _binding = FragmentShowGradesBinding.inflate(inflater, container, false)
        val view = binding.root
        courseId = ShowGradesFragmentArgs.fromBundle(requireArguments()).courseId
        viewModel.fetchStudentGrades(courseId!!)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        viewModel.mutableOfStudentsGrades.observe(viewLifecycleOwner){
            mAdapter.setListToAdapter(it)
            binding.recyclerGrades.adapter = mAdapter
        }
    }

    override fun onResume() {
        super.onResume()

        binding.swipe.setOnRefreshListener { binding.swipe.isRefreshing = false }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}