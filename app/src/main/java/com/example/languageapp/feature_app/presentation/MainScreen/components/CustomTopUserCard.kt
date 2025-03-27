package com.example.languageapp.feature_app.presentation.MainScreen.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.languageapp.R
import com.example.languageapp.feature_app.presentation.ui.theme._E5E5E5
import com.example.languageapp.feature_app.presentation.ui.theme.fontFredokaBold

@Composable
fun CustomTopUserCard(
    userIcon: String,
    userName: String,
    userPoints: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(_E5E5E5)
    ) {
        Row(
            modifier = Modifier
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = userIcon,
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(35.dp, 25.dp)
            )
            Spacer(Modifier.width(25.dp))
            Text(
                text = userName,
                fontFamily = fontFredokaBold,
                fontWeight = FontWeight(500),
                fontSize = 17.sp,
                color = Color.Black,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .weight(1f),
                overflow = TextOverflow.Visible
            )
            Spacer(Modifier.width(25.dp))
            Text(
                text = userPoints + stringResource(R.string.points),
                fontFamily = fontFredokaBold,
                fontWeight = FontWeight(500),
                fontSize = 17.sp,
                color = Color.Black,
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Visible
            )
        }
    }
}