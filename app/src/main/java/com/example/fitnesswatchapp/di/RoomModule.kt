package com.example.fitnesswatchapp.di

import android.content.Context
import androidx.room.Room
import com.example.fitnesswatchapp.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * este modulo nos va a proveer independencias de librerias o de clases
 * que continen interfaces
 */

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val APP_DATABASE_NAME = "rutina_database"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, APP_DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideRutinaDao(db: AppDatabase) = db.rutinaDao()
}