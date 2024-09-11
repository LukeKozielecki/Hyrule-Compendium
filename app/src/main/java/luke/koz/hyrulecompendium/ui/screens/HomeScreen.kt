package luke.koz.hyrulecompendium.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import luke.koz.hyrulecompendium.R
import luke.koz.hyrulecompendium.model.CompendiumItem
import luke.koz.hyrulecompendium.model.CompendiumDataList
import luke.koz.hyrulecompendium.ui.theme.HyruleCompendiumTheme
import luke.koz.hyrulecompendium.viewmodel.CompendiumUiState

@Composable
fun HomeScreen(
    compendiumUiState: CompendiumUiState,
    retryAction: () -> Unit,
    filterResults: (List<CompendiumItem>, String) -> List<CompendiumItem>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    when (compendiumUiState) {
        is CompendiumUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is CompendiumUiState.Success -> ItemsGridScreen(
            compendiumItem = compendiumUiState.compendiumDataList,
            filterResults = filterResults,
            contentPadding = contentPadding,
            modifier = modifier.fillMaxWidth()
        )
        is CompendiumUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxSize())
    }
}

/**
 * The home screen displaying the loading message.
 */
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

/**
 * The home screen displaying error message with re-attempt button.
 */
@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

/**
 * The home screen displaying item grid.
 */
@Composable
fun ItemsGridScreen(
    compendiumItem: CompendiumDataList,
    filterResults: (List<CompendiumItem>, String) -> List<CompendiumItem>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(4.dp),
) {
    Column (modifier.padding(contentPadding)){
        val currentFilterTerm = remember {
            mutableStateOf("any")
        }
        //val filteredData2 : List<CompendiumItem> = compendiumItem.data.filter { item -> item.category == "creatures" }
        val filteredData : List<CompendiumItem> = if(currentFilterTerm.value == "any") {
            compendiumItem.data
        } else {
            filterResults(compendiumItem.data,currentFilterTerm.value)
        }
        CompendiumDefaultFilters(
            currentFilterTerm = currentFilterTerm,
            presetFiltersList = listOf(
                "any",
                "creatures",
                "equipment",
                "materials",
                "monsters",
                "treasure"
            )
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            modifier = modifier
                .padding(horizontal = 4.dp)
                .fillMaxWidth(),
            contentPadding = PaddingValues(0.dp),//this stopped being top element so no longer requires [contentPadding] here
        ) {
            items(
                items = filteredData/*compendiumItem.data*/,
                key = { item -> item.id }) { it ->
                CompendiumItemCard(
                    compendiumItem = it,
                    modifier = modifier
                        .padding(4.dp)
                        .sizeIn(minWidth = 124.dp, maxWidth = 124.dp)
                )
            }
        }
    }
}

@Composable
fun CompendiumDefaultFilters(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(4.dp),
    currentFilterTerm: MutableState<String>,
    presetFiltersList: List<String>,
) {
    val scrollState = rememberScrollState()
    Row(modifier = Modifier.padding(16.dp).horizontalScroll(scrollState)) {
        for ((index, buttonLabel) in presetFiltersList.withIndex()) {
            Button(onClick = { currentFilterTerm.value = presetFiltersList[index] }, Modifier.padding(horizontal = 4.dp)) {
                Text(text = buttonLabel.replaceFirstChar(Char::titlecase))
            }
        }
    }
}

@Composable
fun CompendiumItemCard(compendiumItem: CompendiumItem, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.padding(4.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current).data(compendiumItem.image)
                    .crossfade(true).build(),
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),//todo the size of this should be defined and static
                contentDescription = stringResource(R.string.compendium_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier.padding(4.dp)
            )
            Column (modifier = Modifier.padding(all = 16.dp, )) {
                Text(text = compendiumItem.name.uppercase(), style = MaterialTheme.typography.bodyLarge)
                Text(text = compendiumItem.id.toString(), style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    HyruleCompendiumTheme {
        LoadingScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    HyruleCompendiumTheme {
        ErrorScreen({})
    }
}

@Preview(showBackground = true)
@Composable
fun ItemsGridScreenPreview() {
    HyruleCompendiumTheme {
        val mockData = CompendiumDataList(listOf(
            CompendiumItem(
                category = "creatures",
                common_locations = listOf("Pastures"),
                description = "Its a horse",
                dlc = false,
                drops = listOf("Manure"),
                id = 0,
                image = "https://botw-compendium.herokuapp.com/api/v3/compendium/entry/white_horse/image",//"https://botw-compendium.herokuapp.com/api/v3/compendium/entry/luminous_ore_deposit/image",
                name = "Horse",
                cooking_effect = null
            )
        ))
        val mockFilterPlaceholder : (List<CompendiumItem>, String) -> List<CompendiumItem> = {
            mockDataInner, filterTermInner ->
            mockDataInner.filter { item -> item.category == filterTermInner }
        }
        ItemsGridScreen(mockData, filterResults = mockFilterPlaceholder)
    }
}
@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun CompendiumDefaultFiltersPreview() {
    HyruleCompendiumTheme {
        CompendiumDefaultFilters(
            currentFilterTerm = mutableStateOf(""),
            presetFiltersList = listOf("creatures", "treasure", "monsters")
        )
    }
}