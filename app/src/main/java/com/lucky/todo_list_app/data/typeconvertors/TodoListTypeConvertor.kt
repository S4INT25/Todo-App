package com.lucky.todo_list_app.data.typeconvertors

import androidx.room.TypeConverter
import com.lucky.todo_list_app.data.model.Priority

class TodoListTypeConvertor {
   @TypeConverter
    fun fromPriority(priority: Priority):String{
        return priority.name
    }
    @TypeConverter
    fun toPriority(priority: String):Priority
    {
        return  Priority.valueOf(priority)
    }


}