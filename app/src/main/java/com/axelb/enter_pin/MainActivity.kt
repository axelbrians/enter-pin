package com.axelb.enter_pin

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.axelb.enter_pin.presentation.EnterPinScreen
import com.axelb.enter_pin.ui.theme.EnterpinTheme

class MainActivity : ComponentActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		window.decorView.fitsSystemWindows = false
		window.decorView.systemUiVisibility = (
			View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
			View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)

		setContent {
			EnterpinTheme {
				EnterPinScreen()
			}
		}
	}
}