package luke.koz.hyrulecompendium.navigation
/*
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import luke.koz.hyrulecompendium.model.CompendiumItem

@Serializable
data class User(
    val id: Int,
    val name: String
)
@Composable
fun UserDetailsScreen(
    user: User
){
    Text(text = "USER")
}
// Define a home destination that doesn't take any arguments
@Serializable
object Home

// Define a profile destination that takes an ID
@Serializable
data class Profile(val id: String)

@Composable
fun HomeScreen2(modifier: Modifier = Modifier) {
    Button(onClick = { /*TODO*/ }) {
        Text(text = "dummytHomeScreen2")
    }
}

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    Text(text = "ProfileScreen")
}
@Preview
@Composable
fun AppNavigationHost(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController()
) {
    Column (modifier = modifier) {
        NavHost(
            navController = navHostController,
            startDestination = Home
        ) {
//            composable<Home> {
//                HomeScreen2(onNavigateToProfile = { id ->
//                    navHostController.navigate(Profile(id))
//                })
//            }
//            composable<Profile> { backStackEntry ->
//                val profile: Profile = backStackEntry.toRoute()
//                ProfileScreen(profile)
//            }
//            composable<User> { backStackEntry ->
//                val user: User = backStackEntry.toRoute()
//
//                UserDetailsScreen(user) // Here UserDetailsScreen is a composable.
//            }
//            composable<ScreenA> {
//                Button(onClick = {
//                    navHostController.navigate(ScreenB(
//                        name = "asd",
//                        compendiumSelectedItem = dummy2CompendiumItem2
//                    ))
//                }) {
//                    Text(text = "DummyButton")
//                }
//                CompendiumAppScreen()//todo passing complex object in navHost is not allowed
//            }
//            composable<ScreenB> {
//                val args = it.toRoute<ScreenB>()
//                ScreenB( name  = null,
//                    compendiumSelectedItem = args.compendiumSelectedItem)
//                Column(
//                ) {
//                    Text(text = "${args.compendiumSelectedItem.name} is name")
//                }
//            }
        }
    }
}

@kotlinx.serialization.Serializable
object ScreenA

@kotlinx.serialization.Serializable
data class ScreenB(
    val name: String?,
    @Contextual val compendiumSelectedItem: dummy2CompendiumItem
)



data class dummy2CompendiumItem(
    val name: String,
)

val dummy2CompendiumItem2 : dummy2CompendiumItem = dummy2CompendiumItem (name = "name")

val dummyCompendiumItem: CompendiumItem = CompendiumItem(
    category = "creatures",
    common_locations = listOf(
        "Hebra Mountains",
        "Tabantha Frontier"
    ),
    description = "This particular breed of grassland fox makes its home in cold climates such as the Tabantha region. Its fur turned white as a means of adapting to snowy weather, serving as natural camouflage. Because of this, spotting one in the snow takes a keen eye.",
    dlc = false,
    drops = listOf(
        "raw prime meat",
        "raw gourmet meat"
    ),
    id = 20,
    image = "https://botw-compendium.herokuapp.com/api/v3/compendium/entry/snowcoat_fox/image",
    name = "snowcoat fox",
    cooking_effect = null,
    edible = false
)*/