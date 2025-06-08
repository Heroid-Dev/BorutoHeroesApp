package com.example.borutoapp.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.borutoapp.domain.repository.DataStoreOperation
import com.example.borutoapp.util.Constant.PREFERENCES_KEY
import com.example.borutoapp.util.Constant.PREFERENCE_NAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map


val Context.myPreferenceDataStore: DataStore<Preferences> by preferencesDataStore(PREFERENCE_NAME)

class DataStoreOperationImpl(context: Context) : DataStoreOperation {

    private val myPreferenceDataStore = context.myPreferenceDataStore

    private object PreferenceKey {
        val onBoardingKey = booleanPreferencesKey(PREFERENCES_KEY)
    }

    private val taskFlowState = myPreferenceDataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences()) else throw exception
        }.map { preferences ->
            val onBoardingState = preferences[PreferenceKey.onBoardingKey] ?: false
            onBoardingState
        }

    override suspend fun saveOnBoardingState(complete: Boolean) {
        myPreferenceDataStore.edit { preference ->
            preference[PreferenceKey.onBoardingKey] = complete
        }
    }

    override fun readOnBoardingState(): Flow<Boolean> {
        return taskFlowState
    }
}