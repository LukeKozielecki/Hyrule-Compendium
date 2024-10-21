package luke.koz.hyrulecompendium.ui.screens.SelectedItem

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import luke.koz.hyrulecompendium.R
import luke.koz.hyrulecompendium.model.CompendiumItem
import luke.koz.hyrulecompendium.ui.theme.Dimens


@Composable
fun CompendiumSelectedItemCard(modifier: Modifier = Modifier, compendiumItem: CompendiumItem) {
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