package com.example.languageapp.feature_app.presentation.Listening

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class ListeningViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _state = mutableStateOf(ListeningState())
    val state: State<ListeningState> = _state

    init {
        val speech = SpeechRecognizer.createSpeechRecognizer(context)
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, context.packageName)
        speech.startListening(intent)

        speech.setRecognitionListener(object : RecognitionListener{
            override fun onReadyForSpeech(params: Bundle?) {
                Log.e("speech", "ready")
            }

            override fun onBeginningOfSpeech() {
                Log.e("speech", "begin")
            }

            override fun onRmsChanged(rmsdB: Float) {
            }

            override fun onBufferReceived(buffer: ByteArray?) {
                Log.e("speech", "buffer")
            }

            override fun onEndOfSpeech() {
                Log.e("speech", "end")
            }

            override fun onError(error: Int) {
                Log.e("speech", "error $error")
            }

            override fun onResults(results: Bundle?) {
                val array = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                array?.forEach {
                    Log.e("speech", it)
                }
                speech.startListening(intent)
            }

            override fun onPartialResults(partialResults: Bundle?) {
                Log.e("speech", "partial")
            }

            override fun onSegmentResults(segmentResults: Bundle) {
                super.onSegmentResults(segmentResults)
                Log.e("speech", "segment")
            }

            override fun onEndOfSegmentedSession() {
                super.onEndOfSegmentedSession()
            }

            override fun onLanguageDetection(results: Bundle) {
                super.onLanguageDetection(results)
            }

            override fun onEvent(eventType: Int, params: Bundle?) {
                Log.e("speech", "event")
            }
        })
    }

    fun onEvent(event: ListeningEvent){
        when (event){
            ListeningEvent.ChangeListeningClick -> {
                _state.value = state.value.copy(
                    isListening = !_state.value.isListening,
                    isRightAnswer = true
                )
            }
            ListeningEvent.CheckMySpeechClick -> {

                _state.value = state.value.copy(
                    isRightAnswer = false,
                )
            }
            ListeningEvent.NextClick -> {

            }
            ListeningEvent.ResetException -> {
                _state.value = state.value.copy(exception = "")
            }

            is ListeningEvent.EnterUserAnswer -> {
                _state.value = state.value.copy(
                    userAnswer = event.value
                )
            }
        }
    }
}