package com.cuecall.app.data.remote.source

import com.cuecall.app.data.remote.dto.TokenDto
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Firestore data source for real-time token updates.
 * Collection structure:
 *   clinics/{clinicId}/tokens/{tokenId}
 *   clinics/{clinicId}/queue_days/{queueDayId}/sequences/{serviceId}
 */
@Singleton
class FirestoreTokenSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private fun tokensCollection(clinicId: String) =
        firestore.collection("clinics").document(clinicId).collection("tokens")

    private fun sequenceDoc(clinicId: String, queueDayId: String, serviceId: String) =
        firestore.collection("clinics").document(clinicId)
            .collection("queue_days").document(queueDayId)
            .collection("sequences").document(serviceId)

    /** Observe all tokens for a queue day in real time. */
    fun observeTokensForDay(clinicId: String, queueDayId: String): Flow<List<TokenDto>> =
        callbackFlow {
            val registration: ListenerRegistration = tokensCollection(clinicId)
                .whereEqualTo("queueDayId", queueDayId)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }
                    val tokens = snapshot?.documents
                        ?.mapNotNull { it.data?.let { map -> TokenDto.fromMap(map) } }
                        ?: emptyList()
                    trySend(tokens)
                }
            awaitClose { registration.remove() }
        }

    /** Save or update a token document. */
    suspend fun saveToken(clinicId: String, dto: TokenDto) {
        tokensCollection(clinicId).document(dto.id)
            .set(dto.toMap())
            .await()
    }

    /** Update only the status and timestamps on a token document. */
    suspend fun updateTokenFields(clinicId: String, tokenId: String, fields: Map<String, Any?>) {
        tokensCollection(clinicId).document(tokenId)
            .update(fields)
            .await()
    }

    /**
     * Atomically increment and return the next token number for a service/day.
     * Uses Firestore transaction. REQUIRES active network.
     */
    suspend fun getNextTokenNumberAtomic(
        clinicId: String,
        queueDayId: String,
        serviceId: String
    ): Int {
        val seqRef = sequenceDoc(clinicId, queueDayId, serviceId)
        return firestore.runTransaction { transaction ->
            val snapshot = transaction.get(seqRef)
            val current = if (snapshot.exists()) {
                (snapshot.getLong("nextNumber") ?: 0L).toInt()
            } else {
                0
            }
            val next = current + 1
            transaction.set(seqRef, mapOf("nextNumber" to next))
            next
        }.await()
    }

    /** Reset sequence counter to 0 for the given service/day. */
    suspend fun resetSequence(clinicId: String, queueDayId: String, serviceId: String) {
        sequenceDoc(clinicId, queueDayId, serviceId)
            .set(mapOf("nextNumber" to 0))
            .await()
    }
}
