@file:Suppress("UNCHECKED_CAST")

package com.example.languageapp.feature_app.presentation.MainScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.languageapp.feature_app.presentation.MainScreen.components.CustomExerciseCard
import com.example.languageapp.feature_app.presentation.MainScreen.components.CustomTopUserCard
import com.example.languageapp.feature_app.presentation.Route
import com.example.languageapp.feature_app.presentation.common.CustomAlertDialog
import com.example.languageapp.feature_app.presentation.common.CustomScaffoldWithLargeTopAppBar
import com.example.languageapp.feature_app.presentation.ui.theme._5B7BFEFF
import com.example.languageapp.feature_app.presentation.ui.theme._B6B6B6
import com.example.languageapp.feature_app.presentation.ui.theme._D9D9D9
import com.example.languageapp.feature_app.presentation.ui.theme.fontFredokaMedium

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainScreenViewModel = hiltViewModel()
) {

    val state = viewModel.state.value
    val exercises = listOf(
        listOf(
            ExerciseItem(
                _5B7BFEFF,
                "https://uftclonibwagnofwkbtp.supabase.co/storage/v1/object/public/avatars//guess_the_animal_icon.png",
                "Guess the animal"
            ),
            {}
        ),
        listOf(
            ExerciseItem(
                _5B7BFEFF,
                "https://uftclonibwagnofwkbtp.supabase.co/storage/v1/object/public/avatars//work_practice_icon.png",
                "Word practice"
            ),
            {
                navController.navigate(Route.WordPractice.route)
            }
        ),
        listOf(
            ExerciseItem(
                _5B7BFEFF,
                "https://uftclonibwagnofwkbtp.supabase.co/storage/v1/object/public/avatars//audition_icon.png",
                "Audition"
            ),
            {}
        ),
        listOf(
            ExerciseItem(
                _5B7BFEFF,
                "https://uftclonibwagnofwkbtp.supabase.co/storage/v1/object/public/avatars//game_icon.png",
                "Game"
            ),
            {}
        )
    )

    if (state.exception.isNotEmpty()) {
        CustomAlertDialog(state.exception) {
            viewModel.onEvent(MainScreenEvent.ResetException)
        }
    }

    CustomScaffoldWithLargeTopAppBar(
        topAppBarContent = {
            Box(Modifier
                .padding(start = 25.dp)
                .size(55.dp)
                .background(_D9D9D9, CircleShape)) {
                AsyncImage(
                    model = state.userImage,
                    contentDescription = null,
                    modifier = Modifier
                        .matchParentSize(),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(Modifier.height(5.dp))
            Text(
                text = "Hello, ${state.userName}",
                fontFamily = fontFredokaMedium,
                fontWeight = FontWeight(500),
                fontSize = 22.sp,
                color = Color.White,
                modifier = Modifier
                    .padding(start = 25.dp)
            )
            Spacer(Modifier.height(5.dp))
            Text(
                text = "Are you ready for learning today?",
                fontFamily = fontFredokaMedium,
                fontWeight = FontWeight(500),
                fontSize = 17.sp,
                color = _B6B6B6,
                modifier = Modifier
                    .padding(start = 25.dp)
            )
            Spacer(Modifier.height(10.dp))
        }
    ) {
        Spacer(Modifier.height(10.dp))
        Text(
            text = "Top users",
            fontFamily = fontFredokaMedium,
            fontWeight = FontWeight(500),
            fontSize = 20.sp,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 25.dp),
            color = MaterialTheme.colorScheme.onPrimary,
        )
        Spacer(Modifier.height(5.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 25.dp)
        ) {
            items(state.topUserList) {
                CustomTopUserCard(
                    userIcon = it.userIcon,
                    userName = it.userName,
                    userPoints = it.userPoint,
                    modifier = Modifier
                        .fillParentMaxWidth()
                )
            }
            item {
                Text(
                    text = "Available excersises",
                    fontFamily = fontFredokaMedium,
                    fontWeight = FontWeight(500),
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(Modifier.height(10.dp))
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillParentMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    this@LazyVerticalGrid.items(exercises) {
                        Log.e("icon", (it[0]as ExerciseItem).icon)
                        CustomExerciseCard(
                            background = (it[0] as ExerciseItem).background,
                            icon = (it[0] as ExerciseItem).icon,
                            title = (it[0] as ExerciseItem).title,
                            onClick = it[1] as () -> Unit,
                            modifier = Modifier
                                .fillParentMaxWidth(0.45f)
                        )
                    }
                }
            }
        }
    }
}