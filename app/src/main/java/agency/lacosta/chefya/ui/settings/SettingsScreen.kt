package agency.lacosta.chefya.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import agency.lacosta.chefya.R
import agency.lacosta.chefya.data.ThemeMode
import agency.lacosta.chefya.domain.Plan
import agency.lacosta.chefya.ui.KitchenState
import agency.lacosta.chefya.ui.theme.ChefYa

@Composable
fun SettingsScreen(
    state: KitchenState,
    onThemeMode: (ThemeMode) -> Unit,
    onClearPantry: () -> Unit,
    say: (String) -> Unit,
) {
    val cleared = stringResource(R.string.pantry_cleared)
    Column(
        Modifier.fillMaxSize().statusBarsPadding().verticalScroll(rememberScrollState()).padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Text(stringResource(R.string.tab_settings), style = MaterialTheme.typography.headlineMedium)

        Card(stringResource(R.string.theme)) {
            listOf(
                ThemeMode.SYSTEM to R.string.theme_system,
                ThemeMode.LIGHT to R.string.theme_light,
                ThemeMode.DARK to R.string.theme_dark,
            ).forEach { (mode, label) ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .selectable(selected = state.settings.themeMode == mode, onClick = { onThemeMode(mode) }, role = Role.RadioButton),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    RadioButton(selected = state.settings.themeMode == mode, onClick = null, modifier = Modifier.padding(end = 10.dp, top = 8.dp, bottom = 8.dp))
                    Text(stringResource(label), style = MaterialTheme.typography.bodyLarge)
                }
            }
        }

        Card(stringResource(R.string.plan)) {
            Text(
                stringResource(if (state.settings.isPro) R.string.plan_pro else R.string.plan_free),
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                stringResource(R.string.plan_free_detail, Plan.FREE_PHOTOS_PER_DAY, Plan.FREE_RECIPES_PER_DAY),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                stringResource(R.string.plan_pro_detail, Plan.PRO_MONTHLY_USD, Plan.PRO_YEARLY_USD),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        Card(stringResource(R.string.kitchen)) {
            Text(
                stringResource(R.string.kitchen_detail),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            TextButton(
                onClick = {
                    onClearPantry()
                    say(cleared)
                },
                enabled = state.pantry.isNotEmpty(),
            ) { Text(stringResource(R.string.pantry_clear)) }
        }

        Card(stringResource(R.string.about)) {
            Text(stringResource(R.string.version, versionName()), style = MaterialTheme.typography.bodyMedium)
            Text(stringResource(R.string.made_by), style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun Card(title: String, content: @Composable () -> Unit) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = ChefYa.colors.chip,
        border = androidx.compose.foundation.BorderStroke(1.dp, ChefYa.colors.chipEdge),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            content()
        }
    }
}

@Composable
private fun versionName(): String {
    val context = LocalContext.current
    return runCatching { context.packageManager.getPackageInfo(context.packageName, 0).versionName }.getOrNull().orEmpty()
}
