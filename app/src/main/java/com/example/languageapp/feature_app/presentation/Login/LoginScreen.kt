@file:Suppress("UNCHECKED_CAST")

package com.example.languageapp.feature_app.presentation.Login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.languageapp.R
import com.example.languageapp.feature_app.presentation.Route
import com.example.languageapp.feature_app.presentation.common.CustomAlertDialog
import com.example.languageapp.feature_app.presentation.common.CustomButton
import com.example.languageapp.feature_app.presentation.common.CustomScaffold
import com.example.languageapp.feature_app.presentation.common.CustomScaffoldTextAlign
import com.example.languageapp.feature_app.presentation.common.CustomTextField
import com.example.languageapp.feature_app.presentation.ui.theme.fontFredokaMedium
import com.example.languageapp.feature_app.presentation.ui.theme.fontFredokaRegular

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {

    val state = viewModel.state.value

    val loginList = listOf(
        listOf(
            "Email Address",
            state.email,
            { it: String -> viewModel.onEvent(LoginEvent.EnterEmail(it)) } as (String) -> Unit,
            false,
            state.isShowPassword,
            "Email",
        ),
        listOf(
            "Password",
            state.password,
            { it: String -> viewModel.onEvent(LoginEvent.EnterPassword(it)) } as (String) -> Unit,
            true,
            state.isShowPassword,
            "*******"
        ),
    )

    if (state.exception.isNotEmpty()) {
        CustomAlertDialog(state.exception) {
            viewModel.onEvent(LoginEvent.ResetException)
        }
    }

    LaunchedEffect(!state.isSuccessfulLogin) {
        if (state.isSuccessfulLogin) {

        }
    }
    CustomScaffold(
        text = "Login",
        textAlign = CustomScaffoldTextAlign.CENTER,
        showBackIcon = true,
        textSize = 17.sp,
    ) {
        Spacer(Modifier.height(20.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.login_image),
                contentDescription = null,
                modifier = Modifier
                    .width(100.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = "For free, join now\nand start learning",
                style = MaterialTheme.typography.titleMedium.copy(
                    MaterialTheme.colorScheme.onPrimary
                )
            )
        }
        Spacer(Modifier.height(30.dp))

        Column(
            modifier = Modifier
                .padding(horizontal = 25.dp),
            horizontalAlignment = Alignment.Start
        ) {
            repeat(loginList.size) { index ->
                Text(
                    text = loginList[index][0] as String,
                    style = MaterialTheme.typography.displaySmall.copy(
                        MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
                Spacer(Modifier.height(8.dp))
                CustomTextField(
                    value = loginList[index][1] as String,
                    onValueChange = loginList[index][2] as (String) -> Unit,
                    hint = loginList[index][5] as String,
                    modifier = Modifier
                        .fillMaxWidth(),
                    isPassword = loginList[index][3] as Boolean,
                    isPasswordState = loginList[index][4] as Boolean
                ) {
                    viewModel.onEvent(LoginEvent.ChangeIsShowPasswordState)
                }
                Spacer(Modifier.height(if (index == loginList.lastIndex) 12.dp else 24.dp))
            }

            Text(
                text = "Forgot Password",
                fontFamily = fontFredokaRegular,
                fontWeight = FontWeight(400),
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onError
            )
            Spacer(Modifier.height(30.dp))
            CustomButton(
                text = "Login",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            ) {
                viewModel.onEvent(LoginEvent.LoginClick)
            }
            Spacer(Modifier.height(25.dp))
            TextButton(
                onClick = {
                    navController.navigate(Route.SignUpScreen.route)
                },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color.Transparent)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Not you member? ",
                        style = MaterialTheme.typography.displaySmall.copy(
                            MaterialTheme.colorScheme.surface
                        )
                    )
                    Text(
                        text = "SignUp",
                        style = MaterialTheme.typography.displayMedium.copy(
                            Color(0xFF5B7BFE),
                            fontFamily = fontFredokaMedium
                        )
                    )
                }
            }
        }
    }
}