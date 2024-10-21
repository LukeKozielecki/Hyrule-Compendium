package luke.koz.hyrulecompendium.ui.screens.ItemsGrid

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import luke.koz.hyrulecompendium.ui.screens.HomeScreen
import luke.koz.hyrulecompendium.utils.screens.CompendiumTopAppBar
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
                compendiumViewModel = compendiumViewModel,
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
                contentPadding = it
            )
        }
    }
}
