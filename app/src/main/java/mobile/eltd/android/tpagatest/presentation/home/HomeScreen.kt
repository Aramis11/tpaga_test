package mobile.eltd.android.tpagatest.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import mobile.eltd.android.tpagatest.R
import mobile.eltd.android.tpagatest.presentation.dashboard.DashboardRoute
import mobile.eltd.android.tpagatest.presentation.movements.MovementsRoute
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeRoute(
    onMovementClick: (Int) -> Unit,
    viewModel: HomeViewModel = koinViewModel(),
) {
    HomeScreen(
        onLogout = { viewModel.onLogout() },
        onMovementClick = onMovementClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onLogout: () -> Unit,
    onMovementClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.home_title)) },
                actions = {
                    TextButton(onClick = onLogout) {
                        Text(stringResource(R.string.home_logout))
                    }
                },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            DashboardRoute(
                modifier = Modifier.fillMaxWidth(),
            )
            HorizontalDivider()
            MovementsRoute(
                onMovementClick = onMovementClick,
                modifier = Modifier.weight(1f),
            )
        }
    }
}