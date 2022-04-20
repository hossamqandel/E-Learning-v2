package com.android.e_learning.Ui.Fragment.Authentication.Registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.android.e_learning.Model.UserModel
import com.android.e_learning.R
import com.android.e_learning.Utils.Const
import com.android.e_learning.Utils.customeNavigator
import com.android.e_learning.Utils.toastLong
import com.android.e_learning.databinding.FragmentRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegistrationFragment : Fragment() {
    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!
    val mAuth = FirebaseAuth.getInstance()
    private var userType: String? = null
//    val mDatabaseRef = FirebaseDatabase.getInstance().reference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                userType = parent?.getItemAtPosition(position).toString().lowercase()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.registreationSignupBTN.setOnClickListener {
            authRequest()
        }

        binding.registreationLoginNavigateBTN.setOnClickListener{
            navigateToLogin()
        }


    }



    fun authRequest() {
        binding.apply {
            val fullName = registreationFullNameET.text.toString().trim()
            val age = registreationAgeET.text.toString().trim()
            val phoneNumber = registreationPhoneNumberET.text.toString().trim()
            val email = registreationEmailET.text.toString().trim().lowercase()
            val password = registreationPasswordET.text.toString().trim()

            validation(UserModel("", fullName, age, phoneNumber, email, password, false, userType!!))
        }
    }

    private fun validation(userModel: UserModel) {
        val fullName = userModel.fullName
        val age = userModel.age
        val phoneNumber = userModel.phoneNumber
        val email = userModel.email
        val password = userModel.password

        binding.apply {
            if (fullName!!.length < 2) { registreationFullNameET.error = "Minimum length is 2" }
            if (age!!.isEmpty()) { registreationFullNameET.error = Const.REQUIRED }

            if (age!!.length < 2) { registreationAgeET.error = "Minimum length is 2" }
            if (age.isEmpty()) { registreationAgeET.error = Const.REQUIRED }

            if (phoneNumber!!.length != 11) { registreationPhoneNumberET.error = "Phone Number Must be 11 Elements" }
            if (phoneNumber.isEmpty()) { registreationPhoneNumberET.error = Const.REQUIRED }

            if (email!!.isEmpty()) { registreationEmailET.error = Const.REQUIRED }
            if (password!!.isEmpty()) { registreationPasswordET.error = Const.REQUIRED }
            if (password.length < 8) { registreationPasswordET.error = "Minimum Password length is 8" }

            else { createUserWithEmailAndPassword(userModel) }
        }
    }


    private fun createUserWithEmailAndPassword(userModel: UserModel) {
        val email = userModel.email
        val password = userModel.password
        mAuth.createUserWithEmailAndPassword(email!!, password!!)
            .addOnCompleteListener{
                if (it.isSuccessful) {
                    saveUserDataOnRealTimeDatabase(userModel)
                    toastLong(context, "Registration Success")
                }
            }
    }


    private fun saveUserDataOnRealTimeDatabase(userModel: UserModel) {
        userModel.userId = mAuth.currentUser?.uid!!
        Const.mDatabaseref.child(Const.USERS).child(mAuth.currentUser?.uid!!).setValue(userModel)
//        SharedPref.cacheUserData(userModel.email, userModel.password, userModel.userId)

        binding.apply {
            registreationFullNameET.text.clear()
            registreationAgeET.text.clear()
            registreationPhoneNumberET.text.clear()
            registreationEmailET.text.clear()
            registreationPasswordET.text.clear()
        }

        customeNavigator(R.id.action_registrationFragment_to_profileFragment3)
        toastLong(context, mAuth.currentUser?.uid+"")
    }

    private fun navigateToLogin(){
        requireActivity().onBackPressed()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}