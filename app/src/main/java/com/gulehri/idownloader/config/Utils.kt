package com.gulehri.idownloader.config

import android.app.Activity
import android.content.ClipboardManager
import android.content.Context
import android.view.inputmethod.InputMethodManager


// Created by Shahid Iqbal on 10/6/2022.

object Utils {


    fun Activity.hideKeyboard(){
        val inputManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(this.currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

}