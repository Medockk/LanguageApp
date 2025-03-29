package com.example.languageapp.feature_app.presentation

import android.content.res.AssetManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.languageapp.R
import com.example.languageapp.feature_app.presentation.Animals.AnimalsScreen
import com.example.languageapp.feature_app.presentation.LanguageSelect.LanguageSelectScreen
import com.example.languageapp.feature_app.presentation.Listening.ListeningScreen
import com.example.languageapp.feature_app.presentation.Login.LoginScreen
import com.example.languageapp.feature_app.presentation.MainScreen.MainScreen
import com.example.languageapp.feature_app.presentation.NoConnection.NoConnectionScreen
import com.example.languageapp.feature_app.presentation.OnBoard.OnBoardScreen
import com.example.languageapp.feature_app.presentation.Profile.ProfileScreen
import com.example.languageapp.feature_app.presentation.ProfileResizePhoto.ProfileResizePhotoScreen
import com.example.languageapp.feature_app.presentation.SignUp.SignUpScreen
import com.example.languageapp.feature_app.presentation.Splash.SplashScreen
import com.example.languageapp.feature_app.presentation.WordPractice.WordPracticeScreen
import com.example.languageapp.feature_app.presentation.ui.theme.LanguageAppTheme
import com.example.languageapp.feature_app.presentation.ui.theme.primaryColor
import dagger.hilt.android.AndroidEntryPoint
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val image = painterResource(R.drawable.splash_icon)

            try {
                val byteBuffer = getByteBuffer(this.assets, "model.tflite")
                val interpreter = Interpreter(byteBuffer)

                val output = ByteBuffer.allocateDirect(4)
                output.order(ByteOrder.nativeOrder())
                interpreter.run(floatArrayOf(17f), output)
                output.rewind()

                Log.e("tflite", output.getFloat().toString())
            } catch (e: Exception) {
                Log.e("tflite", e.message.toString())
            }



            val isSystemInDarkTheme = isSystemInDarkTheme()
            this.window.statusBarColor = primaryColor.toArgb()
            val navController = rememberNavController()
            var darkTheme by remember { mutableStateOf(isSystemInDarkTheme) }
            LanguageAppTheme(
                dynamicColor = false,
                darkTheme = darkTheme
            ) {
                Scaffold {
                    NavHost(
                        navController,
                        startDestination = Route.SplashScreen.route,
                        enterTransition = {
                            fadeIn(tween(500))
                        },
                        exitTransition = {
                            fadeOut(tween(500))
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
                        composable(Route.LoginScreen.route) {
                            LoginScreen(navController)
                        }
                        composable(Route.SignUpScreen.route) {
                            SignUpScreen(navController)
                        }
                        composable(Route.NoConnectionScreen.route) {
                            NoConnectionScreen(navController)
                        }
                        composable(Route.LanguageSelectScreen.route) {
                            LanguageSelectScreen(navController)
                        }
                        composable(Route.MainScreen.route) {
                            MainScreen(navController)
                        }
                        composable(Route.ProfileScreen.route) {
                            ProfileScreen(navController) {
                                darkTheme = !darkTheme
                            }
                        }
                        composable(Route.ProfileResizePhotoScreen.route) {
                            ProfileResizePhotoScreen(navController)
                        }
                        composable(Route.WordPractice.route) {
                            WordPracticeScreen(navController)
                        }
                        composable(Route.Animals.route) {
                            AnimalsScreen(navController)
                        }
                        composable(Route.Listening.route) {
                            ListeningScreen(navController)
                        }
                    }
                }
            }
        }
    }

    private fun getByteBuffer(assetManager: AssetManager, path: String) : ByteBuffer{
        val fd = assetManager.openFd(path)
        val inputStream = FileInputStream(fd.fileDescriptor)
        val channel = inputStream.channel
        val startOffset = fd.startOffset
        val declaredLength = fd.declaredLength

        return channel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }
}