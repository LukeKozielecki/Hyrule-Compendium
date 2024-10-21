package luke.koz.hyrulecompendium.ui.screens.SelectedItem

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import luke.koz.hyrulecompendium.model.Properties


@Composable
fun CompendiumItemListedItemProperty(
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