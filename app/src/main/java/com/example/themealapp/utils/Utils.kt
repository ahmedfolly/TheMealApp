package com.example.themealapp.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment


inline fun <T : View> T.showIf(condition: () -> Boolean) {
    if (condition()) {
        show()
    } else {
        hide()
    }
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun showKeyboard(context: Context, editText: EditText){
    if (editText.requestFocus()){
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED)
    }

}
fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}
