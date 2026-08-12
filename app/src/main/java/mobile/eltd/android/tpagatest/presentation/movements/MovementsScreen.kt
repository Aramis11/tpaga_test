package mobile.eltd.android.tpagatest.presentation.movements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import mobile.eltd.android.tpagatest.R
import mobile.eltd.android.tpagatest.core.CARD_TINT_ALPHA
import mobile.eltd.android.tpagatest.core.EXPENSE_COLOR
import mobile.eltd.android.tpagatest.core.INCOME_COLOR
import mobile.eltd.android.tpagatest.core.MOVEMENT_DATE_FORMAT
import mobile.eltd.android.tpagatest.domain.model.Movement
import mobile.eltd.android.tpagatest.domain.model.MovementType
import org.koin.androidx.compose.koinViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun MovementsRoute(
    onMovementClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MovementsViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MovementsContent(
        uiState = uiState,
        onSelectFilter = { viewModel.onEvent(MovementsEvent.SelectFilter(it)) },
        onMovementClick = onMovementClick,
        onRetry = { viewModel.onEvent(MovementsEvent.Retry) },
        modifier = modifier,
    )
}

@Composable
fun MovementsContent(
    uiState: MovementsUiState,
    onSelectFilter: (FilterType) -> Unit,
    onMovementClick: (Int) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (uiState) {
        is MovementsUiState.Loading -> LoadingMovements(modifier)
        is MovementsUiState.Success -> SuccessMovements(uiState, onSelectFilter, onMovementClick, modifier)
        is MovementsUiState.Error -> ErrorMovements(uiState.messageRes, onRetry, modifier)
        is MovementsUiState.Empty -> EmptyMovements(modifier)
    }
}

@Composable
private fun LoadingMovements(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun SuccessMovements(
    uiState: MovementsUiState.Success,
    onSelectFilter: (FilterType) -> Unit,
    onMovementClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        if (uiState.offline) {
            OfflineBanner()
        }
        FilterRow(uiState.filter, onSelectFilter)
        if (uiState.movements.isEmpty()) {
            EmptyMovements(Modifier.fillMaxSize())
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(uiState.movements, key = { it.id }) { movement ->
                    MovementRow(movement, onClick = { onMovementClick(movement.id) })
                }
            }
        }
    }
}

@Composable
private fun FilterRow(
    selected: FilterType,
    onSelectFilter: (FilterType) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        FilterType.entries.forEach { filter ->
            FilterChip(
                selected = selected == filter,
                onClick = { onSelectFilter(filter) },
                label = { Text(stringResource(filter.labelRes())) },
            )
        }
    }
}

@Composable
private fun OfflineBanner() {
    Surface(modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.tertiaryContainer) {
        Text(
            text = stringResource(R.string.movements_offline),
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
        )
    }
}

@Composable
private fun MovementRow(
    movement: Movement,
    onClick: () -> Unit,
) {
    val isIncome = movement.type == MovementType.INCOME
    val accent = if (isIncome) INCOME_COLOR else EXPENSE_COLOR

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.small,
        color = accent.copy(alpha = CARD_TINT_ALPHA),
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = movement.description,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
Text(
                text = remember(movement.date) {
                    SimpleDateFormat(MOVEMENT_DATE_FORMAT, Locale.getDefault()).format(Date(movement.date))
                },
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            }
            Text(
                text = remember(movement.amount, isIncome) {
                    val formatted = NumberFormat.getCurrencyInstance().format(movement.amount)
                    if (isIncome) "+$formatted" else "-$formatted"
                },
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = accent,
            )
        }
    }
}

@Composable
private fun ErrorMovements(
    messageRes: Int,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = stringResource(messageRes),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
        )
        Button(onClick = onRetry) {
            Text(stringResource(R.string.dashboard_retry))
        }
    }
}

@Composable
private fun EmptyMovements(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(R.string.movements_empty),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
        )
    }
}