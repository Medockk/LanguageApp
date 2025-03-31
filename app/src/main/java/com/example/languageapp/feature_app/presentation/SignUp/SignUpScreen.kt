@file:Suppress("UNCHECKED_CAST")

package com.example.languageapp.feature_app.presentation.SignUp

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.languageapp.R
import com.example.languageapp.feature_app.presentation.Route
import com.example.languageapp.feature_app.presentation.common.CustomAlertDialog
import com.example.languageapp.feature_app.presentation.common.CustomButton
import com.example.languageapp.feature_app.presentation.common.CustomIndicator
import com.example.languageapp.feature_app.presentation.common.CustomScaffold
import com.example.languageapp.feature_app.presentation.common.CustomScaffoldTextAlign
import com.example.languageapp.feature_app.presentation.common.CustomTextField
import com.example.languageapp.feature_app.presentation.ui.theme._5B7BFEFF
import com.example.languageapp.feature_app.presentation.ui.theme._656872
import com.example.languageapp.feature_app.presentation.ui.theme.fontFredokaMedium
import com.example.languageapp.feature_app.presentation.ui.theme.fontFredokaRegular

@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: SignUpViewModel = hiltViewModel()
) {

    val state = viewModel.state.value
    val activity = LocalActivity.current
    val signUpFirstPageList = listOf(
        listOf(
            stringResource(R.string.first_name),
            state.firstName,
            { it: String -> viewModel.onEvent(SignUpEvent.EnterFirstName(it)) } as (String) -> Unit,
            stringResource(R.string.your_first_name)
        ),
        listOf(
            stringResource(R.string.last_name),
            state.lastName,
            { it: String -> viewModel.onEvent(SignUpEvent.EnterLastName(it)) } as (String) -> Unit,
            stringResource(R.string.your_last_name)
        ),
        listOf(
            stringResource(R.string.email_address),
            state.email,
            { it: String -> viewModel.onEvent(SignUpEvent.EnterEmail(it)) } as (String) -> Unit,
            stringResource(R.string.email)
        ),
    )

    val signUpSecondPageList = listOf(
        listOf(
            stringResource(R.string.password),
            state.password,
            { it: String -> viewModel.onEvent(SignUpEvent.EnterPassword(it)) } as (String) -> Unit,
            "*******",
            state.isPassword,
            { viewModel.onEvent(SignUpEvent.ChangePasswordState) } as () -> Unit,
        ),
        listOf(
            stringResource(R.string.confirm_password),
            state.confirmPassword,
            { it: String -> viewModel.onEvent(SignUpEvent.EnterConfirmPassword(it)) } as (String) -> Unit,
            "*******",
            state.isConfirmPassword,
            { viewModel.onEvent(SignUpEvent.ChangeConfirmPasswordState) } as () -> Unit,
        ),
    )

    BackHandler {
        viewModel.onEvent(SignUpEvent.BackClick)
    }

    if (state.exception.isNotEmpty()) {
        CustomAlertDialog(state.exception) {
            viewModel.onEvent(SignUpEvent.ResetException)
        }
    }

    LaunchedEffect(!state.isComplete) {
        if (state.isComplete) {
            navController.navigate(Route.LanguageSelectScreen.route) {
                popUpTo(Route.SignUpScreen.route) {
                    inclusive = true
                }
            }
        }
    }

    CustomScaffold(
        text = stringResource(R.string.sign_up),
        textAlign = CustomScaffoldTextAlign.CENTER,
        showBackIcon = true,
        textSize = 17.sp,
        backClick = {
            if (!state.isFirstRegisterPage) {
                viewModel.onEvent(SignUpEvent.BackClick)
            }
        }
    ) {
        Spacer(Modifier.height(40.dp))
        Text(
            text = stringResource(R.string.create_an_account),
            style = MaterialTheme.typography.titleMedium.copy(
                MaterialTheme.colorScheme.onPrimary
            )
        )
        Spacer(Modifier.height(30.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp)
        ) {
            if (state.isFirstRegisterPage) {
                repeat(signUpFirstPageList.size) { index ->
                    Text(
                        text = signUpFirstPageList[index][0] as String,
                        style = MaterialTheme.typography.displaySmall.copy(
                            MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                    Spacer(Modifier.height(8.dp))
                    CustomTextField(
                        value = signUpFirstPageList[index][1] as String,
                        onValueChange = signUpFirstPageList[index][2] as (String) -> Unit,
                        hint = signUpFirstPageList[index][3] as String,
                        modifier = Modifier
                            .fillMaxWidth(),
                    )
                    Spacer(Modifier.height(if (index != signUpFirstPageList.lastIndex) 25.dp else 35.dp))
                }
                CustomButton(
                    text = stringResource(R.string.continue_btn),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                ) { viewModel.onEvent(SignUpEvent.ContinueClick) }
            } else {
                repeat(signUpSecondPageList.size) { index ->
                    Text(
                        text = signUpSecondPageList[index][0] as String,
                        style = MaterialTheme.typography.displaySmall.copy(
                            MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                    Spacer(Modifier.height(8.dp))
                    CustomTextField(
                        value = signUpSecondPageList[index][1] as String,
                        onValueChange = signUpSecondPageList[index][2] as (String) -> Unit,
                        hint = signUpSecondPageList[index][3] as String,
                        modifier = Modifier
                            .fillMaxWidth(),
                        isPassword = true,
                        isPasswordState = signUpSecondPageList[index][4] as Boolean,
                        changeIsPasswordState = signUpSecondPageList[index][5] as () -> Unit
                    )
                    Spacer(Modifier.height(25.dp))
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        contentAlignment = Alignment.CenterStart
                    ) {
                        CustomCheckBox(
                            value = state.isChecked,
                            onValueChange = {
                                viewModel.onEvent(SignUpEvent.ChangeCheckedState(it))
                            }
                        )
                        Text(
                            modifier = Modifier
                                .padding(start = 40.dp)
                                .clickable {
                                    viewModel.onEvent(SignUpEvent.DownloadPolitic(activity))
                                },
                            text = buildAnnotatedString {
                                withStyle(
                                    SpanStyle(
                                        color = _656872,
                                        fontFamily = fontFredokaRegular,
                                        fontSize = 17.sp,
                                        fontWeight = FontWeight(400)
                                    )
                                ) {
                                    append(stringResource(R.string.i))
                                }
                                withStyle(
                                    SpanStyle(
                                        color = _5B7BFEFF,
                                        fontFamily = fontFredokaRegular,
                                        fontWeight = FontWeight(400),
                                        fontSize = 17.sp
                                    )
                                ) {
                                    append(stringResource(R.string.have_made_myself_acquainted_with_the_rules))
                                }
                                withStyle(
                                    SpanStyle(
                                        color = _656872,
                                        fontFamily = fontFredokaRegular,
                                        fontSize = 17.sp,
                                        fontWeight = FontWeight(400)
                                    )
                                ) {
                                    append(stringResource(R.string.and_accept_all_its_provisions))
                                }
                            },
                            textAlign = TextAlign.Start
                        )
                    }
                }
                Spacer(Modifier.height(70.dp))

                CustomButton(
                    text = stringResource(R.string.sign_up),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                ) { viewModel.onEvent(SignUpEvent.SignUpClick) }
            }
        }
        Spacer(Modifier.height(25.dp))

        TextButton(
            onClick = {
                navController.navigate(Route.LoginScreen.route) {
                    popUpTo(Route.SignUpScreen.route) {
                        inclusive = true
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(Color.Transparent)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.already_you_member),
                    style = MaterialTheme.typography.displaySmall.copy(
                        MaterialTheme.colorScheme.surface
                    )
                )
                Text(
                    text = stringResource(R.string.login),
                    style = MaterialTheme.typography.displayMedium.copy(
                        Color(0xFF5B7BFE),
                        fontFamily = fontFredokaMedium
                    )
                )
            }
        }
    }

    CustomIndicator(state.showIndicator)
}