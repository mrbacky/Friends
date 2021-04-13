package com.rado.friends.util

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity


val <T> T.exhaustive: T
    get() = this

//fun hideSoftKeyboard(activity: FragmentActivity?) {
//    if (activity != null) {
//        if (activity.getCurrentFocus() == null) {
//            return
//        }
//    }
//    val inputMethodManager =
//        activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//    inputMethodManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
//}


//private fun closeKeyboard() {
//    val view = this.currentFocus
//    if (view != null) {
//        val imm = ContextCompat.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.hideSoftInputFromWindow(view.windowToken, 0)
//    }
//}