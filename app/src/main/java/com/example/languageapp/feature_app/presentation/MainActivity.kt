package com.example.languageapp.feature_app.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.languageapp.feature_app.presentation.OnBoard.OnBoardScreen
import com.example.languageapp.feature_app.presentation.Splash.SplashScreen
import com.example.languageapp.feature_app.presentation.ui.theme.LanguageAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    //    private lateinit var interpreter: Interpreter
//    private lateinit var byteBuffer: ByteBuffer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        try {
//            byteBuffer = getByteBuffer(this.assets, "model.tflite")
//            interpreter = Interpreter(byteBuffer)
//        } catch (e: Exception) {
//            Log.e("exception", e.message.toString())
//        }
        setContent {
            val navController = rememberNavController()
            LanguageAppTheme(
                dynamicColor = false
            ) {
                Scaffold {
                    NavHost(
                        navController,
                        startDestination = Route.SplashScreen.route,
                        enterTransition = {
                            fadeIn()
                        },
                        exitTransition = {
                            fadeOut()
                        },
                        modifier = Modifier
                            .padding(it)
                    ) {
                        composable(Route.SplashScreen.route) {
                            SplashScreen(navController)
                        }
                        composable(Route.OnBoardScreen.route) {
                            OnBoardScreen(navController)
                        }
                    }
                }
            }
        }
    }

//    private fun getByteBuffer(assetManager: AssetManager, path: String) : ByteBuffer{
//        val fd = assetManager.openFd(path)
//        val inputStream = FileInputStream(fd.fileDescriptor)
//        val channel = inputStream.channel
//        val position = fd.startOffset
//        val size = fd.declaredLength
//
//        return channel.map(FileChannel.MapMode.READ_ONLY, position, size)
//    }
}