package com.lucky.todo_list_app.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lucky.todo_list_app.data.model.Todo
import com.lucky.todo_list_app.data.model.TodoDao
import com.lucky.todo_list_app.data.typeconvertors.TodoListTypeConvertor

@Database(version = 1,entities = [Todo::class], exportSchema = false)
@TypeConverters(TodoListTypeConvertor::class)
abstract class TodoDatabase: RoomDatabase() {
    abstract fun todoDao(): TodoDao
    companion object{
        var INSTANCE:TodoDatabase? =  null
        fun getDatabase(context: Context):TodoDatabase
        {
            val tempInstance = INSTANCE
            if(tempInstance !=  null)
            {
                return  tempInstance
            }
            synchronized(this)
            {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TodoDatabase::class.java,
                    "todo_database"
                ).build()
                INSTANCE =  instance
                return instance
            }
        }
    }

}