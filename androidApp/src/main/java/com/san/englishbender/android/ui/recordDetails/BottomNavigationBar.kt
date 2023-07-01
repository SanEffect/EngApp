package com.san.englishbender.android.ui.recordDetails

import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Spellcheck
import androidx.compose.material.icons.outlined.Translate
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(var title: String, var icon: ImageVector) {
    object Analyze : BottomNavItem("Analyze", Icons.Outlined.Spellcheck)
    object Translate : BottomNavItem("Translate", Icons.Outlined.Translate)
    object Settings : BottomNavItem("Settings", Icons.Outlined.Settings)
}

@Composable
fun NavigationBar(
    containerColor: Color = Color.White,
    navItemClicked: (navItem: BottomNavItem) -> Unit
) {
    val navItems = listOf(
        BottomNavItem.Analyze,
        BottomNavItem.Translate,
        BottomNavItem.Settings
    )
//    val selectedItem by remember { mutableStateOf(0) }

    NavigationBar(
        containerColor = containerColor
    ) {
        navItems.forEachIndexed { index, navItem ->
            NavigationBarItem(
                icon = { Icon(navItem.icon, contentDescription = null) },
                label = { Text(navItem.title) },
                selected = false,
                onClick = { navItemClicked(navItem) }
            )
        }
    }
}
