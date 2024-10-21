package luke.koz.hyrulecompendium.ui.screens.ItemsGrid

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import luke.koz.hyrulecompendium.model.CompendiumDataList
import luke.koz.hyrulecompendium.model.CompendiumItem
import luke.koz.hyrulecompendium.ui.screens.SelectedItem.CompendiumItemScreen
import luke.koz.hyrulecompendium.utils.screens.SearchCategoryDropdownMenu
import luke.koz.hyrulecompendium.ui.theme.Dimens
import luke.koz.hyrulecompendium.ui.theme.HyruleCompendiumTheme
import luke.koz.hyrulecompendium.utils.screens.InputSearchPhrase
import luke.koz.hyrulecompendium.viewmodel.CompendiumViewModel

/**
 * The home screen displaying item grid.
 */
@Composable
fun ItemsGridScreen(
    compendiumViewModel: CompendiumViewModel,
    compendiumDataList: CompendiumDataList,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(4.dp),
) {
    Box {
        val selectedCompendiumItem by compendiumViewModel.selectedCompendiumItemForFullCard.collectAsState()
        Column(modifier.padding(contentPadding)) {
            val sortAscending by compendiumViewModel.sortDirectionAscending.collectAsState()
            val filteredData: List<CompendiumItem> =
                compendiumViewModel.filterData(compendiumDataList)
            val sortedData = if (sortAscending) {
                filteredData.sortedBy { it.id }
            } else {
                filteredData.sortedByDescending { it.id }
            }
            val localIsOpenSearchTools = compendiumViewModel.openSearchTools.collectAsState().value
            if (localIsOpenSearchTools) {
                val currentFilterCategory by compendiumViewModel.currentFilterCategory.collectAsState()
                Text(text = "Current Filter Category:\n$currentFilterCategory", textAlign = TextAlign.Center, modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = Dimens.paddingSmall))
                CompendiumDefaultFilters(
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
                Column (modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.paddingSmall)
                    .align(Alignment.CenterHorizontally),
                    horizontalAlignment = Alignment.CenterHorizontally){

                    val selectedItemSubcategoryOfInputSearch by compendiumViewModel.selectedCategoryOfInputSearch.collectAsState()
                    Text(text = "Current Item Subcategory Of Input Search:\n$selectedItemSubcategoryOfInputSearch", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                    //todo make this recompose ui, atm it does not
                    SearchCategoryDropdownMenu(searchCategory = compendiumViewModel::assignSelectedCategoryOfInputSearch/*filterData = CompendiumViewModel::filterData*/)

                    val currentSearchKeyPhrase by compendiumViewModel.currentSearchKeyPhrase.collectAsState()
                    Text(text = "Current Search Key Phrase:\n$currentSearchKeyPhrase", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                    InputSearchPhrase(
                        text = text,
                        onTextChange = { newText -> text = newText },
                        compendiumViewModel = compendiumViewModel
                    )

                }
            }

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
                    key = { item -> item.id }) {
                    CompendiumItemCard(
                        compendiumItem = it,
                        modifier = modifier
                            .padding(4.dp)
                            .sizeIn(minWidth = 124.dp, maxWidth = 124.dp),
                        onClickItemSelect = {compendiumViewModel.updateSelectedCompendiumItemForFullCard(it)}
                    )
                }
            }
        }
        CompendiumItemScreen(compendiumItem = selectedCompendiumItem, contentPadding = contentPadding)
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
            compendiumDataList = mockData
        )
    }
}