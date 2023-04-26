package com.example.criticaltechworkschallenge.di

import com.example.criticaltechworkschallenge.util.biometric.CryptographyManager
import com.example.criticaltechworkschallenge.util.biometric.CryptographyManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideCryptographyManager(): CryptographyManager {
        return CryptographyManagerImpl()
    }

}