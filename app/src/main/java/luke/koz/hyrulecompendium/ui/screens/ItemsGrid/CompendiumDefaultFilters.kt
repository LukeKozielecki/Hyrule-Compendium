package luke.koz.hyrulecompendium.ui.screens.ItemsGrid

import android.annotation.SuppressLint
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import luke.koz.hyrulecompendium.ui.theme.HyruleCompendiumTheme
import luke.koz.hyrulecompendium.viewmodel.CompendiumViewModel

@Composable
fun CompendiumDefaultFilters(
    modifier: Modifier = Modifier,
    compendiumViewModel: CompendiumViewModel,
    presetFiltersList: List<String>,
) {
    val scrollState = rememberScrollState()
    Row(modifier = modifier
        .padding(16.dp)
        .horizontalScroll(scrollState)) {
        for ((index, buttonLabel) in presetFiltersList.withIndex()) {
            Button(onClick = { compendiumViewModel.updateFilterTerm(presetFiltersList[index]) }, Modifier.padding(horizontal = 4.dp)) {
                Text(text = buttonLabel.replaceFirstChar(Char::titlecase))
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun CompendiumDefaultFiltersPreview() {
    val mockCompendiumViewModel: CompendiumViewModel = viewModel(factory = CompendiumViewModel.Factory)
    HyruleCompendiumTheme {
        CompendiumDefaultFilters(
            compendiumViewModel = mockCompendiumViewModel,
            presetFiltersList = listOf("creatures", "treasure", "monsters")
        )
    }
}