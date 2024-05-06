package org.d3if3132.assesment02.readact.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.d3if3132.assesment02.readact.model.Book

@Database(entities = [Book::class], version = 1, exportSchema = false)
abstract class BookDb : RoomDatabase(){
    abstract val dao:BookDao
    companion object {
        @Volatile
        private var INSTANCE: BookDb? = null
        fun getInstance(context: Context) : BookDb{
            synchronized(lock = this, block = {
                var instance = INSTANCE
                if (instance == null){
                    instance = Room.databaseBuilder(
                        context = context.applicationContext,
                        BookDb::class.java,
                        name = "book.db"
                    ).build()
                    INSTANCE = instance
                }
                return  instance
            })
        }
    }
}