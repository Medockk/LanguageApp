package com.example.languageapp.feature_app.presentation.common

import android.content.Context
import androidx.glance.Button
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.action.actionStartActivity
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxWidth
import androidx.glance.state.PreferencesGlanceStateDefinition
import com.example.languageapp.R
import com.example.languageapp.feature_app.presentation.MainActivity.MainActivity


class Widget : GlanceAppWidget() {

    override val stateDefinition = PreferencesGlanceStateDefinition

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            Column {
                Button(
                    text = context.getString(R.string.start_activity),
                    onClick = actionStartActivity<MainActivity>(),
                    modifier = GlanceModifier.fillMaxWidth()
                )
            }
        }
    }


}


class WidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget
        get() = Widget()
}

