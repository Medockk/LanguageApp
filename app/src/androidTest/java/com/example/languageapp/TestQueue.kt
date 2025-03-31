package com.example.languageapp

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.languageapp.feature_app.domain.use_case.Queue.GetQueueUseCase
import com.example.languageapp.feature_app.domain.use_case.Queue.SetQueueUseCase
import com.example.languageapp.feature_app.presentation.Login.LoginScreen
import com.example.languageapp.feature_app.presentation.OnBoard.OnBoardItem
import com.example.languageapp.feature_app.presentation.OnBoard.OnBoardScreen
import com.example.languageapp.feature_app.presentation.OnBoard.OnBoardViewModel
import com.example.languageapp.feature_app.presentation.Route
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestQueue {

    private lateinit var getQueueUseCase: GetQueueUseCase
    private lateinit var setQueueUseCase: SetQueueUseCase
    private lateinit var onBoardViewModel: OnBoardViewModel

    @get:Rule
    val rule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun initializeQueue() {
        val queueMangerTestImpl = QueueMangerTestImpl()
        getQueueUseCase = GetQueueUseCase(queueMangerTestImpl)
        setQueueUseCase = SetQueueUseCase(queueMangerTestImpl)

        onBoardViewModel = OnBoardViewModel(getQueueUseCase, setQueueUseCase)
    }

    @Test
    fun imageAndTextQueueTest() {

        rule.setContent {
            OnBoardScreen(
                navController = rememberNavController(),
                viewModel = onBoardViewModel
            )
        }

        rule.onAllNodesWithTag("btn").onFirst().performClick()
        rule.onAllNodesWithTag("btn").onFirst().performClick()
        rule.onAllNodesWithTag("img").onFirst().performClick()
    }

    @Test
    fun rightQueueTest() {

        rule.setContent {
            OnBoardScreen(
                navController = rememberNavController(),
                viewModel = onBoardViewModel
            )
        }

        rule.onAllNodesWithTag("btn").onFirst().performClick()
        rule.onAllNodesWithTag("btn").onFirst().performClick()
        rule.onAllNodesWithTag("img").onFirst().performClick()
    }

    @Test
    fun queueButtonTextTest() {

        rule.setContent {
            OnBoardScreen(
                navController = rememberNavController(),
                viewModel = onBoardViewModel
            )
        }

        rule.onAllNodesWithTag("btn").onFirst().performClick()
        rule.onAllNodesWithTag("btn").onFirst().performClick()
        rule.onAllNodesWithTag("img").onFirst().performClick()
    }

    @Test
    fun queueButtonSignUpTextTest() {

        rule.setContent {
            val queueList = listOf(
                OnBoardItem(
                    R.drawable.onboard_image_1,
                    R.string.confidence_in_your_words,
                    R.string.with_conversation_based_learning_you_ll_be_talking_from_lesson_one,
                    R.string.Next
                ),
                OnBoardItem(
                    R.drawable.onboard_image_2,
                    R.string.take_your_time_to_learn,
                    R.string.develop_a_habit_of_learning_and_make_it_a_part_of_your_daily_routine,
                    R.string.More
                ),
                OnBoardItem(
                    R.drawable.onboard_image_3,
                    R.string.the_lessons_you_need_to_learn,
                    R.string.using_a_variety_of_learning_styles_to_learn_and_retain,
                    R.string.sign_up
                ),
            )
            OnBoardScreen(
                navController = rememberNavController(),
                viewModel = onBoardViewModel,
                list = queueList
            )
        }

        rule.onAllNodesWithTag("btn").onFirst().performClick()
        rule.onAllNodesWithTag("btn").onFirst().performClick()
        rule.onAllNodesWithTag("img").onFirst().performClick()
    }
    @Test
    fun queueButtonSignInClickTest() {

        rule.setContent {
            val queueList = listOf(
                OnBoardItem(
                    R.drawable.onboard_image_1,
                    R.string.confidence_in_your_words,
                    R.string.with_conversation_based_learning_you_ll_be_talking_from_lesson_one,
                    R.string.Next
                ),
                OnBoardItem(
                    R.drawable.onboard_image_2,
                    R.string.take_your_time_to_learn,
                    R.string.develop_a_habit_of_learning_and_make_it_a_part_of_your_daily_routine,
                    R.string.More
                ),
                OnBoardItem(
                    R.drawable.onboard_image_3,
                    R.string.the_lessons_you_need_to_learn,
                    R.string.using_a_variety_of_learning_styles_to_learn_and_retain,
                    R.string.sign_in
                ),
            )
            val navController = rememberNavController()
            NavHost(navController, Route.OnBoardScreen.route){
                composable(Route.OnBoardScreen.route){
                    OnBoardScreen(
                        navController = navController,
                        viewModel = onBoardViewModel,
                        list = queueList
                    )
                }
                composable(Route.LoginScreen.route){
                    LoginScreen(navController)
                }
            }
        }

        rule.onAllNodesWithTag("btn").onFirst().performClick()
        rule.onAllNodesWithTag("btn").onFirst().performClick()
        rule.onAllNodesWithTag("btn").onFirst().performClick()
    }
}