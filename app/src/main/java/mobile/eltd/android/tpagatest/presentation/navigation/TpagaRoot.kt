package mobile.eltd.android.tpagatest.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import mobile.eltd.android.tpagatest.core.Route
import mobile.eltd.android.tpagatest.presentation.app.AppViewModel
import mobile.eltd.android.tpagatest.presentation.app.AuthState
import mobile.eltd.android.tpagatest.presentation.auth.login.LoginRoute
import mobile.eltd.android.tpagatest.presentation.home.HomeRoute

@Composable
fun TpagaRoot(appViewModel: AppViewModel) {
    val authState by appViewModel.authState.collectAsStateWithLifecycle(initialValue = AuthState.Loading)

    when (authState) {
        is AuthState.Loading -> LoadingScreen()
        is AuthState.LoggedOut,
        is AuthState.LoggedIn,
        -> AuthNavHost(authState)
    }
}

@Composable
private fun LoadingScreen() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
private fun AuthNavHost(authState: AuthState) {
    val navController = rememberNavController()
    val isLoggedIn = authState is AuthState.LoggedIn

    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) Route.HOME.route else Route.LOGIN.route,
    ) {
        composable(Route.LOGIN.route) {
            LoginRoute()
        }
        composable(Route.HOME.route) {
            HomeRoute()
        }
    }

    LaunchedEffect(isLoggedIn) {
        val target = if (isLoggedIn) Route.HOME.route else Route.LOGIN.route
        if (navController.currentDestination?.route != target) {
            navController.navigate(target) {
                popUpTo(navController.graph.id) { inclusive = true }
            }
        }
    }
}