package com.android.e_learning.Utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController


fun Any.copyToClipboard(context: Context?, text: String?) {
        var clipboard: ClipboardManager = context?.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("label", text)
        toastLong(context,"Copied")
        clipboard.setPrimaryClip(clip)
    }

    fun Any.accessGallery(intent: Intent){
        intent.type = Const.INTENT_IMG_TYPE
        intent.action = Intent.ACTION_GET_CONTENT

    }
    fun Any.toastLong(context: Context?, text: String?) {
        Toast.makeText(context, "$text", Toast.LENGTH_LONG).show()
    }

    fun Any.toastShort(context: Context?, text: String?) {
        Toast.makeText(context, "$text", Toast.LENGTH_SHORT).show()
    }


    fun Fragment.customeNavigator(action: Int?){
        findNavController().navigate(action!!)
    }

//    fun Fragment.navigateWithData(action: Int, data: (Int) -> Unit){
//        findNavController().navigate(action)
//    }




