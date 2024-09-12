package luke.koz.hyrulecompendium.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigationHost(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController()
) {
    Column (modifier = modifier) {
        NavHost(
            navController = navHostController,
            startDestination = NavRoutesSerializable.CompendiumHubLocation
        ) {
            composable<NavRoutesSerializable.CompendiumHubLocation> {
                val compendiumItemScreen =
                    {
                        navHostController.navigate(
                            NavRoutesSerializable.CompendiumItemScreen
                        )
                    }
//                HubScreen(
//                    modifier = modifier,
//                    destinationList = listOf(
//                    ),
//                    destinationLabelsList = listOf(
//                    )
//                )
            }
        }
    }
}