package com.viskit.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.viskit.datastore.token.AuthToken
import com.viskit.datastore.token.TokenDataSerializer
import com.viskit.datastore.user.UserData
import com.viskit.datastore.user.UserDataSerializer
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
    fun providesTokenDataStore(
        @ApplicationContext context: Context,
        tokenDataSerializer: TokenDataSerializer,
    ): DataStore<AuthToken> =
        DataStoreFactory.create(
            serializer = tokenDataSerializer,
        ) {
            context.dataStoreFile("token.json")
        }

    @Provides
    @Singleton
    fun provideUserDataStore(
        @ApplicationContext context: Context,
        userDataSerializer: UserDataSerializer,
    ): DataStore<UserData> =
        DataStoreFactory.create(
            serializer = userDataSerializer,
        ) {
            context.dataStoreFile("user.json")
        }
}
