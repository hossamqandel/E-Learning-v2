package com.android.e_learning.Ui.Fragment.Splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.android.e_learning.R
import com.android.e_learning.Utils.customeNavigator
import com.android.e_learning.databinding.FragmentSplashBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashFragment : Fragment() {
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!
    private val SPLASH_DISPLAY_TIMER: Long = 2500
    var mAuth = FirebaseAuth.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


    override fun onResume() {
        super.onResume()
        authStatus()
    }

    private fun authStatus() {
        lifecycleScope.launchWhenCreated {
            if (mAuth.currentUser != null) {
                launch {
                    delay(SPLASH_DISPLAY_TIMER)
                    customeNavigator(R.id.action_splashFragment_to_profileFragment)
                }
            } else {
                launch {
                    delay(SPLASH_DISPLAY_TIMER)
                    customeNavigator(R.id.action_splashFragment_to_loginFragment)
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}