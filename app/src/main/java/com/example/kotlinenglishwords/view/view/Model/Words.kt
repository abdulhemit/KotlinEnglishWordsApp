package com.example.kotlinenglishwords.view.view.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "words")
class Words (
    @ColumnInfo(name = "En_word")
    val En_word : String,

    @ColumnInfo(name = "Tr_word")
    val Tr_word :String,

    @ColumnInfo(name = "En_Sentence")
    val En_Sentence : String

) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id = 0

}