package com.example.languageapp.feature_app.data.manger

import android.content.Context
import com.example.languageapp.feature_app.domain.manger.QueueManger
import androidx.core.content.edit

class QueueMangerImpl(
    context: Context
) : QueueManger {

    private val key = "KEY"
    private val sp = context.getSharedPreferences(key, Context.MODE_PRIVATE)

    override fun setQueue(queue: Int) {
        sp.edit { clear().putInt(key, queue) }
    }

    override fun getQueue(): Int {
        return sp.getInt(key, 0)
    }
}