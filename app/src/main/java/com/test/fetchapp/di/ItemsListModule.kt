package com.test.fetchapp.di

import com.test.fetchapp.data.api.ItemsApiService
import com.test.fetchapp.data.datasources.RemoteItemsDataSourceImpl
import com.test.fetchapp.domain.datasources.RemoteItemsDataSource
import com.test.fetchapp.domain.repository.ItemsRepository
import com.test.fetchapp.domain.usecases.FetchGroupedItemsUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@InstallIn(ViewModelComponent::class)
@Module
class ItemsListModule {

    @Provides
    @ViewModelScoped
    fun provideItemsApiService(retrofit: Retrofit) = retrofit.create(
        ItemsApiService::class.java)

    @Provides
    @ViewModelScoped
    fun provideItemsRepository(dataSource: RemoteItemsDataSource) = ItemsRepository(dataSource)

    @Provides
    @ViewModelScoped
    fun provideFetchGroupedItemsUseCase(repository: ItemsRepository) = FetchGroupedItemsUseCase(repository)
}