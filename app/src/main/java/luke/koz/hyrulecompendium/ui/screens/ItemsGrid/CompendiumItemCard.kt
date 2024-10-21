package luke.koz.hyrulecompendium.ui.screens.ItemsGrid

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import luke.koz.hyrulecompendium.R
import luke.koz.hyrulecompendium.model.CompendiumItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompendiumItemCard(compendiumItem: CompendiumItem, modifier: Modifier = Modifier, onClickItemSelect : () -> Unit) {
    Card(
        modifier = modifier.padding(4.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        onClick = {onClickItemSelect()}
    ) {
        Row {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current).data(compendiumItem.image)
                    .crossfade(true).build(),
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),//todo the size of this should be defined and static
                contentDescription = stringResource(R.string.compendium_image),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.padding(4.dp)
                    .size(124.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Column (modifier = Modifier.padding(all = 16.dp)) {
                Text(text = compendiumItem.name.uppercase(), style = MaterialTheme.typography.bodyLarge)
                Text(text = compendiumItem.id.toString(), style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}