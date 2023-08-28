package com.san.englishbender.android.ui.common

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.san.englishbender.android.navigation.EBNavigationActions
import com.san.englishbender.core.navigation.Destinations
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

data class DrawerNavOptions(
    val name: String = "",
    val route: String = "",
    val icon: ImageVector
)

private val drawerNavOptions = listOf(
    DrawerNavOptions(
        name = "Stats",
        route = Destinations.STATS_ROUTE,
        icon = Icons.Default.Analytics
    ),
    DrawerNavOptions(
        name = "Records",
        route = Destinations.RECORD_ROUTE,
        icon = Icons.Default.ViewList
    ),
)

@Composable
fun AppDrawer(
    drawerState: DrawerState,
//    navController: NavController,
    currentRoute: String,
    navActions: EBNavigationActions,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    content: @Composable () -> Unit
) {
    val navOption = drawerNavOptions.firstOrNull { it.route == currentRoute }
    val selectedItem = remember { mutableStateOf(navOption ?: drawerNavOptions[0]) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .width(250.dp)
                    .fillMaxHeight()
            ) {
                Spacer(Modifier.height(60.dp))
                drawerNavOptions.forEach { item ->
                    NavigationDrawerItem(
                        icon = { Icon(item.icon, contentDescription = null) },
                        label = { Text(item.name) },
                        selected = item == selectedItem.value,
                        onClick = {
                            selectedItem.value = item
                            coroutineScope.launch { drawerState.close() }

                            when (item.route) {
                                Destinations.STATS_ROUTE -> navActions.navigateToStats()
                                Destinations.RECORD_ROUTE -> navActions.navigateToRecords()
                            }
//                            navController.navigate(item.route)
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        },
        content = {
            content()
        }
    )
}