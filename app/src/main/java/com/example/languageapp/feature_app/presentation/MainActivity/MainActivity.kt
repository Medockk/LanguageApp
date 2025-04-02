package com.example.languageapp.feature_app.presentation.MainActivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.languageapp.feature_app.presentation.Animals.AnimalsScreen
import com.example.languageapp.feature_app.presentation.LanguageSelect.LanguageSelectScreen
import com.example.languageapp.feature_app.presentation.Listening.ListeningScreen
import com.example.languageapp.feature_app.presentation.Login.LoginScreen
import com.example.languageapp.feature_app.presentation.MainScreen.MainScreen
import com.example.languageapp.feature_app.presentation.NoConnection.NoConnectionScreen
import com.example.languageapp.feature_app.presentation.OnBoard.OnBoardScreen
import com.example.languageapp.feature_app.presentation.Profile.ProfileScreen
import com.example.languageapp.feature_app.presentation.ProfileResizePhoto.ProfileResizePhotoScreen
import com.example.languageapp.feature_app.presentation.Route
import com.example.languageapp.feature_app.presentation.SignUp.SignUpScreen
import com.example.languageapp.feature_app.presentation.Splash.SplashScreen
import com.example.languageapp.feature_app.presentation.WordPractice.WordPracticeScreen
import com.example.languageapp.feature_app.presentation.ui.theme.LanguageAppTheme
import com.example.languageapp.feature_app.presentation.ui.theme.primaryColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

//            try {
//                val byteBuffer = getByteBuffer(this.assets, "model.tflite")
//                val interpreter = Interpreter(byteBuffer)
//
//                val output = ByteBuffer.allocateDirect(4)
//                output.order(ByteOrder.nativeOrder())
//                interpreter.run(floatArrayOf(17f), output)
//                output.rewind()
//
//                Log.e("tflite", output.getFloat().toString())
//            } catch (e: Exception) {
//                Log.e("tflite", e.message.toString())
//            }

            val viewModel: MainActivityViewModel = hiltViewModel()
            val state = viewModel.state.value

            viewModel.onEvent(MainActivityEvent.GetUserConfig)

            this.window.statusBarColor = primaryColor.toArgb()
            window.navigationBarColor = Color.Transparent.toArgb()

            val navController = rememberNavController()
            LaunchedEffect(!state.isUserDataNotEmpty) {
                if (state.isUserDataNotEmpty){
                    navController.navigate(Route.MainScreen.route)
                }
            }

            LanguageAppTheme(
                dynamicColor = false,
                darkTheme = state.isSystemInDarkTheme
            ) {
                Scaffold {
                    NavHost(
                        navController,
                        startDestination = Route.SplashScreen.route,
                        enterTransition = {
                            fadeIn(tween(750, easing = LinearOutSlowInEasing))
                        },
                        exitTransition = {
                            fadeOut(tween(750, easing = LinearOutSlowInEasing))
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
                            ProfileScreen(
                                navController,
                                isSystemInDarkTheme = state.isSystemInDarkTheme
                            ) {
                                viewModel.onEvent(
                                    MainActivityEvent.ChangeSystemTheme(
                                        !state.isSystemInDarkTheme
                                    )
                                )
                                state.isSystemInDarkTheme
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

//    private fun getByteBuffer(assetManager: AssetManager, path: String): ByteBuffer {
//        val fd = assetManager.openFd(path)
//        val inputStream = FileInputStream(fd.fileDescriptor)
//        val channel = inputStream.channel
//        val startOffset = fd.startOffset
//        val declaredLength = fd.declaredLength
//
//        return channel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
//    }
}