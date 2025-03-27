package com.example.languageapp.feature_app.presentation

import android.os.Bundle
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.languageapp.feature_app.presentation.LanguageSelect.LanguageSelectScreen
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
                            ProfileScreen(navController){
                                darkTheme = !darkTheme
                            }
                        }
                        composable(Route.ProfileResizePhotoScreen.route){
                            ProfileResizePhotoScreen(navController)
                        }
                        composable(Route.WordPractice.route){
                            WordPracticeScreen(navController)
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