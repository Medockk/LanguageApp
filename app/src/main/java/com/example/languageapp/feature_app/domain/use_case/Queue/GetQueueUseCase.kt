package com.example.languageapp.feature_app.domain.use_case.Queue

import com.example.languageapp.feature_app.domain.manger.QueueManger

class GetQueueUseCase(
    private val queueManger: QueueManger
) {

    operator fun invoke() : Int{
        return queueManger.getQueue()
    }
}