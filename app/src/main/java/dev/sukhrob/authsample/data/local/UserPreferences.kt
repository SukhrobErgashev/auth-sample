package dev.sukhrob.authsample.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences(private val context: Context) {

    companion object {
        private val Context.userDataStore: DataStore<Preferences>
                by preferencesDataStore(name = "my_data_store")

        private val KEY_AUTH = stringPreferencesKey("key_auth")
    }

    // Get Token from DataStore
    val authToken: Flow<String?>
        get() = context.userDataStore.data.map { preferences ->
            preferences[KEY_AUTH]
        }

    // Save Token to DataStore
    suspend fun saveAuthToken(authToken: String) {
        context.userDataStore.edit { preferences ->
            preferences[KEY_AUTH] = authToken
        }
    }

    suspend fun clear() {
        context.userDataStore.edit { preferences ->
            preferences.clear()
        }
    }

}