package com.example.noteapp.cleannoteapp.di

import android.content.Context
import androidx.room.Room
import com.example.noteapp.cleannoteapp.room_database.AppRoomDatabase
import com.example.noteapp.cleannoteapp.room_database.note_table.NoteDao
import dagger.hilt.InstallIn
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Module {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        AppRoomDatabase::class.java,
        "com.example.noteapp.cleannoteapp.local.db"
    ).build()

    @Provides
    fun provideNoteDao(appRoomDatabase: AppRoomDatabase): NoteDao{
        return appRoomDatabase.noteDao()
    }
}