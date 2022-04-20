package com.android.e_learning.Ui.Fragment.Student.SolveQuiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.viewModels
import com.android.e_learning.Model.QuizModel
import com.android.e_learning.Utils.toastShort
import com.android.e_learning.databinding.FragmentSolveQuizBinding

class SolveQuizFragment : Fragment() {

    private var _binding: FragmentSolveQuizBinding? = null
    private var listOfQuizzes = ArrayList<QuizModel>()
    private val binding get() = _binding!!
    private var courseId: String? = null
    private var quizId: String? = null
    val viewModel: SolveQuizViewModel by viewModels()

    private val NOTHING_SELECTED = -1
    var totalQuistions: Int? = null
    var score = 0
    var currentQuistion: QuizModel? = null
    var counter = 0
    var isAnswerd: Boolean? = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        _binding = FragmentSolveQuizBinding.inflate(inflater, container, false)
        val view = binding.root
        progressBarObserver()
        hideUiUntileDataArrive()
        courseId = SolveQuizFragmentArgs.fromBundle(requireArguments()).courseId
        quizId = SolveQuizFragmentArgs.fromBundle(requireArguments()).quizId
        viewModel.fetchQuizzesByParentId(courseId!!, quizId!!)
        setData()
        setUiErrorMessage()
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

        binding.solveQuizSubmitBTN.setOnClickListener {
            checkRadioSelection()
        }
    }


    private fun showNextQuistion() {

        viewModel.mutableOfQuizzes.observe(viewLifecycleOwner) {


                var radioId: Int = binding.radioGroup2.checkedRadioButtonId
                var radioButton: View? = binding.radioGroup2.findViewById<RadioButton>(radioId)
                var currentMarked = binding.radioGroup2.indexOfChild(radioButton)


                if (currentMarked != -1){
                    if (currentMarked+1 == currentQuistion?.rightAnswer) {
                        score++
                        counter++
                        toastShort(context, "New Score = ${score} ")
                    }
                    else {
                        counter++
                        toastShort(context, "Same Score = ${score}")
                    }
                }

            else {
                toastShort(context, "Select on of RB")
                }


            binding.radioGroup2.clearCheck()



        }

    }

    private fun checkAnswer() {
        isAnswerd = true
        var radioId: Int = binding.radioGroup2.checkedRadioButtonId
        var radioButton: View? = binding.radioGroup2.findViewById<RadioButton>(radioId)
        var currentMarked = binding.radioGroup2.indexOfChild(radioButton) + 1

        if (currentMarked == currentQuistion?.rightAnswer) {
            score++
            toastShort(context, "Score: ${score}")
        }

        if (counter < totalQuistions!!) {
            binding.solveQuizSubmitBTN.text = "Next Quistion"
        } else {
            binding.solveQuizSubmitBTN.text = "Finish"
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun hideUiUntileDataArrive(){
        viewModel.mutableOfUserInterface.observe(viewLifecycleOwner){
            binding.solveQuizQuistionCard.visibility = it
            binding.solveQuizFirstAnswerCard.visibility = it
            binding.solveQuizSecondAnswerCard.visibility = it
            binding.solveQuizThirdAnswerCard.visibility = it
            binding.solveQuizLastAnswerCard.visibility = it
            binding.solveQuizSubmitBTN.visibility = it
        }

    }
    private fun progressBarObserver(){
        viewModel.mutableOfProgressBar.observe(viewLifecycleOwner){
            binding.progress.visibility = it
        }

    }
    private fun setUiErrorMessage(){
        viewModel.mutableError.observe(viewLifecycleOwner){
            binding.solveQuizSetErrorTV.text = it.toString()
        }
    }

    private fun setData(){
        viewModel.mutableOfQuizzes.observe(viewLifecycleOwner){
            totalQuistions = it.size

            if (it.size != 0){
                if (counter < it.size - 1) {
                    setUi()
                    binding.solveQuizSubmitBTN.text = "Next Quistion"
                }

                else if (counter == it.size -1 ){
                    setUi()
                    binding.solveQuizSubmitBTN.text = "Submit"
                }

                else {
                    viewModel.getCurrentUserData(courseId.toString(), quizId.toString(), score.toString())
                    requireActivity().onBackPressed()

                    /** Write a code that upload the final Student score and then active the onBackPressed*/
//                    requireActivity().onBackPressed()
                }
            }

        }


    }
    private fun validation(){
        validateForFirstQuistion()
    }

    private fun validateForFirstQuistion(){
        viewModel.mutableOfQuizzes.observe(viewLifecycleOwner){
            if (counter == 0){
                if (it.size != 0){
                    checkRadioSelection()
                }
            }
            validateForNextQuistion()
        }
    }

    private fun validateForNextQuistion(){
        if (counter > 0){
            checkRadioSelection()
        }
    }


    private fun checkRadioSelection(){
        var radioId: Int = binding.radioGroup2.checkedRadioButtonId
        var radioButton: View? = binding.radioGroup2.findViewById<RadioButton>(radioId)
        var currentMarked = binding.radioGroup2.indexOfChild(radioButton)

        if (currentMarked == NOTHING_SELECTED){
            toastShort(context, "Please Select an option")
        }

        else {
            currentMarked++
            viewModel.mutableOfQuizzes.observe(viewLifecycleOwner){
                if (currentMarked == it[counter].rightAnswer){
                    score++
                    counter++
                    setData()
                    toastShort(context, "New Score = $score")
                }
                else {
                    counter++
                    setData()
                    toastShort(context, "Same Score = $score")
                }
                binding.radioGroup2.clearCheck()
            }
        }

    }


    private fun setUi(){
        viewModel.mutableOfQuizzes.observe(viewLifecycleOwner){
            binding.solveQuizQuistionTV.text = "${it.get(counter).question} ?"
            binding.solveQuizFirstAnswerTV.text = it.get(counter).firstAnswer
            binding.solveQuizSecondAnswerTV.text = it.get(counter).secondeAnswer
            binding.solveQuizThirdAnswerTV.text = it.get(counter).thirdAnswer
            binding.solveQuizLastAnswerTV.text = it.get(counter).lastAnswer
        }
    }
}