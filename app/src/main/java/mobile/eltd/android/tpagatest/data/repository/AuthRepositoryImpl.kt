package mobile.eltd.android.tpagatest.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import mobile.eltd.android.tpagatest.domain.model.Session
import mobile.eltd.android.tpagatest.domain.repository.AuthRepository

private val Context.sessionDataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class AuthRepositoryImpl(
    private val context: Context,
) : AuthRepository {

    private object Keys {
        val USERNAME = stringPreferencesKey("username")
    }

    override val session: Flow<Session?> =
        context.sessionDataStore.data
            .map { preferences -> preferences[Keys.USERNAME]?.let { Session(it) } }
            .catch { emit(null) }

    override suspend fun login(username: String) {
        context.sessionDataStore.edit { preferences ->
            preferences[Keys.USERNAME] = username
        }
    }

    override suspend fun logout() {
        context.sessionDataStore.edit { preferences ->
            preferences.remove(Keys.USERNAME)
        }
    }
}