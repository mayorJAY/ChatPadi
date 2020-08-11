package com.josycom.mayorjay.chatpadi.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

/**
 * Inflate layout using layout inflater with context from the parent
 */
fun ViewGroup.inflate(@LayoutRes layout: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layout, this, attachToRoot)
}

/**
 * Function to create SnackBar
 */
fun View.snack(
    message: String?,
    length: Int = Snackbar.LENGTH_SHORT,
    actionText: String? = null,
    actionCallBack: (() -> Unit)? = null
) {
    Snackbar.make(this, message.toString(), length).apply {
        actionCallBack?.let {
            actionText?.let {
                setAction(actionText) {
                    actionCallBack()
                }
            }
        }
    }.show()
}

/**
 * Function to convert Date-time
 */
fun Date?.convertTime(): String {
    if (this == null) return ""
    val formatter = SimpleDateFormat("hh:mm a", Locale.US)
    formatter.timeZone = TimeZone.getDefault()
    return formatter.format(this)
}