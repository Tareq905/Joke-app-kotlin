package com.iamkdblue.randomjoke.util

import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.iamkdblue.randomjoke.R

fun View.snackbar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).also { snackbar ->
        snackbar.setActionTextColor(
            ContextCompat.getColor(context, R.color.white)
        )
        snackbar.setAction("OK")
        {
            snackbar.dismiss()

        }
    }.show()
}