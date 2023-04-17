package com.example.tamakantest.ui.utils

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.example.tamakantest.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Context.hideKeyboard(view: View) {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun EditText?.getString(): String {
    return this!!.text.trim().toString()
}

@SuppressLint("ClickableViewAccessibility")
fun EditText.clearTextOnDrawableRightClick() {
    setOnTouchListener(View.OnTouchListener { v, event ->
        val DRAWABLE_RIGHT = 2
        try {
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= right - compoundDrawables[DRAWABLE_RIGHT]?.bounds?.width()!!) {
                    clearText()
                    return@OnTouchListener false
                }
            }
        } catch (e: Exception) {
            return@OnTouchListener false
        }
        false
    })
}

fun inputValidation(char: String, et: TextInputEditText, til: TextInputLayout): Boolean {
    when {
        TextUtils.isEmpty(char) -> {
            til.showError("This Field can not be empty")
            return false
        }
        char.isEmpty() -> {
            et.requestFocus()
            return false
        }
        else -> til.hideError()
    }
    return true
}

fun TextInputLayout.showError(errorMessage: String?) {
    isErrorEnabled = true
    error = errorMessage
}

fun TextInputLayout.hideError() {
    isErrorEnabled = false
}


fun TextView.easyOnTextChangedListener(listener: (e: CharSequence) -> Unit) =
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable) {
        }

        override fun beforeTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
            listener(p0)
        }
    })

fun EditText.clearText() {
    text?.clear()
}

fun Fragment.alert(title: CharSequence? = null, message: CharSequence? = null, showCancel: Boolean = false, positiveButtonText: String = "Okey", negativeButtonText: String = "Cancel", listener: ((type: Int) -> Unit)? = null): AlertDialog {

    val builder = MaterialAlertDialogBuilder(requireContext())
    builder.setTitle(title)
    // Display a message on alert dialog
    builder.setMessage(message)
    // Set a positive button and its click listener on alert dialog
    builder.setPositiveButton(positiveButtonText) { dialog, which ->
        dialog.dismiss()
        listener?.invoke(AlertDialog.BUTTON_POSITIVE)
    }
    // Display a negative button on alert dialog
    if (showCancel) {
        builder.setNegativeButton(negativeButtonText) { dialog, which ->
            dialog.dismiss()
            listener?.invoke(AlertDialog.BUTTON_NEGATIVE)
        }
    }

    val dialog = builder.create()
    //val typeface = ResourcesCompat.getFont(requireContext(), R.font.solaiman)
    val textView = dialog.findViewById<TextView>(android.R.id.message)
    //textView?.typeface = typeface
    return dialog
}


@SuppressLint("SuspiciousIndentation")
fun Context.toast(msg: String?, time: Int = Toast.LENGTH_SHORT) {
    if (!msg.isNullOrEmpty())
    Toast.makeText(this, msg, time).show()
}

