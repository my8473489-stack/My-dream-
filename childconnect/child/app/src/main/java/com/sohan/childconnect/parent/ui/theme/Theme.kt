package com.sohan.childconnect.parent.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val PurpleAccent = Color(0xFF8B5CF6)
val TealLive = Color(0xFF2DD4BF)
val DarkNavy = Color(0xFF0D1120)
val CardDark = Color(0xFF161B2E)

private val DarkColors = darkColorScheme(
        primary = PurpleAccent,
            secondary = TealLive,
                background = DarkNavy,
                    surface = CardDark
)

@Composable
fun ChildConnectTheme(content: @Composable () -> Unit) {
        MaterialTheme(
                    colorScheme = DarkColors,
                            content = content
        )
}
