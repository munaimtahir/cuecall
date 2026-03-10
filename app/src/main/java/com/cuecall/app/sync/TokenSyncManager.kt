package com.cuecall.app.sync

import com.cuecall.app.data.local.dao.TokenDao
import com.cuecall.app.data.remote.source.FirestoreTokenSource
import com.cuecall.app.data.repository.toEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Subscribes to Firestore token updates for a queue day and writes them into Room.
 * This keeps the local Room cache fresh for display and counter screens.
 *
 * Call [startSync] when entering Display or Counter mode for a queue day.
 * Cancel the scope to stop syncing.
 */
@Singleton
class TokenSyncManager @Inject constructor(
    private val firestoreSource: FirestoreTokenSource,
    private val tokenDao: TokenDao
) {
    fun startSync(clinicId: String, queueDayId: String, scope: CoroutineScope) {
        firestoreSource.observeTokensForDay(clinicId, queueDayId)
            .onEach { dtos ->
                dtos.forEach { dto -> tokenDao.upsertToken(dto.toEntity()) }
            }
            .catch { e ->
                // Log but don't crash — display will show stale Room cache
                android.util.Log.w("TokenSyncManager", "Firestore sync error", e)
            }
            .launchIn(scope)
    }
}
