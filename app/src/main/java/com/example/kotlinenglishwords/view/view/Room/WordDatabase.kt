package com.example.kotlinenglishwords.view.view.Room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.kotlinenglishwords.view.view.Model.Words


@Database(entities = [Words::class], version = 1)
abstract class WordDatabase:RoomDatabase() {
    abstract fun WordDao(): WordDao

}