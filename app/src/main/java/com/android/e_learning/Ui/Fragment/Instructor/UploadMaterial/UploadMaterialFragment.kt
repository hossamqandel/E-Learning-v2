package com.android.e_learning.Ui.Fragment.Instructor.UploadMaterial

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.android.e_learning.R
import com.android.e_learning.Utils.Const
import com.android.e_learning.databinding.FragmentUploadMaterialBinding


class UploadMaterialFragment : Fragment() {

    var _binding: FragmentUploadMaterialBinding? = null
    val binding get() = _binding!!
    private val mIntent = Intent()
    lateinit var startForResult: ActivityResultLauncher<Intent>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        _binding = FragmentUploadMaterialBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if (result != null && result.resultCode == RESULT_OK){

            }
        }

    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    }

   private fun Any.accessGallery(intent: Intent){
        intent.type = Const.INTENT_IMG_TYPE
        intent.action = Intent.ACTION_GET_CONTENT
       startForResult.contract(mIntent, Const.INTENT_IMG_REQUEST_CODE)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}