package luke.koz.hyrulecompendium.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import luke.koz.hyrulecompendium.R
import luke.koz.hyrulecompendium.ui.theme.HyruleCompendiumTheme
import luke.koz.hyrulecompendium.viewmodel.CompendiumViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompendiumAppScreen() {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val compendiumViewModel: CompendiumViewModel = viewModel(factory = CompendiumViewModel.Factory)
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CompendiumTopAppBar(
                scrollBehavior = scrollBehavior,
                compendiumViewModel = compendiumViewModel
            )
        },
    ) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            HomeScreen(
                compendiumUiState = compendiumViewModel.compendiumUiState,
                compendiumViewModel = compendiumViewModel,
                retryAction = compendiumViewModel::getCompendiumItem,
                filterResults = compendiumViewModel::filterData, //todo <- this need down the chain to be purged late? moved to viewmodel
                contentPadding = it
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompendiumTopAppBar(scrollBehavior: TopAppBarScrollBehavior, modifier: Modifier = Modifier, compendiumViewModel: CompendiumViewModel) {
    val sortDirectionAscending by compendiumViewModel.sortDirectionAscending.collectAsState() //this facilitates reloading icon && state flow not being call within composition
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Box (){
                Text(
                    text = stringResource(R.string.compendium_header),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()

                )
            }
        },
//        navigationIcon = {
//            IconButton(onClick = { /* do something */ }) {
//                Icon(
//                    imageVector = Icons.Filled.Search,
//                    contentDescription = "Search"
//                )
//            }
//        },
        actions = {
            IconButton(onClick = { compendiumViewModel.toggleSortOrder() }) {

                Icon(
                    imageVector = if(sortDirectionAscending){
                        Icons.Filled.KeyboardArrowDown
                    } else {
                        Icons.Filled.KeyboardArrowUp
                    },
                    contentDescription = if(sortDirectionAscending){
                        "Sort Button. Sorting Up"
                    } else {
                        "Sort Button. Sorting down"
                    },
                )
            }
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search"
                )
            }
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Favorites"
                )
            }
        },

//        colors = TopAppBarDefaults.topAppBarColors(
//            containerColor = MaterialTheme.colorScheme.primaryContainer,
//            titleContentColor = MaterialTheme.colorScheme.primary,
//        ),
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun CompendiumTopAppBarPreview() {
    val mockCompendiumViewModel: CompendiumViewModel = viewModel(factory = CompendiumViewModel.Factory)
    HyruleCompendiumTheme {
        CompendiumTopAppBar(
            modifier = Modifier,
            scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
            compendiumViewModel = mockCompendiumViewModel
        )
    }
}