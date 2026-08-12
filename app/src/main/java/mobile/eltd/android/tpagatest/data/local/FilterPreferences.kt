package mobile.eltd.android.tpagatest.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import mobile.eltd.android.tpagatest.presentation.movements.FilterType

private val Context.movementsDataStore: DataStore<Preferences> by preferencesDataStore(name = "movements")

class FilterPreferences(
    private val context: Context,
) {

    private val key = stringPreferencesKey("filter")

    val filter: Flow<FilterType> =
        context.movementsDataStore.data.map { preferences ->
            preferences[key]?.let { name ->
                FilterType.entries.firstOrNull { it.name == name }
            } ?: FilterType.ALL
        }

    suspend fun save(filter: FilterType) {
        context.movementsDataStore.edit { preferences ->
            preferences[key] = filter.name
        }
    }
}