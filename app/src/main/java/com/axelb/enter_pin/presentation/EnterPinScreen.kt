package com.axelb.enter_pin.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.axelb.enter_pin.ui.theme.MyColor
import kotlin.math.*

@Composable
fun EnterPinScreen(modifier: Modifier = Modifier) {
	Column(
		modifier = modifier
			.fillMaxSize()
			.gradientBackground(
				color1 = 0f to MyColor.OrangeBright,
				color2 = 0.3f to Color(0xFFFDBB41),
				color3 = 0.7f to Color(0xFFF8A931),
				color4 = 1f to MyColor.OrangeDark,
				angle = -45f
			)
			.padding(top = 25.dp)
	) {
		Text(
			text = "Version 7.0.0 (751)",
			textAlign = TextAlign.End,
			fontSize = 14.sp,
			color = MyColor.TextCaption,
			modifier = Modifier
				.fillMaxWidth()
				.padding(start = 12.dp, end = 12.dp),
		)

		PinDotIndicatorComposable(
			modifier = Modifier.weight(1f).padding(top = 18.dp),
			pinDigitCount = 2
		)

		PinNumPadComposable(
			modifier = Modifier.weight(5f, true).padding(vertical = 18.dp)
		)
	}
}

private fun Modifier.gradientBackground(
	color1: Pair<Float, Color>,
	color2: Pair<Float, Color>,
	color3: Pair<Float, Color>,
	color4: Pair<Float, Color>,
	angle: Float
) = this.then(
	Modifier.drawBehind {
		val angleRad = angle / 180f * PI
		val x = cos(angleRad).toFloat() //Fractional x
		val y = sin(angleRad).toFloat() //Fractional y

		val radius = sqrt(size.width.pow(2) + size.height.pow(2)) / 2f
		val offset = center + Offset(x * radius, y * radius)

		val exactOffset = Offset(
			x = min(offset.x.coerceAtLeast(0f), size.width),
			y = size.height - min(offset.y.coerceAtLeast(0f), size.height)
		)
		Brush.linearGradient()
		drawRect(
			brush = Brush.linearGradient(
				color1,
				color2,
				color3,
				color4,
				start = Offset(size.width, size.height) - exactOffset,
				end = exactOffset
			),
			size = size
		)
	}
)