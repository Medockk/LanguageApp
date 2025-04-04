package com.example.languageapp.feature_app.domain.use_case.Queue

import com.example.languageapp.feature_app.domain.manger.QueueManger

class SetQueueUseCase(
    private val queueManger: QueueManger
) {

    operator fun invoke(queue: Int){
        queueManger.setQueue(queue)
    }
}