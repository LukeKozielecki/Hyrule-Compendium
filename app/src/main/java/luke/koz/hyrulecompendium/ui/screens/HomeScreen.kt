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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import luke.koz.hyrulecompendium.R
import luke.koz.hyrulecompendium.model.CompendiumItem
import luke.koz.hyrulecompendium.model.CompendiumDataList
import luke.koz.hyrulecompendium.ui.theme.HyruleCompendiumTheme
import luke.koz.hyrulecompendium.viewmodel.CompendiumUiState
import luke.koz.hyrulecompendium.viewmodel.CompendiumViewModel

@Composable
fun HomeScreen(
    compendiumUiState: CompendiumUiState,
    compendiumViewModel: CompendiumViewModel,
    retryAction: () -> Unit,
    filterResults: (List<CompendiumItem>, String) -> List<CompendiumItem>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    when (compendiumUiState) {
        is CompendiumUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is CompendiumUiState.Success -> ItemsGridScreen(
            compendiumViewModel = compendiumViewModel,
            compendiumDataList = compendiumUiState.compendiumDataList,
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
    compendiumViewModel: CompendiumViewModel,
    compendiumDataList: CompendiumDataList,
    filterResults: (List<CompendiumItem>, String) -> List<CompendiumItem>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(4.dp),
) {
    Column (modifier.padding(contentPadding)){
        val currentFilterTerm by compendiumViewModel.currentFilterCategory.collectAsState()
        val searchCategory by compendiumViewModel.selectedCategoryOfInputSearch.collectAsState()
        val sortAscending by compendiumViewModel.sortDirectionAscending.collectAsState()
//        val filteredData : List<CompendiumItem> = if(currentFilterTerm == "any") {
//            compendiumItem.data
//        } else {//TODO fix currentFilterTerm
//            filterResults(compendiumItem.data,currentFilterTerm)
//        }
        val filteredData : List<CompendiumItem> = compendiumViewModel.filterData(compendiumDataList)
//        compendiumViewModel.filterData(compendiumDataList)
//        val filteredData by compendiumViewModel.filteredData.collectAsState()
        val sortedData = if (sortAscending) {
            filteredData.sortedBy { it.id }
        } else {
            filteredData.sortedByDescending { it.id }
        }
        CompendiumDefaultFilters(
            currentFilterTerm = currentFilterTerm,
            compendiumViewModel = compendiumViewModel,
            presetFiltersList = listOf(
                "any",
                "creatures",
                "equipment",
                "materials",
                "monsters",
                "treasure"
            )
        )
        var text by remember { mutableStateOf("") }
//        Button(onClick = { compendiumViewModel.updateFilterTerm(text) }) {
//            Text ("SEARCH")
//        }
        Text(text = "currentFilterCategory: "+compendiumViewModel.currentFilterCategory.value)
        Text(text = "currentSearchKeyPhrase: "+compendiumViewModel.currentSearchKeyPhrase.value)
        Text(text = "selectedCategoryOfInputSearch: "+compendiumViewModel.selectedCategoryOfInputSearch.value)
        TextField(value = text, onValueChange = {
            text = it
            compendiumViewModel.updateSearchKeyPhrase(text)
        },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done // Set imeAction to Done
            ),
            )
        SearchCategoryDropdownMenu(searchCategory = compendiumViewModel::assignSelectedCategoryOfInputSearch/*filterData = CompendiumViewModel::filterData*/)
        //this is a grid for the sake of adaptive ui implementation
        LazyVerticalGrid(
            columns = GridCells.Fixed(1), //todo move this to Viewmodel. connect to adaptive layout after it is implemented
            modifier = modifier
                .padding(horizontal = 4.dp)
                .fillMaxWidth(),
            contentPadding = PaddingValues(0.dp),//this stopped being top element so no longer requires [contentPadding] here
        ) {
            items(
                items = sortedData,//filteredData,/*compendiumItem.data*/
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
    compendiumViewModel: CompendiumViewModel,
    contentPadding: PaddingValues = PaddingValues(4.dp),
    currentFilterTerm: String,
    presetFiltersList: List<String>,
) {
    val scrollState = rememberScrollState()
    Row(modifier = Modifier
        .padding(16.dp)
        .horizontalScroll(scrollState)) {
        for ((index, buttonLabel) in presetFiltersList.withIndex()) {
            Button(onClick = { compendiumViewModel.updateFilterTerm(presetFiltersList[index]) }, Modifier.padding(horizontal = 4.dp)) {
                Text(text = buttonLabel.replaceFirstChar(Char::titlecase))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchCategoryDropdownMenu(modifier: Modifier = Modifier, searchCategory: (String) -> Unit) {
    val categories = listOf("any", "category", "id", "name")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(categories[0]) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                categories.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(text = category) },
                        onClick = {
                            searchCategory(category)
                            selectedText = category
                            expanded = false
                        }
                    )
                }
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
                contentScale = ContentScale.FillBounds,
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
        val mockCompendiumViewModel: CompendiumViewModel = viewModel()
        ItemsGridScreen(
            compendiumViewModel = mockCompendiumViewModel,
            compendiumDataList = mockData,
            filterResults = mockFilterPlaceholder
        )
    }
}
@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun CompendiumDefaultFiltersPreview() {
    val mockCompendiumViewModel: CompendiumViewModel = viewModel(factory = CompendiumViewModel.Factory)
    HyruleCompendiumTheme {
        CompendiumDefaultFilters(
            currentFilterTerm = "",
            compendiumViewModel = mockCompendiumViewModel,
            presetFiltersList = listOf("creatures", "treasure", "monsters")
        )
    }
}