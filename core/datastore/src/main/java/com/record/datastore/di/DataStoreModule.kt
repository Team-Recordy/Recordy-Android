package org.sopt.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.sopt.datastore.RecordyLocalData
import org.sopt.datastore.RecordyLocalDataSerializer
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
}
