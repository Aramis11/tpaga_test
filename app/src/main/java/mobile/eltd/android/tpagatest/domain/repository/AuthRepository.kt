package mobile.eltd.android.tpagatest.domain.repository

import kotlinx.coroutines.flow.Flow
import mobile.eltd.android.tpagatest.domain.model.Session

interface AuthRepository {
    val session: Flow<Session?>
    suspend fun login(username: String)
    suspend fun logout()
}