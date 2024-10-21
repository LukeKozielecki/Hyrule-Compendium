package luke.koz.hyrulecompendium.ui.screens.ItemsGrid

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import luke.koz.hyrulecompendium.R
import luke.koz.hyrulecompendium.ui.theme.HyruleCompendiumTheme


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

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    HyruleCompendiumTheme {
        LoadingScreen()
    }
}