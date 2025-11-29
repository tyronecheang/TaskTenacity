package com.example.tasktenacity.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Today
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.tasktenacity.Destinations

@Composable
fun BottomNav(navController: NavHostController) {

    // Define the navigation items: route, label, icon
    val items = listOf(
        Triple(Destinations.TODAY, "Today", Icons.Filled.Today),
        Triple(Destinations.ALL_TASKS, "All", Icons.AutoMirrored.Filled.List),
        Triple(Destinations.ADD_TASK, "Add", Icons.Filled.Add),
        Triple(Destinations.TIPS, "Tips", Icons.Filled.Lightbulb)
    )

    // Observe the current back stack entry to determine the active route
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Main bottom navigation bar
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceVariant // Background color of the nav bar
    ) {
        // Create a NavigationBarItem for each item in the list
        items.forEach { (route, label, icon) ->
            NavigationBarItem(
                icon = { Icon(icon, contentDescription = label) }, // Icon displayed in the nav item
                label = { Text(label) }, // Text label displayed below the icon
                selected = currentRoute == route, // Highlight if this is the current route
                onClick = {
                    // Navigate to the selected route
                    navController.navigate(route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true } // Preserve state of start destination
                        launchSingleTop = true // Avoid multiple copies of the same destination
                        restoreState = true // Restore previously saved state when reselecting
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimary, // Icon color when selected
                    selectedTextColor = MaterialTheme.colorScheme.onSurface, // Label color when selected
                    indicatorColor = MaterialTheme.colorScheme.primary // Background indicator color for selected item
                )
            )
        }
    }
}
