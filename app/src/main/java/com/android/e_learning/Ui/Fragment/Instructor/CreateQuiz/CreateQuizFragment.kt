package com.android.e_learning.Ui.Fragment.Instructor.CreateQuiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.android.e_learning.Model.QuizModel
import com.android.e_learning.Utils.toastLong
import com.android.e_learning.Utils.toastShort
import com.android.e_learning.databinding.FragmentCreateQuizBinding

class CreateQuizFragment : Fragment() {
    var _binding: FragmentCreateQuizBinding? = null
    val binding get() = _binding!!
    var courseId: String? = null
    val viewModel: CreateQuizViewModel by viewModels()
    var listOfQuistions = ArrayList<QuizModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        _binding = FragmentCreateQuizBinding.inflate(inflater, container, false)
        val view = binding.root
        courseId = CreateQuizFragmentArgs.fromBundle(requireArguments()).courseId
        listOfQuistions.clear()
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

        binding.clearFieldsBTN.setOnClickListener {
            clear()
        }

        binding.removeLastQuistionBTN.setOnClickListener {
            viewModel.removeLastQuistion(listOfQuistions)
        }

        toastObserve()

        binding.addQuistionBTN.setOnClickListener {
            addQuistionToList()
        }

        binding.uploadAssignmentBTN.setOnClickListener {
            viewModel.pushQuistionsOnFirebase(courseId, listOfQuistions)
            if (listOfQuistions.isNotEmpty()){
                binding.uploadAssignmentBTN.isEnabled = false
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        listOfQuistions.clear()
    }


    private fun clear(){
        binding.apply {
            quistion.text.clear()
            firstAnswerET.text.clear()
            secondAnswerET.text.clear()
            thirdAnswerET.text.clear()
            lastAnswerET.text.clear()
            radioGroup.clearCheck()
        }
    }

    private fun toastObserve(){
        viewModel.toastMessage.observe(viewLifecycleOwner){
            toastShort(context, it)
        }
    }

    private fun addQuistionToList(){
        binding.apply {
            var quistion = quistion.text.toString().trim()
            var firstAnswer = firstAnswerET.text.toString().trim()
            var secondAnswer = secondAnswerET.text.toString().trim()
            var thirdAnswer = thirdAnswerET.text.toString().trim()
            var lastAnswer = lastAnswerET.text.toString().trim()

            var radioId: Int = radioGroup.checkedRadioButtonId
            var radioButton: View? = binding!!.radioGroup.findViewById<RadioButton>(radioId)
            var currentMarked = radioGroup.indexOfChild(radioButton) /** if nothing selected will return -1*/

                if (quistion.isNotEmpty() && firstAnswer.isNotEmpty() && secondAnswer.isNotEmpty()
                && thirdAnswer.isNotEmpty() && lastAnswer.isNotEmpty() && currentMarked != -1) {
                    listOfQuistions.add(QuizModel(quistion, firstAnswer, secondAnswer, thirdAnswer, lastAnswer, currentMarked+1))
                    clear()
                    toastShort(context, listOfQuistions.size.toString())

                } else {
                toastShort(context, "You Must to fill all fields with Data and choose one of these buttons")
            }

        }


    }
}