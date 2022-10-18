package com.example.noteapp.cleannoteapp.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.noteapp.cleannoteapp.util.PreferenceKeys
import com.example.noteapp.cleannoteapp.room_database.AppRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ProductionModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        AppRoomDatabase::class.java,
        "com.example.noteapp.cleannoteapp.local.db"
    ).build()

    @Singleton
    @Provides
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences {
        return context.getSharedPreferences(
            PreferenceKeys.NOTE_PREFERENCES,
            Context.MODE_PRIVATE
        )
    }
}