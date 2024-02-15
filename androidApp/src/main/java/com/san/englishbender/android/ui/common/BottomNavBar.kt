package com.san.englishbender.android.ui.common

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Archive
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Spellcheck
import androidx.compose.material.icons.outlined.Translate
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(var label: String, var icon: ImageVector)

sealed class DeckNavItem(label: String, icon: ImageVector) : BottomNavItem(label, icon) {
    data object SendToArchive : DeckNavItem("Archive", Icons.Outlined.Archive)
}

sealed class RecordDetailsNavItem(label: String, icon: ImageVector) : BottomNavItem(label, icon) {
    data object GrammarCheck : RecordDetailsNavItem("GrammarCheck", Icons.Outlined.Spellcheck)
    data object Translate : RecordDetailsNavItem("Translate", Icons.Outlined.Translate)
    data object Settings : RecordDetailsNavItem("Settings", Icons.Outlined.Settings)
}

@Composable
fun BottomNavBar(
    hasLabel: Boolean = false,
    containerColor: Color = Color.White,
    navItems: List<BottomNavItem>,
    navItemClicked: (navItem: BottomNavItem) -> Unit
) {
    NavigationBar(
        contentColor = Color.Black,
        containerColor = containerColor
    ) {
        navItems.forEach{ navItem ->
            NavigationBarItem(
                icon = { Icon(navItem.icon, contentDescription = null) },
                label = { if (hasLabel) Text(navItem.label) },
                selected = false,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    selectedTextColor = Color.Black,
                    unselectedIconColor = Color.Black,
                    unselectedTextColor = Color.Black,
                    disabledIconColor = Color.Black,
                    disabledTextColor = Color.Black,
                ),
                onClick = { navItemClicked(navItem) }
            )
        }
    }
}