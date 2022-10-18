package com.example.noteapp.cleannoteapp.di

import android.content.SharedPreferences
import com.example.noteapp.cleannoteapp.room_database.AppRoomDatabase
import com.example.noteapp.cleannoteapp.room_database.note_table.NoteDao
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDao(appRoomDatabase: AppRoomDatabase): NoteDao {
        return appRoomDatabase.noteDao()
    }

    @Provides
    @Singleton
    fun provideSharedPrefsEditor(sharedPreferences: SharedPreferences): SharedPreferences.Editor {
        return sharedPreferences.edit()
    }

}