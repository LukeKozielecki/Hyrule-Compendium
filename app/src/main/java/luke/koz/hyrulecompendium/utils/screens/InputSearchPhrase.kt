package luke.koz.hyrulecompendium.utils.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import luke.koz.hyrulecompendium.ui.theme.Dimens
import luke.koz.hyrulecompendium.viewmodel.CompendiumViewModel


@Composable
fun InputSearchPhrase(
    modifier: Modifier = Modifier,
    text: String,
    onTextChange: (String) -> Unit,
    compendiumViewModel: CompendiumViewModel
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = Dimens.paddingSmall),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = "Select continuous search phrase")
        TextField(
            value = text,
            onValueChange = { newText ->
                onTextChange(newText)
                compendiumViewModel.updateSearchKeyPhrase(newText)
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
        )
    }
}