package com.example.languageapp.feature_app.presentation.ProfileResizePhoto

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.languageapp.feature_app.presentation.common.CustomAlertDialog
import com.example.languageapp.feature_app.presentation.common.CustomButton
import com.example.languageapp.feature_app.presentation.common.CustomScaffold
import com.example.languageapp.feature_app.presentation.ui.theme.fontFredokaMedium
import com.example.languageapp.feature_app.presentation.ui.theme.resizePhotoBackground

@Composable
fun ProfileResizePhotoScreen(
    navController: NavController,
    viewModel: ProfileResizePhotoViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    if (state.exception.isNotEmpty()) {
        CustomAlertDialog(state.exception) {
            viewModel.onEvent(ProfileResizePhotoEvent.ResetException)
        }
    }

    CustomScaffold(
        text = "Your photo is gorgeous!",
        showBackIcon = false
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(resizePhotoBackground)
                .padding(top = 15.dp)
        ) {
            Text(
                text = "Just resize that photo\n" +
                        "for fit in square",
                fontFamily = fontFredokaMedium,
                fontWeight = FontWeight(500),
                fontSize = 22.sp,
                color = Color.White,
                modifier = Modifier
                    .padding(start = 25.dp),
                textAlign = TextAlign.Start
            )
            Spacer(Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.3f)
                    .border(1.dp, Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                if (state.photoBitmap != null) {
                    Image(
                        bitmap = state.photoBitmap.asImageBitmap(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(resizePhotoBackground)
                    )
                }

                Box(
                    Modifier
                        .fillMaxSize()
                        .alpha(0.7f)
                        .background(resizePhotoBackground)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.55f)
                        .fillMaxHeight()
                        .drawBehind {
                            drawLine(
                                Color.White,
                                Offset(0f, 0f),
                                Offset(12.dp.toPx(), 0f),
                                strokeWidth = 2.dp.toPx()
                            )
                            drawLine(
                                Color.White,
                                Offset(0f, 0f),
                                Offset(0f, 12.dp.toPx()),
                                strokeWidth = 2.dp.toPx()
                            )
                            drawLine(
                                Color.White,
                                Offset(size.width - 12.dp.toPx(), 0f),
                                Offset(size.width, 0f),
                                strokeWidth = 2.dp.toPx()
                            )
                            drawLine(
                                Color.White,
                                Offset(size.width, 0f),
                                Offset(size.width, 12.dp.toPx()),
                                strokeWidth = 2.dp.toPx()
                            )
                            drawLine(
                                Color.White,
                                Offset(0f, size.height),
                                Offset(12.dp.toPx(), size.height),
                                strokeWidth = 2.dp.toPx()
                            )
                            drawLine(
                                Color.White,
                                Offset(0f, size.height),
                                Offset(0f, size.height - 12.dp.toPx()),
                                strokeWidth = 2.dp.toPx()
                            )
                            drawLine(
                                Color.White,
                                Offset(size.width - 12.dp.toPx(), size.height),
                                Offset(size.width, size.height),
                                strokeWidth = 2.dp.toPx()
                            )
                            drawLine(
                                Color.White,
                                Offset(size.width, size.height),
                                Offset(size.width, size.height - 12.dp.toPx()),
                                strokeWidth = 2.dp.toPx()
                            )
                        }
                )
            }

            Spacer(Modifier.weight(1.5f))

            CustomButton(
                text = "Use that image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .padding(start = 25.dp, end = 25.dp),
            ) {
                viewModel.onEvent(ProfileResizePhotoEvent.UseThatImageClick)
            }
            Spacer(Modifier.height(30.dp))
        }
    }
}