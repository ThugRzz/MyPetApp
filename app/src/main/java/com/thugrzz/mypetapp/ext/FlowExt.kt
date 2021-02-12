package com.thugrzz.mypetapp.ext

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

fun EditText.asFlow(resetTarget: () -> Unit): Flow<EditText> {
    return Channel<EditText>(capacity = Channel.UNLIMITED).also { channel ->
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                    this@asFlow.clearError()
                    resetTarget()
                    channel.offer(this@asFlow)
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }.receiveAsFlow()
}