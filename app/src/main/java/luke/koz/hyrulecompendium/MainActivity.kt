package luke.koz.hyrulecompendium

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import luke.koz.hyrulecompendium.ui.screens.CompendiumAppScreen
import luke.koz.hyrulecompendium.ui.theme.HyruleCompendiumTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HyruleCompendiumTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    CompendiumAppScreen()
                }
            }
        }
    }
}