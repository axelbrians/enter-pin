package com.axelb.enter_pin.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.axelb.enter_pin.ui.theme.MyColor
import kotlin.math.*

@Composable
fun EnterPinScreen(modifier: Modifier = Modifier) {
	Box(
		modifier = Modifier
			.fillMaxSize()
			.gradientBackground(
				colors = listOf(MyColor.OrangeBright, MyColor.OrangeDark),
				angle = -45f
			)
	) {

	}
}

private fun Modifier.gradientBackground(
	colors: List<Color>,
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

		drawRect(
			brush = Brush.linearGradient(
				colors = colors,
				start = Offset(size.width, size.height) - exactOffset,
				end = exactOffset
			),
			size = size
		)
	}
)