package com.android.e_learning.Ui.Fragment.Student.Quizzes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.e_learning.Adapter.QuizzesAdapter
import com.android.e_learning.R
import com.android.e_learning.Utils.toastShort
import com.android.e_learning.databinding.FragmentQuizzesBinding

class QuizzesFragment : Fragment() {

    private var _binding: FragmentQuizzesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: QuizzesViewModel by viewModels()
    lateinit var mAdapter: QuizzesAdapter
    private var currentCourseId: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        _binding = FragmentQuizzesBinding.inflate(inflater, container, false)
        val view = binding.root
        currentCourseId = QuizzesFragmentArgs.fromBundle(requireArguments()).currentCourseId
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.swipRefresh.setOnRefreshListener {
//            binding.swipRefresh.isRefreshing = false
//        }

        mAdapter = QuizzesAdapter {
            findNavController().navigate(QuizzesFragmentDirections.actionQuizzesFragmentToSolveQuizFragment(currentCourseId!!, it!!))
        }
        viewModel.getCurrentCourseQuizzesById(currentCourseId!!)
        viewModel.MutableListOfQuizzes.observe(viewLifecycleOwner){
            mAdapter.setListOfQuizzes(it)
            binding.recyclerQuizzes.adapter = mAdapter
        }

    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}