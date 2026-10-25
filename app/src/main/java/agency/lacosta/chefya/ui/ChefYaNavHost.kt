package agency.lacosta.chefya.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import agency.lacosta.chefya.R
import agency.lacosta.chefya.ui.home.HomeScreen
import agency.lacosta.chefya.ui.recipe.RecipeScreen
import agency.lacosta.chefya.ui.recipes.RecipesScreen
import agency.lacosta.chefya.ui.settings.SettingsScreen
import agency.lacosta.chefya.ui.shopping.ShoppingScreen
import kotlinx.coroutines.launch

private enum class Tab(val route: String, @StringRes val label: Int, val icon: ImageVector) {
    HOME("home", R.string.tab_home, Icons.Filled.Home),
    RECIPES("recipes", R.string.tab_recipes, Icons.AutoMirrored.Filled.List),
    SHOPPING("shopping", R.string.tab_shopping, Icons.Filled.ShoppingCart),
    SETTINGS("settings", R.string.tab_settings, Icons.Filled.Settings),
}

private const val RECIPE = "recipe/{id}"

private fun recipe(id: String) = "recipe/$id"

@Composable
fun ChefYaNavHost() {
    val vm: ChefYaViewModel = viewModel(factory = ChefYaViewModel.Factory)
    val state by vm.state.collectAsStateWithLifecycle()
    val nav = rememberNavController()
    val entry by nav.currentBackStackEntryAsState()
    val current = entry?.destination?.route
    val snackbar = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val say: (String) -> Unit = { message -> scope.launch { snackbar.showSnackbar(message) } }

    Scaffold(
        // Cada pantalla maneja la barra de arriba: Inicio pinta su cabecera mango detrás de ella.
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        snackbarHost = { SnackbarHost(snackbar) },
        bottomBar = {
            if (Tab.entries.any { it.route == current }) {
                NavigationBar(containerColor = MaterialTheme.colorScheme.background) {
                    Tab.entries.forEach { tab ->
                        NavigationBarItem(
                            selected = current == tab.route,
                            onClick = {
                                nav.navigate(tab.route) {
                                    popUpTo(nav.graph.findStartDestination().id) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Icon(tab.icon, contentDescription = null) },
                            label = { Text(stringResource(tab.label)) },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = MaterialTheme.colorScheme.secondary,
                                selectedIconColor = MaterialTheme.colorScheme.onSecondary,
                            ),
                        )
                    }
                }
            }
        },
    ) { padding ->
        NavHost(navController = nav, startDestination = Tab.HOME.route, modifier = Modifier.padding(padding)) {
            composable(Tab.HOME.route) {
                HomeScreen(
                    state = state,
                    onAdd = vm::addToPantry,
                    onRemove = vm::removeFromPantry,
                    onOpenRecipe = { nav.navigate(recipe(it)) },
                    onSeeAll = { nav.navigate(Tab.RECIPES.route) { launchSingleTop = true } },
                    say = say,
                )
            }
            composable(Tab.RECIPES.route) {
                RecipesScreen(state = state, onOpenRecipe = { nav.navigate(recipe(it)) })
            }
            composable(Tab.SHOPPING.route) {
                ShoppingScreen(
                    state = state,
                    onAdd = vm::addToShopping,
                    onBought = vm::markBought,
                    onRemove = vm::removeFromShopping,
                )
            }
            composable(Tab.SETTINGS.route) {
                SettingsScreen(state = state, onThemeMode = vm::setThemeMode, onClearPantry = vm::clearPantry, say = say)
            }
            composable(RECIPE) { backStack ->
                RecipeScreen(
                    recipeId = backStack.arguments?.getString("id").orEmpty(),
                    state = state,
                    onBack = { nav.popBackStack() },
                    onAddMissing = { keys ->
                        vm.addToShopping(keys)
                    },
                    say = say,
                )
            }
        }
    }
}
