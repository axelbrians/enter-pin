package com.axelb.enter_pin.presentation

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.axelb.enter_pin.ui.theme.MyColor
import com.axelb.enter_pin.R
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.onEach

@Composable
fun PinNumPadComposable(
	modifier: Modifier = Modifier,
) {
	Column(
		modifier = modifier,
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center
	) {
		Row(
			modifier = Modifier.fillMaxWidth(),
			horizontalArrangement = Arrangement.SpaceEvenly
		) {
			PinButtonComposable(1)
			PinButtonComposable(2)
			PinButtonComposable(3)
		}
		Row(
			modifier = Modifier.fillMaxWidth(),
			horizontalArrangement = Arrangement.SpaceEvenly
		) {
			PinButtonComposable(4)
			PinButtonComposable(5)
			PinButtonComposable(6)
		}
		Row(
			modifier = Modifier.fillMaxWidth(),
			horizontalArrangement = Arrangement.SpaceEvenly
		) {
			PinButtonComposable(7)
			PinButtonComposable(8)
			PinButtonComposable(9)
		}
		Row(
			modifier = Modifier.fillMaxWidth(),
			horizontalArrangement = Arrangement.SpaceEvenly
		) {
			Box(modifier = Modifier.size(80.dp))
			PinButtonComposable(0)
			Box(modifier = Modifier
				.padding(vertical = 16.dp)
				.size(80.dp)) {
				Image(
					painter = painterResource(id = R.drawable.ic_backspace_regular),
					contentDescription = "Backspace",
					modifier = Modifier
						.size(42.dp)
						.align(Alignment.Center)
				)
			}
		}

		Box(
			modifier = Modifier
				.padding(top = 12.dp)
				.border(
					width = 1.dp,
					color = MyColor.TextCaption,
					shape = RoundedCornerShape(50)
				)
				.padding(horizontal = 12.dp, vertical = 4.dp)
		) {
			Text(
				text = "USE PASSWORD",
				color = MyColor.TextCaption,
				fontWeight = FontWeight.W600,
				fontSize = 16.sp
			)
		}

	}
}

@Composable
private fun PinButtonComposable(
		padNumber: Int,
		onClick: () -> Int = { 0 }
) {
	val shape = RoundedCornerShape(50)
	val padSize = 80.dp
	val isPressedChannel = Channel<Boolean>(Channel.UNLIMITED)
	val isPressed = remember { mutableStateOf(false) }

	LaunchedEffect(Unit) {
		isPressedChannel.consumeAsFlow().collect { status ->
			isPressed.value = status
		}
	}

	val circlePadColor = animateColorAsState(
		targetValue = if (isPressed.value) Color.Black else MyColor.YellowSecondary,
		animationSpec = tween(150, 0, FastOutSlowInEasing)
	)
	val numberPadColor = animateColorAsState(
		targetValue = if (isPressed.value) Color.White else Color.Black,
		animationSpec = tween(100, 0, FastOutSlowInEasing)
	)

	Box(
		modifier = Modifier
			.padding(vertical = 16.dp)
			.size(padSize)
			.pointerInput(Unit) {
				detectTapGestures(
					onPress = {
						isPressedChannel.trySend(true)
						if (tryAwaitRelease()) {
							onClick()
						}
						isPressedChannel.trySend(false)

					}
				)
			}
			.drawBehind {
				val color = Color.Black
				val shadowRadius = 4.dp
				val shadowColor = color
					.copy(alpha = 0.2f)
					.toArgb()
				val transparent = color
					.copy(alpha = 0f)
					.toArgb()
				val paint = Paint()
				val frameworkPaint = paint.asFrameworkPaint()
				frameworkPaint.color = transparent

				frameworkPaint.setShadowLayer(
					shadowRadius.toPx(),
					0.dp.toPx(),
					2.dp.toPx(),
					shadowColor
				)
				this.drawIntoCanvas {
					it.drawRoundRect(
						0f,
						0f,
						this.size.width,
						this.size.height,
						padSize.toPx(),
						padSize.toPx(),
						paint
					)
				}
			}
			.clip(shape)
			.background(circlePadColor.value)
	) {
		Text(
			text = padNumber.toString(),
			fontSize = 36.sp,
			color = numberPadColor.value,
			fontWeight = FontWeight.W400,
			modifier = Modifier.align(Alignment.Center)
		)
	}
}

enum class PinPressStatus {
	Press,
	Release
}