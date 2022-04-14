package com.axelb.enter_pin.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.axelb.enter_pin.ui.theme.MyColor

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PinDotIndicatorComposable(
	modifier: Modifier = Modifier,
	pinDigitCount: Int,
	isIncorrectPin: Boolean
) {
	Column(
		modifier = modifier,
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		
		Text(
			text = "Enter your PIN",
			fontWeight = FontWeight.W700,
			fontSize = 20.sp,
			textAlign = TextAlign.Center,
			modifier = Modifier
				.fillMaxWidth()
				.padding(bottom = 18.dp, top = 24.dp)
		)
		
		Row(
			horizontalArrangement = Arrangement.SpaceEvenly
		) {
			repeat(6) { index ->
				val color = if (index < pinDigitCount) {
					MyColor.PurplePrimary
				} else {
					Color.White
				}

				Box(
					modifier = Modifier
						.padding(horizontal = 12.dp)
						.size(12.dp)
						.clip(RoundedCornerShape(50))
						.background(color)
				)
			}
		}
		if (isIncorrectPin) {
			Text(
				text = "Incorrect PIN. Let's try again.",
				color = Color.Red,
				fontWeight = FontWeight.W500,
				fontSize = 18.sp,
				textAlign = TextAlign.Center,
				modifier = Modifier
					.fillMaxWidth()
					.padding(top = 18.dp)
			)
		} else {
			Text(
				text = "",
				fontWeight = FontWeight.W500,
				fontSize = 18.sp,
				modifier = Modifier
					.fillMaxWidth()
					.padding(top = 18.dp)
			)
		}
	}
}