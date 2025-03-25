package com.example.languageapp.di

import android.content.Context
import com.example.languageapp.feature_app.data.manger.QueueMangerImpl
import com.example.languageapp.feature_app.domain.manger.QueueManger
import com.example.languageapp.feature_app.domain.use_case.Queue.GetQueueUseCase
import com.example.languageapp.feature_app.domain.use_case.Queue.SetQueueUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object QueueModule {

    @Provides
    @Singleton
    fun getQueueManger(@ApplicationContext context: Context) : QueueManger{
        return QueueMangerImpl(context)
    }

    @Provides
    @Singleton
    fun getQueue(queueManger: QueueManger) : GetQueueUseCase{
        return GetQueueUseCase(queueManger)
    }
    @Provides
    @Singleton
    fun setQueue(queueManger: QueueManger) : SetQueueUseCase{
        return SetQueueUseCase(queueManger)
    }
}