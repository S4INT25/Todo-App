package com.lucky.todo_list_app.util

import android.app.Activity
import android.content.Context
import android.hardware.input.InputManager
import android.view.inputmethod.InputMethodManager

fun hideKeyboard(activity: Activity)
{
    val inputManager =
        activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    val currentFocusedView = activity.currentFocus
    currentFocusedView?.let {
        inputManager.hideSoftInputFromWindow(
            currentFocusedView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS
        )
    }

}