@file:Suppress("UNCHECKED_CAST")

package com.example.languageapp.feature_app.presentation.Profile

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.languageapp.feature_app.presentation.Route
import com.example.languageapp.feature_app.presentation.common.CustomAlertDialog
import com.example.languageapp.feature_app.presentation.common.CustomButton
import com.example.languageapp.feature_app.presentation.common.CustomScaffoldWithLargeTopAppBar
import com.example.languageapp.feature_app.presentation.ui.theme._5BA890
import com.example.languageapp.feature_app.presentation.ui.theme._E5E5E5
import com.example.languageapp.feature_app.presentation.ui.theme.fontFredokaMedium

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel(),
    themeSwitch: () -> Unit,
) {

    val state = viewModel.state.value
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        if (it != null){
            val stream = context.contentResolver.openInputStream(it)
            val photo = stream?.readBytes()
            Route.ProfileResizePhotoScreen.photo = photo
            stream?.close()
            navController.navigate(Route.ProfileResizePhotoScreen.route)
        }
    }
    val buttonList = listOf(
        listOf(
            "Switch to Dark",
            {
                themeSwitch()
            }
        ),
        listOf(
            "Change mother language",
            {}
        ),
        listOf(
            "Change your image",
            {
                launcher.launch("image/*")
            }
        ),
        listOf(
            "Logout",
            {viewModel.onEvent(ProfileEvent.LogOut)}
        ),
    )

    if (state.exception.isNotEmpty()){
        CustomAlertDialog(state.exception) {
            viewModel.onEvent(ProfileEvent.ResetException)
        }
    }

    CustomScaffoldWithLargeTopAppBar(
        topAppBarContent = {
            Box(
                modifier = Modifier
                    .padding(start = 25.dp)
                    .clip(CircleShape)
                    .sizeIn(130.dp, 130.dp)
                    .background(_5BA890, CircleShape),
                contentAlignment = Alignment.Center
            ){
                AsyncImage(
                    model = state.userImage,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                )
            }
            Spacer(Modifier.height(5.dp))
            Text(
                text = "Your profile, ${state.userName}",
                fontFamily = fontFredokaMedium,
                fontWeight = FontWeight(500),
                fontSize = 22.sp,
                color = Color.White,
                modifier = Modifier
                    .padding(start = 25.dp)
            )
            Spacer(Modifier.height(20.dp))
        }
    ) {
        Spacer(Modifier.weight(1f))

        repeat(buttonList.size){
            CustomButton(
                text = buttonList[it][0] as String,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(55.dp)
                    .padding(horizontal = 25.dp),
                onClick = buttonList[it][1] as () -> Unit,
                background = if (it == buttonList.lastIndex) _E5E5E5 else MaterialTheme.colorScheme.secondary
            )
            Spacer(Modifier.height(10.dp))
        }
    }
}