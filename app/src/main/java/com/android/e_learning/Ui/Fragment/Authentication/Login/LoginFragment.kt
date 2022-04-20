package com.android.e_learning.Ui.Fragment.Authentication.Login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.android.e_learning.R
import com.android.e_learning.Utils.customeNavigator
import com.android.e_learning.Utils.toastShort
import com.android.e_learning.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    val mAuth = FirebaseAuth.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            logInNavigateToSignUpBTN.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
            }

            loginLoginBTN.setOnClickListener{
                val email = loginEmailET.text.toString().trim().lowercase()
                val password = loginPasswordET.text.toString().trim().lowercase()
                if (!email.isEmpty() && !password.isEmpty()){
                    checkLoginRequest(email, password)
                }
            }
        }


    }


    private fun checkLoginRequest(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val currentUserId = mAuth.currentUser?.uid
                    binding.loginEmailET.text.clear()
                    binding.loginPasswordET.text.clear()
//                    SharedPref.cacheUserData(email, password, currentUserId!!)
                    customeNavigator(R.id.action_loginFragment_to_profileFragment)
                    toastShort(context, "Login Success")
                } else {
                    toastShort(context, "Login Failed."+it.exception?.message)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}