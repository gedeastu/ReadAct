package org.d3if3132.assesment02.readact.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book")
data class Book(
    @PrimaryKey(autoGenerate = true)
    val id:Long = 0L,
    val cover: String,
    val title:String,
    val desc:String,
    val date:Int,
)