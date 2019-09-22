package com.techchallenge.core.util.ext

import android.app.Activity
import android.app.AlertDialog
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Activity.showAlert(
    title: String? = "",
    message: String? = "",
    positiveButtonText: String? = "",
    negativeButtonText: String? = "",
    positiveButton: () -> Unit? = {},
    negativeButton: () -> Unit? = {}
) {
    AlertDialog.Builder(this).apply {
        title?.let(::setTitle)
        message?.let(::setMessage)
        setPositiveButton(positiveButtonText) { dialog, _ ->
            positiveButton.invoke()
            dialog.dismiss()
        }
        setNegativeButton(negativeButtonText) { dialog, _ ->
            negativeButton.invoke()
            dialog.dismiss()
        }
    }.show()
}

inline fun <reified T : Any> Activity.launchActivity(
    noinline init: Intent.() -> Unit = {}
) {
    val intent = newIntent<T>()
    intent.init()
    startActivity(intent)
}

inline fun <reified T : Any> Activity.newIntent(): Intent =
    Intent(this, T::class.java)

fun Activity.hideKeyBoard() {
    val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    var view = currentFocus
    if (view == null) {
        view = View(this)
    }
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}
