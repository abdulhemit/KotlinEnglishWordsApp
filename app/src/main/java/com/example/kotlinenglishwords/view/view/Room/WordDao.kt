package com.example.kotlinenglishwords.view.view.Room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.kotlinenglishwords.view.view.Model.Words
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable


@Dao
interface WordDao {

    @Query("SELECT * FROM words")
    fun allget () : Flowable<List<Words>>

    @Insert
    fun insert (words: Words) : Completable

    @Delete
    fun delete (words: Words) : Completable
}