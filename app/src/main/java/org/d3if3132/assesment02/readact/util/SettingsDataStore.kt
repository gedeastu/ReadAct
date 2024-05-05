package org.d3if3132.assesment02.readact.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore : DataStore<Preferences> by preferencesDataStore(
    name = "settings_preferences"
)
class SettingsDataStore {
}