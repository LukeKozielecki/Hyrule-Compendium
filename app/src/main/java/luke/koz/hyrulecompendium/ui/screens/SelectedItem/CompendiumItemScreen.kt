package luke.koz.hyrulecompendium.ui.screens.SelectedItem

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import luke.koz.hyrulecompendium.R
import luke.koz.hyrulecompendium.model.CompendiumDataList
import luke.koz.hyrulecompendium.model.CompendiumItem
import luke.koz.hyrulecompendium.ui.theme.HyruleCompendiumTheme

/**
 * How does it work: this composable displays a single compendium item.
 * Said item is displayed on top of the list, not utilizing navHost
 *
 * Why was it implemented like this: Nav host does not natively facilitate custom complex objects inside
 * of itself. This implementation is admittedly not good, but it works and stops n'th
 * hour of figuring out how to get serializable data class objects to be passed in NavHost.
 *
 * todo: rework sometimeSoon™
 */
@Composable
fun CompendiumItemScreen(modifier: Modifier = Modifier, compendiumItem: CompendiumItem? = null, contentPadding: PaddingValues = PaddingValues(0.dp)) {
    if (compendiumItem != null) {
        Box(modifier = modifier.padding(paddingValues = contentPadding)){
            Image(
                painter = painterResource(id = R.drawable.zelda_dark_backgorund),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = modifier.fillMaxSize()
            )
            CompendiumSelectedItemCard(compendiumItem = compendiumItem)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CompendiumItemScreenPreview() {
    HyruleCompendiumTheme {
        val mockData = CompendiumDataList(
            listOf(
                CompendiumItem(
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
                )
            )
        )
        CompendiumItemScreen(
            compendiumItem = mockData.data[0]
        )
    }
}