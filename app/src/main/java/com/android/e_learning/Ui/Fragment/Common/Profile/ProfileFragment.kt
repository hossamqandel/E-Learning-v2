package com.android.e_learning.Ui.Fragment.Common.Profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.e_learning.R
import com.android.e_learning.Utils.Const
import com.android.e_learning.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    val viewModel: ProfileViewModel by viewModels()
    private val mDatabaseRef = FirebaseDatabase.getInstance().reference
    private val mAuth = FirebaseAuth.getInstance().currentUser
    private val currentUserId = mAuth?.uid


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onStart() {
        super.onStart()
        setText()

        binding.profileCONTINUEBTN.setOnClickListener {
            navigate()
        }

    }

    override fun onResume() {
        super.onResume()
        binding.profileLOGOUTBTN.setOnClickListener {
            Firebase.auth.signOut()
            requireActivity().onBackPressed()
        }

        onBackPressedExit()

    }


    private fun onBackPressedExit() {

        var callBack = requireActivity().onBackPressedDispatcher.addCallback(this) {
            if (fragmentManager?.backStackEntryCount!! > 0)
                requireActivity().onBackPressed()
            fragmentManager?.popBackStack()
        }
    }


    private fun setText() {
        viewModel.readUserProfileData()

        viewModel.userModel.observe(viewLifecycleOwner) {
            binding.apply {
                profileEMAILTV.text = it.email
                profileNAMETV.text = it.fullName
                profilePHONENUMBERTV.text = it.phoneNumber

                if (it.isVerified!!) {
                    profileVERIFYTV.visibility = View.VISIBLE
                } else {
                    profileVERIFYTV.visibility = View.GONE
                }
            }
        }
    }

    private fun navigate() {
        viewModel.userModel.observe(viewLifecycleOwner) {
            if (it.userType.equals(Const.INSTRUCTOR)) {
                findNavController().navigate(R.id.action_profileFragment_to_instCoursesFragment)
            } else {
                findNavController().navigate(R.id.action_profileFragment_to_studCoursesFragment)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}