package com.san.englishbender.android.ui.recordDetails

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Spellcheck
import androidx.compose.material.icons.outlined.Translate
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

sealed class BottomNavItem(var title: String, var icon: ImageVector) {
    object GrammarCheck : BottomNavItem("GrammarCheck", Icons.Outlined.Spellcheck)
    object Translate : BottomNavItem("Translate", Icons.Outlined.Translate)
    object Settings : BottomNavItem("Settings", Icons.Outlined.Settings)
}

@Composable
fun NavigationBar(
    hasLabel: Boolean = false,
    containerColor: Color = Color.White,
    navItemClicked: (navItem: BottomNavItem) -> Unit
) {
    val navItems = listOf(
        BottomNavItem.GrammarCheck,
        BottomNavItem.Translate,
        BottomNavItem.Settings
    )

    NavigationBar(
        contentColor = Color.Black,
        containerColor = containerColor
    ) {
        navItems.forEachIndexed { index, navItem ->
            NavigationBarItem(
                icon = { Icon(navItem.icon, contentDescription = null) },
                label = { if (hasLabel) Text(navItem.title) },
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
