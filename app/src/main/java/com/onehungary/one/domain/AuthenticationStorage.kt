package com.onehungary.one.domain

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore("auth_prefs")

@Singleton
class AuthenticationStorage @Inject constructor(
    private val context: Context
) {

    companion object {
        private val SESSION_KEY = booleanPreferencesKey("hasSession")
    }

    suspend fun saveSession(isAuthenticated: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[SESSION_KEY] = isAuthenticated
        }
    }

    val isSessionAuthenticated = runBlocking {
        context.dataStore.data
            .map { preferences ->
                preferences[SESSION_KEY] ?: false
            }.first()
    }

}