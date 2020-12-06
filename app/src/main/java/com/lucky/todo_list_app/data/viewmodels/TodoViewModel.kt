package com.lucky.todo_list_app.data.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.lucky.todo_list_app.data.database.TodoDatabase
import com.lucky.todo_list_app.data.model.Todo
import com.lucky.todo_list_app.data.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoViewModel(context: Application) : AndroidViewModel(context) {
    private val todoDao = TodoDatabase.getDatabase(context).todoDao()
    private val repository: TodoRepository
    private val _todo: LiveData<List<Todo>>
    private val _orderByLow:LiveData<List<Todo>>
    private val _orderByHigh:LiveData<List<Todo>>

    init {
        repository = TodoRepository(todoDao)
        _todo = repository.getAllData
        _orderByHigh =  repository.sortByHighPriority
        _orderByLow =  repository.sortByLowPriority
       }

    fun getAllData(): LiveData<List<Todo>> {
        return _todo
    }

    fun orderByLow():LiveData<List<Todo>>
    {
        return _orderByLow
    }
    fun orderByHigh():LiveData<List<Todo>>
    {
        return _orderByHigh
    }

    fun addTodo(todo: Todo)
    {
        viewModelScope.launch(Dispatchers.IO)
        {
           repository.addTodo(todo)
        }
    }

    fun updateTodo(todo: Todo)
    {
        viewModelScope.launch(Dispatchers.IO)
        {
            repository.updateTodo(todo)
        }
    }

    fun deleteTodo(todo: Todo)
    {
        viewModelScope.launch(Dispatchers.IO)
        {
            repository.deleteTodo(todo)
        }
    }

    fun deleteAll()
    {
        viewModelScope.launch(Dispatchers.IO)
        {
            repository.deleteAll()
        }
    }
    fun searchDatabase(searchQuery: String):LiveData<List<Todo>>
    {
       return repository.searchDatabase(searchQuery)
    }


}