package com.example.languageapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.task.vision.classifier.Classifications
import org.tensorflow.lite.task.vision.classifier.ImageClassifier
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.net.URL

class ImageClassifierTest(private val context: Context) {
    private var classifier: ImageClassifier? = null

    init {
        try {
            val options = ImageClassifier.ImageClassifierOptions.builder()
                .setMaxResults(1) // Топ-5 результатов
                .build()

            classifier = ImageClassifier.createFromFileAndOptions(
                context,
                "model.tflite", // имя вашего файла модели
                options
            )
        } catch (e: Exception) {
            Log.e("ImageClassifier", "Error initializing classifier", e)
        }
    }

    fun classify(bitmap: Bitmap): List<Classifications>? {
        if (classifier == null) {
            Log.e("ImageClassifier", "Classifier is not initialized")
            return null
        }

        // Преобразуем Bitmap в TensorImage
        val tensorImage = TensorImage.fromBitmap(bitmap)

        // Классификация
        return classifier?.classify(tensorImage)
    }
}

fun module(context: Context) = ImageClassifier.createFromFileAndOptions(
        context,
        "model.tflite",
        ImageClassifier.ImageClassifierOptions.builder().setMaxResults(1).build()
    )

class teRepoImpl(private val imageClassifier: ImageClassifier) : teRepo{

    override suspend fun classify(byteArray: ByteArray): String {
        val image = TensorImage.fromBitmap(byteArray.toBitmap())
        return imageClassifier.classify(image).firstOrNull()?.categories?.joinToString("\n"){it.label} ?: "xz"
    }
    private fun ByteArray.toBitmap() : Bitmap{
        return BitmapFactory.decodeByteArray(this, 0, this.size)
    }
    private fun Bitmap.toByteArray() : ByteArray{
        val baos = ByteArrayOutputStream()
        this.compress(Bitmap.CompressFormat.PNG, 100, baos)
        return baos.toByteArray()
    }
}

interface teRepo{
    suspend fun classify(byteArray: ByteArray) : String
}
class teUseCase(private val teRepo: teRepo){
    suspend operator fun invoke(byteArray: ByteArray) = teRepo.classify(byteArray)
}

@Composable
fun TensorFlowLiteApp() {
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    var resultText by remember { mutableStateOf("") }
    val context = LocalContext.current
    val imageClassifier = remember { ImageClassifierTest(context) }
    val coroutine = rememberCoroutineScope()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            try {
                val inputStream = context.contentResolver.openInputStream(uri)
                coroutine.launch(Dispatchers.IO) {
                    val t = BitmapFactory.decodeStream(
                        URL("https://3dnews.ru/assets/external/illustrations/2025/01/28/1117365/deepseek_01.jpg")
                            .openConnection().getInputStream()
                    )
                    Log.e("bit", "bit")

                    withContext(Dispatchers.Main) {
                        bitmap = t
                        // Классификация изображения
                        t?.let { bmp ->
                            val classifications = imageClassifier.classify(bmp)
                            resultText =
                                classifications?.firstOrNull()?.categories?.joinToString("\n") {
                                    it.label
                                } ?: "Не удалось классифицировать"
                        }
                    }
                }
                val selectedBitmap = BitmapFactory.decodeStream(inputStream)
                inputStream?.close()

            } catch (e: IOException) {
                e.printStackTrace()
                resultText = "Ошибка загрузки изображения"
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "TensorFlow Lite Classifier",
            style = MaterialTheme.typography.headlineSmall
        )

        Button(onClick = { launcher.launch("image/*") }) {
            Text("Выбрать изображение")
        }

        if (bitmap != null) {
            Image(
                bitmap = bitmap!!.asImageBitmap(),
                contentDescription = "Selected image",
                modifier = Modifier.size(300.dp)
            )
        } else {
            Box(
                modifier = Modifier
                    .size(300.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Text("Изображение не выбрано")
            }
        }

        if (resultText.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = resultText,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}


class SpeechRecognizer(private val context: Context, private val apiKey: String) {
    private val client = OkHttpClient()
    private var audioRecord: AudioRecord? = null
    private var isRecording = false

    companion object {
        private const val SAMPLE_RATE = 16000
        private const val CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO
        private const val AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT
        private val BUFFER_SIZE = AudioRecord.getMinBufferSize(
            SAMPLE_RATE,
            CHANNEL_CONFIG,
            AUDIO_FORMAT
        )
    }

    @RequiresPermission(Manifest.permission.RECORD_AUDIO)
    fun startRecording(
        onResult: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        if (isRecording) return

        try {
            audioRecord = AudioRecord(
                MediaRecorder.AudioSource.MIC,
                SAMPLE_RATE,
                CHANNEL_CONFIG,
                AUDIO_FORMAT,
                BUFFER_SIZE
            )

            audioRecord?.startRecording()
            isRecording = true

            Thread {
                val audioData = ByteArray(BUFFER_SIZE)
                val outputStream = ByteArrayOutputStream()

                while (isRecording) {
                    val bytesRead = audioRecord?.read(audioData, 0, BUFFER_SIZE) ?: 0
                    if (bytesRead > 0) {
                        outputStream.write(audioData, 0, bytesRead)
                    }
                }

                val audioBytes = outputStream.toByteArray()
                outputStream.close()
                recognizeSpeech(audioBytes, onResult, onError)
            }.start()
        } catch (e: Exception) {
            onError("Ошибка записи: ${e.message}")
        }
    }

    fun stopRecording() {
        isRecording = false
        audioRecord?.stop()
        audioRecord?.release()
        audioRecord = null
    }

    private fun recognizeSpeech(
        audioData: ByteArray,
        onResult: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart(
                "audio",
                "audio.pcm",
                audioData.toRequestBody("audio/x-pcm".toMediaType())
            )
            .build()

        val request = Request.Builder()
            .url("https://stt.api.cloud.yandex.net/speech/v1/stt:recognize?lang=ru-RU")
            .addHeader("Authorization", "Api-Key $apiKey")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                onError("Ошибка сети: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                try {
                    if (response.isSuccessful && !responseBody.isNullOrEmpty()) {
                        val json = JSONObject(responseBody)
                        val result = json.optString("result", "Не удалось распознать")
                        onResult(result)
                    } else {
                        onError("Ошибка сервера: $responseBody")
                    }
                } catch (e: Exception) {
                    onError("Ошибка обработки ответа: ${e.message}")
                }
            }
        })
    }
}

@Composable
fun SpeechRecognitionApp() {
    val context = LocalContext.current
    val speechRecognizer = remember {
        SpeechRecognizer(
            context = context,
            apiKey = "your-api-key-here" // Замените на ваш API-ключ
        )
    }

    var isRecording by remember { mutableStateOf(false) }
    var recognizedText by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = if (isRecording) "Говорите..." else "Нажмите для записи",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                if (isRecording) {
                    speechRecognizer.stopRecording()
                    isRecording = false
                } else {
                    recognizedText = ""
                    errorMessage = ""
                    if (ActivityCompat.checkSelfPermission(
                            context,
                            Manifest.permission.RECORD_AUDIO
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        speechRecognizer.startRecording(
                            onResult = { result ->
                                recognizedText = result
                            },
                            onError = { error ->
                                errorMessage = error
                            }
                        )
                        isRecording = true
                    }
                }
            },
            modifier = Modifier.size(150.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isRecording) MaterialTheme.colorScheme.errorContainer
                else MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Text(if (isRecording) "Стоп" else "Запись")
        }

        Spacer(modifier = Modifier.height(32.dp))

        if (recognizedText.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = recognizedText,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}