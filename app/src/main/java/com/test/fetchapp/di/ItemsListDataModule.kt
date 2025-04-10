package com.test.fetchapp.di

import com.test.fetchapp.data.datasources.RemoteItemsDataSourceImpl
import com.test.fetchapp.domain.datasources.RemoteItemsDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@InstallIn(ViewModelComponent::class)
@Module
abstract class ItemsListDataModule {

    @Binds
    @ViewModelScoped
    abstract fun provideRemoteItemsDataSource(repository: RemoteItemsDataSourceImpl): RemoteItemsDataSource

}