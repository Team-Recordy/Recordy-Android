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
import org.sopt.datastore.UserData
import org.sopt.datastore.UserDataSerializer
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Provides
    @Singleton
    fun providesUserDataStore(
        @ApplicationContext context: Context,
        userDataSerializer: UserDataSerializer,
    ): DataStore<UserData> =
        DataStoreFactory.create(
            serializer = userDataSerializer,
        ) {
            context.dataStoreFile("userdata.json")
        }
}