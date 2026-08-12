package mobile.eltd.android.tpagatest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import mobile.eltd.android.tpagatest.presentation.navigation.TpagaRoot
import mobile.eltd.android.tpagatest.ui.theme.TpagaTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            TpagaTheme {
                TpagaRoot(appViewModel = koinViewModel())
            }
        }
    }
}