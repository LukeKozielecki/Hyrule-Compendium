package luke.koz.hyrulecompendium.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import luke.koz.hyrulecompendium.R
import luke.koz.hyrulecompendium.model.CompendiumDataList
import luke.koz.hyrulecompendium.model.CompendiumItem
import luke.koz.hyrulecompendium.model.Properties
import luke.koz.hyrulecompendium.ui.theme.Dimens
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

@Composable
fun CompendiumSelectedItemCard(modifier: Modifier = Modifier,compendiumItem: CompendiumItem) {
    Column (modifier.padding(Dimens.paddingLarge)) {
        Row {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(compendiumItem.image)
                    .crossfade(true).build(),
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),//todo the size of this should be defined and static
                contentDescription = stringResource(R.string.compendium_image),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.padding(4.dp)
            )
            Column(
                modifier = Modifier.padding(
                    all = 16.dp
                )
            ) {
                Text(
                    text = compendiumItem.name.uppercase(),
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = compendiumItem.category.toString(),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        Text(
            text = compendiumItem.description,
            modifier = Modifier.padding(bottom = 16.dp),
            textAlign = TextAlign.Justify
        )
        CompendiumItemListedItemProperty(
            label = "Common Locations",
            compendiumItemProperty = compendiumItem.common_locations
        )
        CompendiumItemListedItemProperty(
            label = "Drops",
            compendiumItemProperty = compendiumItem.drops
        )
        CompendiumItemListedItemProperty(
            label = "Properties",
            itemProperty = compendiumItem.properties
        )
        if(compendiumItem.hearts_recovered != null) {
            Text(text = "Hearts recovered: ${compendiumItem.hearts_recovered}")
        }
        if(compendiumItem.cooking_effect != null) {
            Text(text = "Cooking effect:")
            Text(text = compendiumItem.cooking_effect)
        }
        if(compendiumItem.hearts_recovered != null) {
            Text(text = "Fuse attack power: ${compendiumItem.fuse_attack_power}")
        }
    }
}

@Composable
private fun CompendiumItemListedItemProperty(
    modifier: Modifier = Modifier,
    label: String,
    compendiumItemProperty: List<String>? = null,
    itemProperty : Properties? = null
) {
    if (!compendiumItemProperty.isNullOrEmpty()) {
        Text (text = "$label:")
        LazyColumn(Modifier.padding(vertical = 8.dp)){
            items(compendiumItemProperty.size) {
                    item ->
                Text(text = compendiumItemProperty[item])
            }
        }
    }
    if(!itemProperty?.type.isNullOrEmpty()) {
        Text(text = "Type: ${itemProperty?.type}")
    }
    if(!itemProperty?.effect.isNullOrEmpty()) {
        Text(text = "Effect: ${itemProperty?.effect}")
    }
    if(itemProperty?.attack != null) {
        Text(text = "Attack: ${itemProperty.effect}")
    }
    if(itemProperty?.defense != null) {
        Text(text = "Defense: ${itemProperty.defense}")
    }
}

@Preview(showBackground = true)
@Composable
fun CompendiumItemScreenPreview() {
    HyruleCompendiumTheme {
        val mockData = CompendiumDataList(listOf(
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

        ))
        val mockFilterPlaceholder : (List<CompendiumItem>, String) -> List<CompendiumItem> = {
                mockDataInner, filterTermInner ->
            mockDataInner.filter { item -> item.category == filterTermInner }
        }
//        val mockCompendiumViewModel: CompendiumViewModel = viewModel()
        CompendiumItemScreen(
            compendiumItem = mockData.data[0]
        )
    }
}