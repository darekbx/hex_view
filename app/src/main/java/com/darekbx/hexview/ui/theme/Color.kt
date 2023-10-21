package com.darekbx.hexview.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

data class Colors(
    var orange: Color = Color(0xFFFF8F00),
    val green: Color = Color(0xFF4CAF50),
    val blue: Color = Color(0xFF0A247D),
    val red: Color = Color(0xFFE75B52),
)

val LocalColors = compositionLocalOf { Colors() }