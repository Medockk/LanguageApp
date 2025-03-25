package com.example.languageapp

import android.content.res.AssetManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.languageapp.ui.theme.LanguageAppTheme
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.channels.FileChannel

class MainActivity : ComponentActivity() {
    private lateinit var interpreter: Interpreter
    private lateinit var byteBuffer: ByteBuffer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        try {
            byteBuffer = getByteBuffer(this.assets, "model.tflite")
            interpreter = Interpreter(byteBuffer)
        } catch (e: Exception) {
            Log.e("exception", e.message.toString())
        }
        setContent {
            LanguageAppTheme {

            }
        }
    }

    private fun getByteBuffer(assetManager: AssetManager, path: String) : ByteBuffer{
        val fd = assetManager.openFd(path)
        val inputStream = FileInputStream(fd.fileDescriptor)
        val channel = inputStream.channel
        val position = fd.startOffset
        val size = fd.declaredLength

        return channel.map(FileChannel.MapMode.READ_ONLY, position, size)
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LanguageAppTheme {
        Greeting("Android")
    }
}