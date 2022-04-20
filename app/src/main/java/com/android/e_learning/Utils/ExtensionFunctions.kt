package com.android.e_learning.Utils

import android.content.Context
import androidx.fragment.app.Fragment

interface ExtensionFunctions {

    fun Fragment.copyToClipboard(context: Context, text: String?)
    fun Fragment.toastLong(context: Context, text: String?)
    fun Fragment.toastShort(context: Context, text: String?)

}