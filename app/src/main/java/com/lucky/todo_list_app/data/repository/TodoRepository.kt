package com.lucky.todo_list_app.data.repository

import androidx.lifecycle.LiveData
import com.lucky.todo_list_app.data.model.TodoDao
import com.lucky.todo_list_app.data.model.Todo

class TodoRepository(private val todoDao: TodoDao) {

    val getAllData:LiveData<List<Todo>> =  todoDao.getAllData()
    val sortByHighPriority:LiveData<List<Todo>> =  todoDao.sortByHighPriority()
    val sortByLowPriority:LiveData<List<Todo>> = todoDao.sortByLowPriority()

    suspend fun addTodo(todo: Todo)
    {
        todoDao.insertData(todo)
    }

    suspend fun updateTodo(todo: Todo)
    {
        todoDao.updateData(todo)
    }
    suspend fun deleteTodo(todo: Todo)
    {
        todoDao.deleteData(todo)
    }

    suspend fun deleteAll()
    {
        todoDao.deleteAll()
    }

    fun searchDatabase(searchQuery: String):LiveData<List<Todo>>
    {
      return  todoDao.searchDatabase(searchQuery)
    }



}