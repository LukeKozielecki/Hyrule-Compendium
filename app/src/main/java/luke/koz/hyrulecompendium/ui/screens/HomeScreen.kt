package luke.koz.hyrulecompendium.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import luke.koz.hyrulecompendium.R
import luke.koz.hyrulecompendium.ui.screens.ItemsGrid.ErrorScreen
import luke.koz.hyrulecompendium.ui.screens.ItemsGrid.ItemsGridScreen
import luke.koz.hyrulecompendium.ui.screens.ItemsGrid.LoadingScreen
import luke.koz.hyrulecompendium.viewmodel.CompendiumUiState
import luke.koz.hyrulecompendium.viewmodel.CompendiumViewModel

@Composable
fun HomeScreen(
    compendiumUiState: CompendiumUiState,
    compendiumViewModel: CompendiumViewModel,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    Box {
        Image(
            painter = painterResource(id = R.drawable.zelda_dark_backgorund),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = modifier.fillMaxSize()
        )
        when (compendiumUiState) {
            is CompendiumUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
            is CompendiumUiState.Success -> ItemsGridScreen(
                compendiumViewModel = compendiumViewModel,
                compendiumDataList = compendiumUiState.compendiumDataList,
                modifier = modifier.fillMaxWidth(),
                contentPadding = contentPadding
            )

            is CompendiumUiState.Error -> ErrorScreen(
                retryAction,
                modifier = modifier.fillMaxSize()
            )
        }
    }
}