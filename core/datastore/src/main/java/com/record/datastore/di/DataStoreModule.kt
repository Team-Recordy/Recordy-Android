package com.record.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.record.datastore.RecordyLocalData
import com.record.datastore.RecordyLocalDataSerializer
import com.record.datastore.token.AuthToken
import com.record.datastore.token.TokenDataSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Provides
    @Singleton
    fun providesUserDataStore(
        @ApplicationContext context: Context,
        recordyLocalDataSerializer: RecordyLocalDataSerializer,
    ): DataStore<RecordyLocalData> =
        DataStoreFactory.create(
            serializer = recordyLocalDataSerializer,
        ) {
            context.dataStoreFile("recordydata.json")
        }

    @Provides
    @Singleton
    fun providesTokenDataStore(
        @ApplicationContext context: Context,
        tokenDataSerializer: TokenDataSerializer,
    ): DataStore<AuthToken> =
        DataStoreFactory.create(
            serializer = tokenDataSerializer,
        ) {
            context.dataStoreFile("token.json")
        }
}
