package agency.lacosta.chefya

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import agency.lacosta.chefya.data.Settings
import agency.lacosta.chefya.ui.ChefYaNavHost
import agency.lacosta.chefya.ui.theme.ChefYaTheme
import agency.lacosta.chefya.ui.theme.isDark

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }
        val settingsFlow = (application as ChefYaApp).container.kitchen.settings
        setContent {
            val settings by settingsFlow.collectAsStateWithLifecycle(initialValue = Settings())
            val dark = isDark(settings.themeMode)
            // La cabecera mango es clara en los dos temas, así que los íconos de arriba van oscuros siempre;
            // los de abajo siguen el tema.
            DisposableEffect(dark) {
                val top = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
                val bottom = if (dark) SystemBarStyle.dark(Color.TRANSPARENT) else top
                enableEdgeToEdge(statusBarStyle = top, navigationBarStyle = bottom)
                onDispose { }
            }
            ChefYaTheme(themeMode = settings.themeMode) {
                ChefYaNavHost()
            }
        }
    }
}
